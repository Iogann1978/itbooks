package ru.home.itbooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
