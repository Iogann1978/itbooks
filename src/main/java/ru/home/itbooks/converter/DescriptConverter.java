package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.home.itbooks.model.Descript;

@Component
@Slf4j
public class DescriptConverter implements Converter<MultipartFile, Descript> {
    @Override
    @SneakyThrows
    public Descript convert(MultipartFile multipartFile) {
        log.debug("DescriptConverter");
        Descript desc = new Descript();
        if(multipartFile != null && !multipartFile.isEmpty()) {
            desc.setText(multipartFile.getBytes());
        }
        return desc;
    }
}