package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.service.BookFileService;

@Component
@Slf4j
public class BookFileConverter implements Converter<String, BookFile> {
    private BookFileService bookFileService;

    @Override
    @SneakyThrows
    public BookFile convert(String id) {
        log.debug("BookFileConverter: {}", id);
        BookFile file = null;
        if(id != null && !id.isEmpty()) {
            file = bookFileService.findById(Long.valueOf(id))
                    .orElseThrow(() -> new Exception(String.format("Файл %s не найден!", id)));
        }
        return file;
    }

    @Autowired
    public void setBookFileService(BookFileService bookFileService) {
        this.bookFileService = bookFileService;
    }
}
