package ru.home.itbooks.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.service.BookFileService;

@Component
public class BookFileConverter extends AbstractStringIdConverter<BookFile, BookFileService> {

    @Autowired
    public BookFileConverter(BookFileService bookFileService) {
        super(bookFileService, "Файл книги");
    }

    @Override
    public BookFile getItem(String s) {
        return null;
    }
}
