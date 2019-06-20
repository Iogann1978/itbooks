package ru.home.itbooks.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.BookForm;
import ru.home.itbooks.model.form.FindForm;
import ru.home.itbooks.model.xml.Contents;
import ru.home.itbooks.repository.BookRepository;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService extends AbstractService<Book, BookForm, BookRepository> {
    private DescriptService descriptService;
    private AuthorService authorService;
    private TagService tagService;
    private PublisherService publisherService;
    private BookFileService bookFileService;
    private ContentsService contentsService;

    @Autowired
    public BookService(BookRepository repository,
                       DescriptService descriptService,
                       AuthorService authorService,
                       TagService tagService,
                       PublisherService publisherService,
                       BookFileService bookFileService,
                       ContentsService contentsService) {
        super(repository);
        this.descriptService = descriptService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.publisherService = publisherService;
        this.bookFileService = bookFileService;
        this.contentsService = contentsService;
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
        if(bookForm.getContents() != null) {
            book.setContents(bookForm.getContents().getBytes(StandardCharsets.UTF_8));
        }
        if(bookForm.getDescript() != null) {
            val desc = descriptService.findById(bookForm.getDescript())
                    .orElseThrow(() -> new Exception(String.format("Описание %s не найдено!", bookForm.getDescript())));
            book.setDescript(desc);
        }
        val book_save = save(book);

        if(bookForm.getFileHtml() != null && !bookForm.getFileHtml().isEmpty()) {
            val desc_new = new Descript();
            desc_new.setText(bookForm.getFileHtml().getBytes());
            val desc_save = descriptService.save(desc_new);
            book_save.setDescript(desc_save);
        }
        if(bookForm.getFileXml() != null && !bookForm.getFileXml().isEmpty()) {
            book_save.setContents(bookForm.getFileXml().getBytes());
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

    @SneakyThrows
    public Collection<Book> findBook(FindForm findForm, String action) {
        Collection<Book> books = null;
        switch (action) {
            case "state":
                books = getRepository().findBooksByState(findForm.getState());
                break;
            case "title":
                books = getRepository().findBooksByTitleContains(findForm.getTitle());
                break;
            case "author":
                val author = authorService.findById(findForm.getAuthorId())
                        .orElseThrow(() -> new Exception(String.format("Автор %s не найден!", findForm.getAuthorId())));
                books = author.getBooks();
                break;
            case "publisher":
                val publisher = publisherService.findById(findForm.getPublisherId())
                        .orElseThrow(() -> new Exception(String.format("Издатель %s не найден!", findForm.getPublisherId())));
                books = publisher.getBooks();
                break;
            case "tag":
                val tag = tagService.findById(findForm.getTagId())
                        .orElseThrow(() -> new Exception(String.format("Тэг %s не найден!", findForm.getTagId())));
                books = tag.getBooks();
                break;
            case "descript":
                books = descriptService.findByHtml(findForm.getDescript())
                        .stream().filter(d -> d.getBook() != null)
                        .map(d -> d.getBook()).collect(Collectors.toList());
                break;
            case "contents":
                books = getRepository().findAll().stream()
                        .filter(book -> {
                            if(book.getContents() == null || book.getContents().length == 0) {
                                return false;
                            } else {
                                val root = ContentsService.fromBytes(book.getContents());
                                return contentsService.containsXml(root, findForm.getContents());
                            }
                        }).collect(Collectors.toList());
                break;
            default:
                break;
        }
        return books;
    }
}
