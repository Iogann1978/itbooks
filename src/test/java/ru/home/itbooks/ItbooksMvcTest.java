package ru.home.itbooks;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.home.itbooks.config.WebMvcConfig;
import ru.home.itbooks.controller.BookController;
import ru.home.itbooks.converter.*;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.FindForm;
import ru.home.itbooks.service.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {WebMvcConfig.class})
//@WebMvcTest(controllers = {BookController.class})
public class ItbooksMvcTest {
    private MockMvc mockMvc;

    @Mock
    private TagService tagService;
    @Mock
    private PublisherService publisherService;
    @Mock
    private BookFileService bookFileService;
    @Mock
    private AuthorService authorService;
    @Mock
    private DescriptService descriptService;
    @Mock
    private BookService bookService;

    private Set<Tag> tags;
    private Set<Publisher> publishers;
    private List<BookFile> files;
    private Set<Author> authors;
    private Publisher publisher;
    private Descript descript;
    private Book book;
    private byte[] contents;

    private FindForm findForm;
    private String findAction = "title";

    private PublisherConverter publisherConverter;
    private BookFileConverter bookFileConverter;
    private DescriptConverter descriptConverter;
    private AuthorsConverter authorsConverter;
    private TagsConverter tagsConverter;
    private TagConverter tagConverter;
    private FileHtmlConverter fileHtmlConverter;
    private  FileXmlConverter fileXmlConverter;

    private BookController bookController;

    @Before
    public void setUp() {
        publisherConverter = new PublisherConverter(publisherService);
        bookFileConverter = new BookFileConverter(bookFileService);
        descriptConverter = new DescriptConverter(descriptService);
        authorsConverter = new AuthorsConverter(authorService);
        tagsConverter = new TagsConverter(tagService);
        tagConverter = new TagConverter(tagService);
        fileHtmlConverter = new FileHtmlConverter();
        fileXmlConverter = new FileXmlConverter();

        tags = new HashSet<Tag>() {
            {
                add(Tag.builder().id(1L).tag("Tag1").build());
                add(Tag.builder().id(2L).tag("Tag2").build());
                add(Tag.builder().id(3L).tag("Tag3").build());
            }
        };
        when(tagService.findAll()).thenReturn(tags);
        tags.stream().forEach(tag ->
                when(tagService.findById(tag.getId()))
                        .thenReturn(Optional.of(tag)));

        publisher = Publisher.builder().id(4L).name("Publisher1").build();
        publishers = new HashSet<Publisher>() {
            {
                add(publisher);
                add(Publisher.builder().id(5L).name("Publisher2").build());
                add(Publisher.builder().id(6L).name("Publisher3").build());
            }
        };
        when(publisherService.findAll()).thenReturn(publishers);
        publishers.stream().forEach(publisher ->
                when(publisherService.findById(publisher.getId()))
                        .thenReturn(Optional.of(publisher)));

        files = new ArrayList<BookFile>() {
            {
                add(BookFile.builder().id(7L).filename("file1").build());
            }
        };
        when(bookFileService.getFreeFiles()).thenReturn(files);
        files.stream().forEach(file ->
                when(bookFileService.findById(file.getId()))
                    .thenReturn(Optional.of(file)));

        authors = new HashSet<Author>() {
            {
                add(Author.builder().id(8L).name("Author1").build());
                add(Author.builder().id(9L).name("Author2").build());
                add(Author.builder().id(10L).name("Author3").build());
            }
        };
        when(authorService.findAll()).thenReturn(authors);

        descript = Descript.builder().id(11L)
                .text("<html></html>".getBytes(StandardCharsets.UTF_8)).build();
        when(descriptService.findById(descript.getId()))
                .thenReturn(Optional.of(descript));

        contents = "<contents></contents>".getBytes(StandardCharsets.UTF_8);
        book = Book.builder()
                .id(10L)
                .title("book1")
                .authors(authors)
                .tags(tags)
                .descript(descript)
                .publisher(publisher)
                .file(files.get(0))
                .pages(7)
                .rate(BookRate.INDIFFERENT)
                .state(BookState.STUDIED)
                .contents(contents)
                .build();
        authors.stream().forEach(auth -> auth.addBook(book));
        tags.stream().forEach(tag -> tag.addBook(book));
        publishers.stream().forEach(publisher -> publisher.addBook(book));
        when(bookService.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookService.save(book, descript, contents)).thenReturn(book);

        findForm = FindForm.builder().title(book.getTitle()).build();
        when(bookService.findBook(findForm, findAction)).thenReturn(Arrays.asList(book));

        bookController = new BookController(bookService,
                tagService,
                publisherService,
                bookFileService,
                authorService);

        val fcs = new FormattingConversionService();
        fcs.addConverter(publisherConverter);
        fcs.addConverter(bookFileConverter);
        fcs.addConverter(descriptConverter);
        fcs.addConverter(authorsConverter);
        fcs.addConverter(tagsConverter);
        fcs.addConverter(tagConverter);
        fcs.addConverter(fileHtmlConverter);
        fcs.addConverter(fileXmlConverter);
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .setConversionService(fcs).build();
    }

    @Test
    public void testBookController() throws Exception {
        mockMvc.perform(get("book/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
