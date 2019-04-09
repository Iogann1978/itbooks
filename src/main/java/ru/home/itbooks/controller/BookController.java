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
import javax.swing.text.html.Option;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @GetMapping(params = {"id"})
    public String getBook(Model model, @RequestParam long id) {
        //val book = service.findById(id);
        val a = new ArrayList<Author>() {
            {
                add(new Author(null, "Brian", "Göetz", null));
                add(new Author(null, "Tim", "Peierls", null));
                add(new Author(null, "Joshua","Blochn", null));
                add(new Author(null, "Joseph","Bowbeer", null));
                add(new Author(null, "David","Holmes", null));
                add(new Author(null, "Doug","Lea", null));
            }
        };
        val d = new Descript(null, "<html><body><h1>Descript</h1><hr/></body></html>".getBytes());
        val b = Book.builder()
                .id(id)
                .title("Java Concurrency In Practice")
                .authors(a)
                .publisher(new Publisher(null, "Addison Wesley Professional", null))
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .descript(d)
                .build();
        val book = Optional.of(b);
        if(!book.isPresent()) {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
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
                .publisher(new Publisher(null, "Addison Wesley Professional", null))
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .build();
        val book = Optional.of(b);
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
    public String getBookDescript(Model model, @PathVariable Long id) {
        //val book = service.findById(id);
        val d = new Descript(null, "<html><body><h1>Descript</h1><hr/></body></html>".getBytes());
        val b = Book.builder()
                .id(id)
                .title("Java Concurrency In Practice")
                .publisher(new Publisher(null, "Addison Wesley Professional", null))
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .descript(d)
                .build();
        val book = Optional.of(b);

        val url = getClass().getClassLoader().getResource("WEB-INF/templates/descript.html");
        File file = null;
        try {
            file = new File(url.toURI().getPath());
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        if(book.isPresent() && file != null && file.exists()) {
            try (val fos = new FileOutputStream(file)) {
                fos.write(book.get().getDescript().getText());
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
