package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.service.PublisherService;

@Component
@Slf4j
public class PublisherConverter implements Converter<String, Publisher> {
    private PublisherService publisherService;

    @Override
    @SneakyThrows
    public Publisher convert(String s) {
        log.debug("PublisherConverter: {}", s);
        Publisher pub = null;
        if(s != null && !s.isEmpty()) {
            if(s.matches("\\d+")) {
                pub = publisherService.findById(Long.valueOf(s))
                        .orElseThrow(() -> new Exception(String.format("Издатель %s не найден!", s)));
            } else {
                pub = Publisher.builder().name(s).build();
            }
        }
        return pub;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
}
