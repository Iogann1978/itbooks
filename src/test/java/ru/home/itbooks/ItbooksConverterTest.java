package ru.home.itbooks;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.itbooks.config.JpaConfig;
import ru.home.itbooks.converter.AuthorsConverter;
import ru.home.itbooks.converter.BookFileConverter;
import ru.home.itbooks.converter.DescriptConverter;
import ru.home.itbooks.converter.PublisherConverter;
import ru.home.itbooks.model.*;
import ru.home.itbooks.repository.*;
import ru.home.itbooks.service.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ItbooksApplication.class, JpaConfig.class })
@DataJpaTest
@Slf4j
public class ItbooksConverterTest {
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

    private PublisherConverter publisherConverter;
    private BookFileConverter bookFileConverter;
    private DescriptConverter descriptConverter;
    private AuthorsConverter authorsConverter;
    private Set<Author> auths;

    private PublisherService publisherService;
    private BookFileService bookFileService;
    private DescriptService descriptService;
    private AuthorService authorService;
    private TagService tagService;
    private BookService bookService;

    @Before
    public void setUp() {
        publisherService = new PublisherService(publisherRepository);
        publisherConverter = new PublisherConverter(publisherService);
        bookFileService = new BookFileService(bookFileRepository);
        bookFileConverter = new BookFileConverter(bookFileService);
        descriptService = new DescriptService(descriptRepository);
        descriptConverter = new DescriptConverter(descriptService);
        authorService = new AuthorService(authorRepository);
        authorsConverter = new AuthorsConverter(authorService);
        tagService = new TagService(tagRepository);
        bookService = new BookService(bookRepository,
                descriptService,
                authorService,
                tagService,
                publisherService,
                new ContentsService());

        auths = new HashSet<Author>() {
            {
                add(Author.builder().name("Author7").build());
            }
        };
        val desc = Descript.builder().text(new byte[3]).build();
        val pub = Publisher.builder().name("Publisher7").build();
        val file = BookFile.builder().filename("file7").size(0L).build();
        val book = Book.builder()
                .title("book7")
                .authors(auths)
                .descript(desc)
                .publisher(pub)
                .file(file)
                .build();
        bookService.save(book);
    }

    @Test
    public void testStringIdConverter() {
        publisherService.findAll().forEach(pub ->
            assertNotNull(publisherConverter.convert(pub.getId().toString())));
        String title = "Publisher7";
        val pub = publisherConverter.convert(title);
        assertNotNull(pub);
        assertEquals(title, pub.getName());
        assertNull(pub.getId());

        bookFileService.findAll().forEach(file ->
                assertNotNull(bookFileConverter.convert(file.getId().toString())));
        assertNull(bookFileConverter.convert("file7"));

        descriptService.findAll().forEach(desc ->
                assertNotNull(descriptConverter.convert(desc.getId().toString())));
        assertNull(descriptConverter.convert("Desc7"));

        val listAuths = authorsConverter.convert(auths.stream()
                .map(Author::getName).collect(Collectors.joining(",")));
        assertNotNull(listAuths);
        assertEquals(1, listAuths.size());
    }
}
