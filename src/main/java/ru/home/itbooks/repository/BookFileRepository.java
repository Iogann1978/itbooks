package ru.home.itbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.BookFile;

import java.util.List;

@Repository
public interface BookFileRepository extends JpaRepository<BookFile, Long> {
    @Query(value = "SELECT f FROM BookFile f LEFT JOIN f.book b WHERE b IS NULL")
    List<BookFile> getFreeFiles();
}
