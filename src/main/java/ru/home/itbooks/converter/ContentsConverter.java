package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class ContentsConverter implements Converter<MultipartFile, byte[]> {
    @Override
    @SneakyThrows
    public byte[] convert(MultipartFile multipartFile) {
        log.debug("ContentsConverter");
        byte[] contents = null;
        if(multipartFile != null && !multipartFile.isEmpty()) {
            contents = multipartFile.getBytes();
        }
        return contents;
    }
}
