package ru.home.itbooks.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.*;
import ru.home.itbooks.repository.BookRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
public class BookService extends AbstractService<Book, BookRepository> {
    private DescriptService descriptService;
    private AuthorService authorService;
    private TagService tagService;
    private PublisherService publisherService;

    @Autowired
    public BookService(BookRepository repository,
                       DescriptService descriptService,
                       AuthorService authorService,
                       TagService tagService,
                       PublisherService publisherService) {
        super(repository);
        this.descriptService = descriptService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.publisherService = publisherService;
    }

    public Optional<Book> findById(long id) {
        val a = new HashSet<Author>() {
            {
                add(new Author(null, "Brian Göetz", null));
                add(new Author(null, "Tim Peierls", null));
                add(new Author(null, "Joshua Blochn", null));
                add(new Author(null, "Joseph Bowbeer", null));
                add(new Author(null, "David Holmes", null));
                add(new Author(null, "Doug Lea", null));
            }
        };
        val d = new Descript(null, "<html><body><h1>Descript</h1><hr/></body></html>".getBytes());
        val b = Book.builder()
                .title("Java Concurrency In Practice")
                .publisher(new Publisher(null, "Addison Wesley Professional", null))
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .authors(a)
                .descript(d)
                .build();
        val book = Optional.of(b);
        return book;
    }

    public Book save(BookForm bookForm) {
        val book = Book.builder()
                .title(bookForm.getTitle())
                .authors(new HashSet<>())
                .year(bookForm.getYear())
                .pages(bookForm.getPages())
                .rate(bookForm.getRate())
                .state(bookForm.getState())
                .build();
        if(bookForm.getFileHtml() != null && !bookForm.getFileHtml().isEmpty()) {
            try {
                val desc_new = new Descript();
                desc_new.setText(bookForm.getFileHtml().getBytes());
                val desc_save = descriptService.save(desc_new);
                book.setDescript(desc_save);
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        if(bookForm.getFileXml() != null && !bookForm.getFileXml().isEmpty()) {
            try {
                book.setContents(bookForm.getFileXml().getBytes());
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        if(bookForm.getAuthors() != null && !bookForm.getAuthors().isEmpty()) {
            val authors = bookForm.getAuthors().split(";");
            for(val author : authors) {
                val auth = authorService.findByName(author).orElse(Author.builder().name(author).build());
                auth.addBook(book);
                val auth_save = authorService.save(auth);
                book.addAuthor(auth_save);
            }
        }
        if(bookForm.getTags() != null && !bookForm.getTags().isEmpty()) {
            val tags = bookForm.getTags().split(",");
            for(val tag : tags) {
                val tg = tagService.findById(Long.parseLong(tag)).orElse(Tag.builder().tag(tag).build());
                tg.addBook(book);
                val tg_save = tagService.save(tg);
                book.addTag(tg_save);
            }
        }
        if(bookForm.getPublisher() != null && !bookForm.getPublisher().isEmpty()) {
            val pub = publisherService.findById(Long.parseLong(bookForm.getPublisher()))
                    .orElse(Publisher.builder().name(bookForm.getPublisher()).build());
            pub.addBook(book);
            val pub_save = publisherService.save(pub);
            book.setPublisher(pub_save);
        }
        val book_save = save(book);
        return book_save;
    }
}
