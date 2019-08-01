package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import ru.home.itbooks.model.Descript;

public class FileHtmlConverter extends AbstractMultipartFileConverter<Descript> {
    @Override
    @SneakyThrows
    public Descript getItem(MultipartFile s) {
        return Descript.builder().text(s.getBytes()).build();
    }
}