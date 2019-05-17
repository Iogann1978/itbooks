package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.home.itbooks.model.*;
import ru.home.itbooks.service.BookService;
import ru.home.itbooks.service.PublisherService;
import ru.home.itbooks.service.TagService;

import javax.annotation.security.RolesAllowed;
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
            put("edit", "edit_book.html");
            put("del", "del_book.html");
            put("add", "add_book.html");
            put("descript", "descript.html");
            put("error", "error.html");
        }
    };

    @Autowired
    public BookController(BookService bookService, TagService tagService, PublisherService publisherService) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.publisherService = publisherService;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable Long id) {
        val book = bookService.findById(id);
        if(!book.isPresent()) {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        }
        model.addAttribute("book", book.get());
        return htmls.get("view");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable Long id) {
        val book = bookService.findById(id);
        model.asMap().forEach((k, v) -> log.info("key: {}, value: {}", k, v));
        if(!book.isPresent()) {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        }
        model.addAttribute("book", book.get());
        model.addAttribute("rates", BookRate.values());
        model.addAttribute("states", BookState.values());
        return htmls.get("edit");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/descript/{id}")
    public ModelAndView getBookDescript(@PathVariable Long id) {
        val book = bookService.findById(id);
        val view = new BytesView();
        book.ifPresent(b -> view.setHtml(b.getDescript().getText()));
        return new ModelAndView(view);
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
        return "books.html";
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String formAddBook(Model model) {
        val bookForm = new BookForm();
        model.addAttribute("bookForm", bookForm);
        model.addAttribute("rates", BookRate.values());
        model.addAttribute("states", BookState.values());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("publishers", publisherService.findAll());
        return "add_book.html";
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/add")
    public String addBook(@ModelAttribute("bookForm") BookForm bookForm) {
        val book = Book.builder()
                .title(bookForm.getTitle())
                .authors(null)
                .tags(null)
                .year(bookForm.getYear())
                .pages(bookForm.getPages())
                .publisher(bookForm.getPublisher())
                .rate(bookForm.getRate())
                .state(bookForm.getState())
                .build();
        bookService.save(book);
        return "redirect:list";
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/del")
    public String delBook(Model model, @ModelAttribute("id") Long id) {
        bookService.deleteById(id);
        return "books.html";
    }
}
