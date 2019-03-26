package ru.home.itbooks;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.itbooks.model.*;
import ru.home.itbooks.service.BookService;
import ru.home.itbooks.service.DescriptService;
import ru.home.itbooks.service.TagService;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
@Slf4j
public class ItbooksJpaTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private TagService tagService;
    @Autowired
    private DescriptService descriptService;

    @Test
    public void testBooks() {
        val tags1 = new ArrayList<Tag>() {
            {
                add(new Tag(null, "Java"));
            }
        };
        val tags2 = tagService.saveAll(tags1);
        long count = tagService.count();
        assertTrue(tags2 != null);
        assertEquals(tags1.size(), count);
        val tagList = new ArrayList<Tag>();
        tags2.forEach(t -> {
            assertNotNull(t);
            assertNotNull(t.getId());
            tagList.add(t);
            log.info("tag: {} {}", t.getId(), t.getTag());
        });

        val descript1 = new Descript(null, "Descript");
        val descript2 = descriptService.save(descript1);
        log.info("descript: {} {}", descript2.getId(), descript2.getText());
        count = descriptService.count();
        assertNotNull(descript2);
        assertNotNull(descript2.getId());
        assertEquals(count, 1);

        //val book1 = new Book(null, "Sping Boot 2", 537, "Dorian Yates", "Appress", BookRate.GOOD, 2015, BookState.PLANNED, tagList, descript2, "<html/>");
        val book1 = Book.builder()
                .title("Sping Boot 2")
                .pages(537)
                .author("Dorian Yates")
                .publisher("Appress")
                .rate(BookRate.GOOD)
                .year(2015)
                .state(BookState.PLANNED)
                .tags(tagList)
                .descript(descript2)
                .contents("<html/>")
                .build();
        val book2 = bookService.save(book1);
        log.info("book: {} {}", book2.getId(), book2.getTitle());
        count = bookService.count();
        assertNotNull(book2);
        assertNotNull(book2.getId());
        assertEquals(count, 1);

        bookService.deleteById(book2.getId());
        count = bookService.count();
        assertEquals(count, 0);
    }
}
