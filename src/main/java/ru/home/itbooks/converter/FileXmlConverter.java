package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

public class FileXmlConverter extends AbstractMultipartFileConverter<byte[]> {
    @Override
    @SneakyThrows
    public byte[] getItem(MultipartFile s) {
        return s.getBytes();
    }
}
