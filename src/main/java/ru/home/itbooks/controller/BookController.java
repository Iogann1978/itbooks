package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.*;
import ru.home.itbooks.service.BookService;

import javax.annotation.security.RolesAllowed;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping(value = "/book")
@Slf4j
public class BookController {
    @Autowired
    private BookService service;
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

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable Long id) {
        val book = service.findById(id);
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
        val book = service.findById(id);
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
    public String getBookDescript(@PathVariable Long id) {
        val book = service.findById(id);
        val url = getClass().getClassLoader().getResource("WEB-INF/templates/descript.html");
        Path path = null;
        try {
            path = Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        if(book.isPresent() && path != null) {
            try {
                Files.write(path, book.get().getDescript().getText());
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                return htmls.get("error");
            }
        }

        return htmls.get("descript");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getBooks(Model model) {
        val count = service.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val books = service.findAll();
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
        model.addAttribute("tags", Arrays.asList(new Tag(1L, "Один", null), new Tag(2L, "Два", null), new Tag(3L, "Три", null)));
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
        service.save(book);
        return "redirect:list";
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/del")
    public String delBook(Model model, @ModelAttribute("id") Long id) {
        service.deleteById(id);
        return "books.html";
    }
}
