package ru.home.itbooks.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.service.BookFileService;

@Component
@Slf4j
public class BookFileConverter extends AbstractStringIdConverter<BookFile, BookFileService> {

    @Autowired
    public BookFileConverter(BookFileService bookFileService) {
        super(bookFileService, "BookFileConverter", "BookFile");
    }

    @Override
    public BookFile getNewItem() {
        return null;
    }
}
