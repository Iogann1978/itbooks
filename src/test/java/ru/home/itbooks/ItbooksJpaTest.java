package ru.home.itbooks;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.itbooks.model.*;
import ru.home.itbooks.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class ItbooksJpaTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private TagService tagService;
    @Autowired
    private DescriptService descriptService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PublisherService publisherService;

    @Test
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
        long count = tagService.count();
        assertTrue(tags2 != null);
        assertEquals(tags1.size(), count);
        val tagList = new HashSet<Tag>();
        tags2.forEach(t -> {
            assertNotNull(t);
            assertNotNull(t.getId());
            tagList.add(t);
            log.info("tag: {} {}", t.getId(), t.getTag());
        });

        val descript1 = new Descript(null, "<html/>".getBytes());
        val descript2 = descriptService.save(descript1);
        log.info("descript: {} {}", descript2.getId(), descript2.getText());
        count = descriptService.count();
        assertNotNull(descript2);
        assertNotNull(descript2.getId());
        assertEquals(count, 1);

        val authors1 = new ArrayList<Author>() {
            {
                add(new Author(null,"Dorian Yates", bookList));
                add(new Author(null,"Kai Greene", bookList));
            }
        };
        val authors2 = authorService.saveAll(authors1);
        count = authorService.count();
        assertNotNull(authors2);
        assertEquals(authors1.size(),count);
        val authorList = new HashSet<Author>();
        authors2.forEach(a -> {
            assertNotNull(a);
            assertNotNull(a.getId());
            authorList.add(a);
            log.info("author: {} {} {}", a.getId(), a.getName());
        });

        val publusher1 = new Publisher(null, "Addison Wesley Professional", bookList);
        val publisher2 = publisherService.save(publusher1);
        assertNotNull(publisher2);
        assertNotNull(publisher2.getId());

        book1.setAuthors(authorList);
        book1.setTags(tagList);
        book1.setPublisher(publisher2);
        val book2 = bookService.save(book1);
        log.info("book: {} {} {}", book2.getId(), book2.getTitle(), new String(book2.getContents()));
        count = bookService.count();
        assertNotNull(book2);
        assertNotNull(book2.getId());
        assertEquals(count, 1);

        bookService.deleteById(book2.getId());
        count = bookService.count();
        assertEquals(count, 0);
    }
}
