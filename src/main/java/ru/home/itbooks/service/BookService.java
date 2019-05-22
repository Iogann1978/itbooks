package ru.home.itbooks.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.*;
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

    @PostConstruct
    public void init() {
        val b = Book.builder()
                .title("Java Concurrency In Practice")
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .build();
        val blist = new HashSet<Book>() {
            {
                add(b);
            }
        };

        val pubs = new HashSet<Publisher>() {
            {
                add(new Publisher(null, "Publisher 1", blist));
                add(new Publisher(null, "Publisher 2", blist));
                add(new Publisher(null, "Publisher 3", blist));
            }
        };
        val pubs_iter = publisherService.saveAll(pubs);

        val a = new HashSet<Author>() {
            {
                add(new Author(null, "Brian Göetz", blist));
                add(new Author(null, "Tim Peierls", blist));
                add(new Author(null, "Joshua Blochn", blist));
                add(new Author(null, "Joseph Bowbeer", blist));
                add(new Author(null, "David Holmes", blist));
                add(new Author(null, "Doug Lea", blist));
            }
        };
        val a_iter = authorService.saveAll(a);
        val a_save = new HashSet<Author>();
        a_iter.forEach(ai -> a_save.add(ai));

        val d = new Descript(null, "<html><body><h1>Descript</h1><hr/></body></html>".getBytes());
        val d_save = descriptService.save(d);

        Set<Tag> tags = new HashSet<Tag>() {
            {
                add(new Tag(null, "Tag 1", blist));
                add(new Tag(null, "Tag 2", blist));
                add(new Tag(null, "Tag 3", blist));
            }
        };
        val tags_iter = tagService.saveAll(tags);
        val tags_save = new HashSet<Tag>();
        tags_iter.forEach(t -> tags_save.add(t));

        b.setAuthors(a_save);
        b.setTags(tags_save);
        b.setPublisher(pubs_iter.iterator().next());
        b.setDescript(d_save);
        save(b);
    }

    @SneakyThrows
    public Book save(BookForm bookForm) {
        val book = Book.builder()
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
        if(bookForm.getPublisher() != null && !bookForm.getPublisher().isEmpty()) {
            val pub = publisherService.findById(Long.parseLong(bookForm.getPublisher()))
                    .orElseThrow(() -> new Exception(String.format("Издатель %s не найден!", bookForm.getPublisher())));
            pub.addBook(book_save);
            val pub_save = publisherService.save(pub);
            book.setPublisher(pub_save);
        }
        return save(book_save);
    }
}
