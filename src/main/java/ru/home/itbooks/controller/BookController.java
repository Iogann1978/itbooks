package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.home.itbooks.model.Book;
import ru.home.itbooks.service.BookService;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@Controller
@RequestMapping(value = "/book")
@Slf4j
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping(params = {"action", "id"})
    public ModelAndView getBook(@RequestParam String action, @RequestParam long id) {
        val model = new ModelAndView();
        //val book = service.findById(id);
        val book = Optional.of(new Book());
        if("edit".equals(action)) {
            book.get().setTitle("Editing book");
            book.get().setAuthor("Editor");
        } else {
            book.get().setTitle("Another book");
            book.get().setAuthor("Unknown");
        }
        if(book.isPresent()) {
            model.addObject("book", book.get());
        } else {
            model.addObject("book", "Книга не найдена!");
        }
        model.setViewName("book.html");
        return model;
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
