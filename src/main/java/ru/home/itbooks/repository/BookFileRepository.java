package ru.home.itbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.BookFile;

import java.util.List;

@Repository
public interface BookFileRepository extends JpaRepository<BookFile, Long> {
}
