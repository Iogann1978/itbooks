package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ContentsConverter implements Converter<MultipartFile, byte[]> {
    @Override
    @SneakyThrows
    public byte[] convert(MultipartFile multipartFile) {
        byte[] contents = null;
        if(multipartFile != null && !multipartFile.isEmpty()) {
            contents = multipartFile.getBytes();
        }
        return contents;
    }
}
