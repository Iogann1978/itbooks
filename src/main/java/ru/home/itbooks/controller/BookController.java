package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.model.Book;
import ru.home.itbooks.model.BookRate;
import ru.home.itbooks.model.BookState;
import ru.home.itbooks.service.BookService;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@Controller
@RequestMapping(value = "/book")
@Slf4j
public class BookController {
    @Autowired
    private BookService service;
    private static final Map<String, String> htmls = new HashMap() {
        {
            put("view", "book.html");
            put("edit", "edit_book.html");
            put("del", "del_book.html");
            put("add", "add_book.html");
        }
    };

    @RolesAllowed("USER,ADMIN")
    @GetMapping(params = {"id"})
    public String getBook(Model model, @RequestParam long id) {
        //val book = service.findById(id);
        val a = new ArrayList<Author>() {
            {
                add(new Author(null, "Brian","Göetz"));
                add(new Author(null, "Tim","Peierls"));
                add(new Author(null, "Joshua","Blochn"));
                add(new Author(null, "Joseph","Bowbeer"));
                add(new Author(null, "David","Holmes"));
                add(new Author(null, "Doug","Lea"));
            }
        };
        val b = Book.builder()
                .id(id)
                .title("Java Concurrency In Practice")
                .authors(a)
                .publisher("Addison Wesley Professional")
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .build();
        val book = Optional.of(b);
        if(!book.isPresent()) {
            model.addAttribute("error", "Книга не найдена!");
            return "error.html";
        }
        model.addAttribute("book", book.get());
        return htmls.get("view");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping(value = "/edit", params = {"id"})
    public String editBook(Model model, @RequestParam long id) {
        //val book = service.findById(id);
        model.asMap().forEach((k, v) -> log.info("key: {}, value: {}", k, v));
        val b = Book.builder()
                .title("Java Concurrency In Practice")
                .publisher("Addison Wesley Professional")
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .build();
        val book = Optional.of(b);
        if(!book.isPresent()) {
            model.addAttribute("error", "Книга не найдена!");
            return "error.html";
        }
        model.addAttribute("book", book.get());
        model.addAttribute("rates", BookRate.values());
        model.addAttribute("states", BookState.values());
        return htmls.get("edit");
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
        val book = new Book();
        model.addAttribute("book", book);
        return "addbook.html";
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/add")
    public String addBook(Model model, @ModelAttribute("book") Book book) {
        val nbook = service.save(book);
        model.addAttribute("book", nbook);
        return "redirect:list";
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/del")
    public String delBook(Model model, @ModelAttribute("id") Long id) {
        service.deleteById(id);
        return "books.html";
    }
}
