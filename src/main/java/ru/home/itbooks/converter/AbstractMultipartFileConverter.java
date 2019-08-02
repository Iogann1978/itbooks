package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class AbstractMultipartFileConverter<T>
        implements ItemConverter<T, MultipartFile>, Converter<MultipartFile, T> {
    @Override
    @SneakyThrows
    public T convert(MultipartFile multipartFile) {
        log.debug(getClass().getSimpleName());
        if(multipartFile != null && !multipartFile.isEmpty()) {
            return getItem(multipartFile);
        } else {
            return null;
        }
    }
}
