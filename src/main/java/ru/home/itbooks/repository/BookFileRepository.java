package ru.home.itbooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.BookFile;

@Repository
public interface BookFileRepository extends CrudRepository<BookFile, Long> {
}
