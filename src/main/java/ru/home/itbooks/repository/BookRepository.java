package ru.home.itbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
