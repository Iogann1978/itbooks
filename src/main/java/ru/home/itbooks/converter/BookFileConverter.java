package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.service.BookFileService;

@Component
public class BookFileConverter implements Converter<Long, BookFile> {
    private BookFileService bookFileService;

    @Override
    @SneakyThrows
    public BookFile convert(Long aLong) {
        BookFile file = null;
        if(aLong != null) {
            file = bookFileService.findById(aLong)
                    .orElseThrow(() -> new Exception(String.format("Файл %s не найден!", aLong)));
        }
        return file;
    }

    @Autowired
    public void setBookFileService(BookFileService bookFileService) {
        this.bookFileService = bookFileService;
    }
}
