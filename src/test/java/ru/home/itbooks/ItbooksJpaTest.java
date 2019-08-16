package ru.home.itbooks;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.home.itbooks.config.JpaConfig;
import ru.home.itbooks.converter.AuthorsConverter;
import ru.home.itbooks.converter.BookFileConverter;
import ru.home.itbooks.converter.DescriptConverter;
import ru.home.itbooks.converter.PublisherConverter;
import ru.home.itbooks.model.*;
import ru.home.itbooks.repository.*;
import ru.home.itbooks.service.*;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ItbooksApplication.class, JpaConfig.class })
@DataJpaTest
@Slf4j
public class ItbooksJpaTest {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookFileRepository bookFileRepository;
    @Autowired
    private DescriptRepository descriptRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TagRepository tagRepository;

    private BookService bookService;
    private TagService tagService;
    private DescriptService descriptService;
    private AuthorService authorService;
    private PublisherService publisherService;
    private BookFileService bookFileService;

    @Before
    public void setUp() {
        publisherService = new PublisherService(publisherRepository);
        bookFileService = new BookFileService(bookFileRepository);
        descriptService = new DescriptService(descriptRepository);
        authorService = new AuthorService(authorRepository);
        tagService = new TagService(tagRepository);
        bookFileService = new BookFileService(bookFileRepository);
        bookService = new BookService(bookRepository,
                descriptService,
                authorService,
                tagService,
                publisherService,
                new ContentsService());
    }

    @Test
    @Transactional
    public void testBooks() {
        val book1 = Book.builder()
                .title("Sping Boot 2")
                .pages(537)
                .rate(BookRate.GOOD)
                .year(2015)
                .state(BookState.PLANNED)
                .contents("<xml/>".getBytes())
                .build();
        val bookList = new HashSet<Book>() {
            {
                add(book1);
            }
        };

        val tags1 = new HashSet<Tag>() {
            {
                add(new Tag(null, "Java", bookList));
            }
        };

        val tags2 = tagService.saveAll(tags1);
        assertTrue(tags2 != null);
        val tagList = new HashSet<Tag>();
        tags2.forEach(t -> {
            assertNotNull(t);
            assertNotNull(t.getId());
            tagList.add(t);
            log.info("tag: {} {}", t.getId(), t.getTag());
        });

        val descript1 = new Descript(null, "<html/>".getBytes(), book1);
        val descript2 = descriptService.save(descript1);
        log.info("descript: {} {}", descript2.getId(), descript2.getText());
        assertNotNull(descript2);
        assertNotNull(descript2.getId());

        val authors1 = new ArrayList<Author>() {
            {
                add(new Author(null,"Dorian Yates", bookList));
                add(new Author(null,"Kai Greene", bookList));
            }
        };
        val authors2 = authorService.saveAll(authors1);
        assertNotNull(authors2);
        val authorList = new HashSet<Author>();
        authors2.forEach(a -> {
            assertNotNull(a);
            assertNotNull(a.getId());
            authorList.add(a);
            log.info("author: {} {}", a.getId(), a.getName());
        });

        val publusher1 = new Publisher(null, "Addison Wesley Professional", bookList);
        val publisher2 = publisherService.save(publusher1);
        assertNotNull(publisher2);
        assertNotNull(publisher2.getId());

        book1.setAuthors(authorList);
        book1.setTags(tagList);
        book1.setPublisher(publisher2);
        book1.setDescript(descript2);
        val book2 = bookService.save(book1);
        log.info("book: {} {} {}", book2.getId(), book2.getTitle(), new String(book2.getContents()));
        assertNotNull(book2);
        assertNotNull(book2.getId());

        bookService.deleteById(book2.getId());
    }
}
