package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.Book;
import ru.home.itbooks.service.BookService;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/")
    public String getBook(Model model, @ModelAttribute("id") Long id) {
        val book = service.findById(id);
        if(book.isPresent()) {
            model.addAttribute("book", book.get());
        } else {
            model.addAttribute("book", "Книга не найдена!");
        }
        return "book.html";
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
