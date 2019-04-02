package ru.home.itbooks.service;

import org.springframework.stereotype.Service;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.repository.BookFileRepository;

@Service
public class BookFileService extends AbstractService<BookFile, BookFileRepository> {
}
