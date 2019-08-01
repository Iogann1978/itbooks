package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;
import ru.home.itbooks.model.Descript;

@Slf4j
public class FileHtmlConverter implements Converter<MultipartFile, Descript> {
    @Override
    @SneakyThrows
    public Descript convert(MultipartFile multipartFile) {
        log.debug("FileHtmlConverter");
        Descript desc = new Descript();
        if(multipartFile != null && !multipartFile.isEmpty()) {
            desc.setText(multipartFile.getBytes());
        }
        return desc;
    }
}