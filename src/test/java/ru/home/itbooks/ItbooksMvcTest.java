package ru.home.itbooks;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.itbooks.controller.*;
import ru.home.itbooks.converter.*;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.FindForm;
import ru.home.itbooks.service.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private Author author;
    private Publisher publisher;
    private Descript descript;
    private Book book;
    private BookFile file;
    private Tag tag;
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
    private AuthorController authorController;
    private BookFileController bookFileController;
    private PublisherController publisherController;
    private TagController tagController;

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

        tag = Tag.builder().id(1L).tag("Tag1").build();
        tags = new HashSet<Tag>() {
            {
                add(tag);
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

        file = BookFile.builder().id(7L).filename("file1").build();
        files = new ArrayList<BookFile>() {
            {
                add(file);
            }
        };
        when(bookFileService.findAll()).thenReturn(files);
        when(bookFileService.getFreeFiles()).thenReturn(files);
        files.stream().forEach(file ->
                when(bookFileService.findById(file.getId()))
                    .thenReturn(Optional.of(file)));

        author = Author.builder().id(8L).name("Author1").build();
        authors = new HashSet<Author>() {
            {
                add(author);
                add(Author.builder().id(9L).name("Author2").build());
                add(Author.builder().id(10L).name("Author3").build());
            }
        };
        when(authorService.findAll()).thenReturn(authors);
        authors.stream().forEach(auth ->
                when(authorService.findById(auth.getId()))
                        .thenReturn(Optional.of(auth)));

        descript = Descript.builder().id(11L)
                .text("<html></html>".getBytes(StandardCharsets.UTF_8)).build();
        when(descriptService.findById(descript.getId()))
                .thenReturn(Optional.of(descript));

        contents = "<contents></contents>".getBytes(StandardCharsets.UTF_8);
        book = Book.builder()
                .id(12L)
                .title("book1")
                .authors(authors)
                .tags(tags)
                .descript(descript)
                .publisher(publisher)
                .file(file)
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
        authorController = new AuthorController(authorService);
        bookFileController = new BookFileController(bookFileService);
        publisherController = new PublisherController(publisherService);
        tagController = new TagController(tagService);

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
                .standaloneSetup(bookController,
                        authorController,
                        bookFileController,
                        publisherController,
                        tagController)
                .setConversionService(fcs)
                .build();
    }

    @Test
    public void testBookController() throws Exception {
        mockMvc.perform(get("http://localhost:8081/book/list"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8081/book/{id}", book.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(post("http://localhost:8081/book/find/{action}", findAction))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorController() throws Exception {
        mockMvc.perform(get("http://localhost:8081/author/list"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8081/author/{id}", author.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testBookFileController() throws Exception {
        mockMvc.perform(get("http://localhost:8081/file/list"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8081/file/{id}", file.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPublisherController() throws Exception {
        mockMvc.perform(get("http://localhost:8081/publisher/list"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8081/publisher/{id}", publisher.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testTagController() throws Exception {
        mockMvc.perform(get("http://localhost:8081/tag/list"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8081/tag/{id}", tag.getId()))
                .andExpect(status().isOk());
    }
}
