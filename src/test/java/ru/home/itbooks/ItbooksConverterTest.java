package ru.home.itbooks;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.itbooks.converter.*;
import ru.home.itbooks.model.*;
import ru.home.itbooks.service.*;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ItbooksConverterTest {
    private PublisherConverter publisherConverter;
    private BookFileConverter bookFileConverter;
    private DescriptConverter descriptConverter;
    private AuthorsConverter authorsConverter;
    private TagsConverter tagsConverter;
    private TagConverter tagConverter;
    private FileHtmlConverter fileHtmlConverter;
    private  FileXmlConverter fileXmlConverter;

    private Set<Author> auths;
    private Set<Tag> tags;
    private Descript descript;
    private Publisher publisher;
    private BookFile file;
    private Book book;

    @Mock
    private PublisherService publisherService;
    @Mock
    private BookFileService bookFileService;
    @Mock
    private DescriptService descriptService;
    @Mock
    private AuthorService authorService;
    @Mock
    private TagService tagService;
    @Mock
    private BookService bookService;

    @Before
    public void setUp() {
        auths = new HashSet<Author>() {
            {
                add(Author.builder().id(1L).name("Author1").build());
                add(Author.builder().id(2L).name("Author2").build());
                add(Author.builder().id(3L).name("Author3").build());
            }
        };
        when(authorService.findAll()).thenReturn(auths);

        tags = new HashSet<Tag>() {
            {
                add(Tag.builder().id(4L).tag("Tag1").build());
                add(Tag.builder().id(5L).tag("Tag2").build());
                add(Tag.builder().id(6L).tag("Tag3").build());
            }
        };
        when(tagService.findAll()).thenReturn(tags);
        tags.stream().forEach(tag ->
                when(tagService.findById(tag.getId())).thenReturn(Optional.of(tag)));

        descript = Descript.builder().id(7L).text(new byte[3]).build();
        when(descriptService.findById(descript.getId()))
                .thenReturn(Optional.of(descript));

        publisher = Publisher.builder().id(8L).name("Publisher1").build();
        when(publisherService.findById(publisher.getId()))
                .thenReturn(Optional.of(publisher));

        file = BookFile.builder().id(9L).filename("file1").size(0L).build();
        when(bookFileService.findById(file.getId()))
                .thenReturn(Optional.of(file));

        book = Book.builder()
                .id(10L)
                .title("book1")
                .authors(auths)
                .tags(tags)
                .descript(descript)
                .publisher(publisher)
                .file(file)
                .pages(7)
                .rate(BookRate.INDIFFERENT)
                .state(BookState.STUDIED)
                .build();
        auths.stream().forEach(auth -> auth.addBook(book));
        tags.stream().forEach(tag -> tag.addBook(book));
        publisher.addBook(book);
        when(bookService.findById(book.getId())).thenReturn(Optional.of(book));

        publisherConverter = new PublisherConverter(publisherService);
        bookFileConverter = new BookFileConverter(bookFileService);
        descriptConverter = new DescriptConverter(descriptService);
        authorsConverter = new AuthorsConverter(authorService);
        tagsConverter = new TagsConverter(tagService);
        tagConverter = new TagConverter(tagService);
        fileHtmlConverter = new FileHtmlConverter();
        fileXmlConverter = new FileXmlConverter();
    }

    @Test
    public void testStringIdConverter() {
        assertNotNull(publisherConverter.convert(publisher.getId().toString()));
        String title = "Publisher7";
        val pub = publisherConverter.convert(title);
        assertNotNull(pub);
        assertEquals(title, pub.getName());
        assertNull(pub.getId());

        assertNotNull(bookFileConverter.convert(file.getId().toString()));
        assertNull(bookFileConverter.convert("file7"));

        assertNotNull(descriptConverter.convert(descript.getId().toString()));
        assertNull(descriptConverter.convert("Desc7"));

        tags.stream().forEach(tag -> {
            assertNotNull(tagConverter.convert(tag.getId().toString()));
            val tname = "tag7";
            val t = tagConverter.convert(tname);
            assertEquals(tname, t.getTag());
            assertNull(t.getId());
        });
    }

    @Test
    public void testStringSetConverter() {
        val listAuths = authorsConverter.convert(auths.stream()
                .map(Author::getName).collect(Collectors.joining(",")));
        assertNotNull(listAuths);
        assertEquals(3, listAuths.size());

        val listTags = tagsConverter.convert(tags.stream()
                .map(tag -> tag.getId().toString())
                .collect(Collectors.joining(",")));
        assertNotNull(listTags);
        assertEquals(3, listTags.size());
    }

    @Test
    public void testMultipartFileConverter() {
        val textDesc  = "<html></html>";
        val htmlFile = new MockMultipartFile("fileHtml", textDesc.getBytes(StandardCharsets.UTF_8));
        val desc = fileHtmlConverter.convert(htmlFile);
        assertEquals(textDesc, desc.getHtml());

        val textXml  = "<xml></xml>";
        val xmlFile = new MockMultipartFile("fileXml", textXml.getBytes(StandardCharsets.UTF_8));
        val bytes = fileXmlConverter.convert(xmlFile);
        assertEquals(textXml, new String(bytes, StandardCharsets.UTF_8));
    }
}
