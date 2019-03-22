package ru.home.itbooks.service;

import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Book;
import ru.home.itbooks.repository.BookRepository;

@Service
public class BookService extends AbstractService<Book, BookRepository> {
}
