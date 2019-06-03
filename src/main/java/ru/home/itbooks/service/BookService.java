package ru.home.itbooks.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.BookForm;
import ru.home.itbooks.repository.BookRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class BookService extends AbstractService<Book, BookRepository> {
    private DescriptService descriptService;
    private AuthorService authorService;
    private TagService tagService;
    private PublisherService publisherService;
    private BookFileService bookFileService;

    @Autowired
    public BookService(BookRepository repository,
                       DescriptService descriptService,
                       AuthorService authorService,
                       TagService tagService,
                       PublisherService publisherService,
                       BookFileService bookFileService) {
        super(repository);
        this.descriptService = descriptService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.publisherService = publisherService;
        this.bookFileService = bookFileService;
    }

    @SneakyThrows
    public Book save(BookForm bookForm) {
        val book = Book.builder()
                .id(bookForm.getId())
                .title(bookForm.getTitle())
                .year(bookForm.getYear())
                .pages(bookForm.getPages())
                .rate(bookForm.getRate())
                .state(bookForm.getState())
                .build();
        val book_save = save(book);

        if(bookForm.getFileHtml() != null && !bookForm.getFileHtml().isEmpty()) {
            try {
                val desc_new = new Descript();
                desc_new.setText(bookForm.getFileHtml().getBytes());
                val desc_save = descriptService.save(desc_new);
                book_save.setDescript(desc_save);
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        if(bookForm.getFileXml() != null && !bookForm.getFileXml().isEmpty()) {
            try {
                book_save.setContents(bookForm.getFileXml().getBytes());
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        if(bookForm.getAuthors() != null && !bookForm.getAuthors().isEmpty()) {
            val authors = bookForm.getAuthors().split(";");
            for(val author : authors) {
                val auth = authorService.findByName(author).orElse(Author.builder().name(author).build());
                auth.addBook(book_save);
                val auth_save = authorService.save(auth);
                book_save.addAuthor(auth_save);
            }
        }
        if(bookForm.getTags() != null && !bookForm.getTags().isEmpty()) {
            val tags = bookForm.getTags().split(",");
            for(val tag : tags) {
                val tg = tagService.findById(Long.parseLong(tag))
                        .orElseThrow(() -> new Exception(String.format("Тэг %s не найден!", bookForm.getPublisher())));
                tg.addBook(book_save);
                val tg_save = tagService.save(tg);
                book_save.addTag(tg_save);
            }
        }
        if(bookForm.getPublisher() != null) {
            val pub = publisherService.findById(bookForm.getPublisher())
                    .orElseThrow(() -> new Exception(String.format("Издатель %s не найден!", bookForm.getPublisher())));
            pub.addBook(book_save);
            val pub_save = publisherService.save(pub);
            book_save.setPublisher(pub_save);
        }
        if(bookForm.getFile() != null) {
            val file = bookFileService.findById(bookForm.getFile())
                    .orElseThrow(() -> new Exception(String.format("Файл %s не найден!", bookForm.getFile())));
            val file_save = bookFileService.save(file);
            book_save.setFile(file_save);
        }
        return save(book_save);
    }
}
