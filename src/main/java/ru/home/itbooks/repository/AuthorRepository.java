package ru.home.itbooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
