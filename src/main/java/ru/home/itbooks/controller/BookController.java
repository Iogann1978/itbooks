package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.BookForm;
import ru.home.itbooks.service.BookFileService;
import ru.home.itbooks.service.BookService;
import ru.home.itbooks.service.PublisherService;
import ru.home.itbooks.service.TagService;

import javax.annotation.security.RolesAllowed;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

@Controller
@RequestMapping(value = "/book")
@Slf4j
public class BookController extends AbstractController<Book, BookForm, BookService> {
    private TagService tagService;
    private PublisherService publisherService;
    private BookFileService bookFileService;

    @Autowired
    public BookController(BookService bookService,
                          TagService tagService,
                          PublisherService publisherService,
                          BookFileService bookFileService) {
        super(bookService);
        this.tagService = tagService;
        this.publisherService = publisherService;
        this.bookFileService = bookFileService;
        setViewHtml("book.html");
        setListHtml("books.html");
        setEditHtml("edit_book.html");
        setDelHtml("del_book.html");
        setAddHtml("add_book.html");
    }

    @Override
    protected void itemFormModel(Model model, BookForm bookForm) {
        model.addAttribute("bookForm", bookForm);
        model.addAttribute("rates", BookRate.values());
        model.addAttribute("states", BookState.values());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("publishers", publisherService.findAll());
        model.addAttribute("files", bookFileService.findAll());
    }

    @Override
    protected void itemModel(Model model, Book book) {
        model.addAttribute("book", book);
    }

    @Override
    protected void listModel(Model model, Iterable books) {
        model.addAttribute("books", books);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable Long id) {
        return get("Книга не найдена!", model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable Long id) {
        return edit(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/descript/{id}")
    public ModelAndView getBookDescript(@PathVariable Long id) {
        val book = getService().findById(id);
        val view = new BytesView(book.map(b -> b.getDescript()).map(d -> d.getText()).orElse(null));
        return new ModelAndView(view);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/contents/{id}")
    public ModelAndView getBookContents(@PathVariable Long id) {
        val view = new ModelAndView("contents");
        getService().findById(id).ifPresent(b -> {
            if(b.getContents() != null) {
                val bais = new ByteArrayInputStream(b.getContents());
                view.addObject("xmlSource", new StreamSource(bais));
            }
        });
        return view;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getBooks(Model model) {
        return getList(model);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addBook(Model model) {
        val bookForm = new BookForm();
        return add(model, bookForm);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("bookForm") BookForm bookForm) {
        return save(bookForm);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delBook(Model model, @PathVariable Long id) {
        return del(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delBook(@ModelAttribute("bookForm") BookForm bookForm) {
        return del(bookForm);
    }
}
