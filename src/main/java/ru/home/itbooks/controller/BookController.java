package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.BookForm;
import ru.home.itbooks.service.BookService;
import ru.home.itbooks.service.PublisherService;
import ru.home.itbooks.service.TagService;

import javax.annotation.security.RolesAllowed;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/book")
@Slf4j
public class BookController {
    private BookService bookService;
    private TagService tagService;
    private PublisherService publisherService;
    private static final Map<String, String> htmls = new HashMap<String, String>() {
        {
            put("view", "book.html");
            put("books", "books.html");
            put("edit", "edit_book.html");
            put("del", "del_book.html");
            put("add", "add_book.html");
            put("error", "error.html");
        }
    };

    @Autowired
    public BookController(BookService bookService,
                          TagService tagService,
                          PublisherService publisherService,
                          ResourceLoader resourceLoader) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.publisherService = publisherService;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable Long id) {
        val book = bookService.findById(id);
        val result = book.map(b -> {
            model.addAttribute("book", b);
            return htmls.get("view");
        }).orElseGet(() -> {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable Long id) {
        val book = bookService.findById(id);
        val result = book.map(b -> {
            val bookForm = BookForm.builder()
                    .id(b.getId())
                    .title(b.getTitle())
                    .authors(String.join(";", b.getAuthors().stream().map(a -> a.getName()).toArray(String[]::new)))
                    .publisher(b.getPublisher().getId())
                    .pages(b.getPages())
                    .year(b.getYear())
                    .rate(b.getRate())
                    .state(b.getState())
                    .fileHtml(new MockMultipartFile("fileXml", b.getContents()))
                    .build();
            if(b.getDescript() != null) {
                bookForm.setFileHtml(new MockMultipartFile("fileHtml", b.getDescript().getText()));
            }
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("rates", BookRate.values());
            model.addAttribute("states", BookState.values());
            model.addAttribute("publishers", publisherService.findAll());
            return htmls.get("edit");
        }).orElseGet(() -> {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/descript/{id}")
    public ModelAndView getBookDescript(@PathVariable Long id) {
        val book = bookService.findById(id);
        val view = new BytesView(book.map(b -> b.getDescript()).map(d -> d.getText()).orElse(null));
        return new ModelAndView(view);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/contents/{id}")
    public ModelAndView getBookContents(@PathVariable Long id) {
        val view = new ModelAndView("contents");;
        bookService.findById(id).ifPresent(b -> {
            val bais = new ByteArrayInputStream(b.getContents());
            view.addObject("xmlSource", new StreamSource(bais));
        });
        return view;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getBooks(Model model) {
        val count = bookService.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val books = bookService.findAll();
            model.addAttribute("books", books);
        }
        return htmls.get("books");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addBook(Model model) {
        val bookForm = new BookForm();
        model.addAttribute("bookForm", bookForm);
        model.addAttribute("rates", BookRate.values());
        model.addAttribute("states", BookState.values());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("publishers", publisherService.findAll());
        return htmls.get("add");
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("bookForm") BookForm bookForm) {
        bookService.save(bookForm);
        return "redirect:list";
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/del")
    public String delBook(Model model, @ModelAttribute("id") Long id) {
        bookService.deleteById(id);
        return htmls.get("books");
    }
}
