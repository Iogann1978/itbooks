package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.repository.BookFileRepository;

@Service
public class BookFileService extends AbstractService<BookFile, BookFileRepository> {
    @Autowired
    public BookFileService(BookFileRepository repository) {
        super(repository);
    }
}
