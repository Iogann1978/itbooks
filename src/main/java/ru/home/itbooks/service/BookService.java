package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.*;
import ru.home.itbooks.repository.BookRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookService extends AbstractService<Book, BookRepository> {
    @Autowired
    public BookService(BookRepository repository) {
        super(repository);
    }

    public Optional<Book> findById(long id) {
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
                .title("Java Concurrency In Practice")
                .publisher(new Publisher(null, "Addison Wesley Professional", null))
                .year(2006)
                .state(BookState.PLANNED)
                .rate(BookRate.GOOD)
                .authors(a)
                .descript(d)
                .build();
        val book = Optional.of(b);
        return book;
    }
}
