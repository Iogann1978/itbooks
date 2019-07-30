package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.service.PublisherService;

@Component
public class PublisherConverter implements Converter<Long, Publisher> {
    private PublisherService publisherService;

    @Override
    @SneakyThrows
    public Publisher convert(Long aLong) {
        Publisher pub = null;
        if(aLong != null) {
            pub = publisherService.findById(aLong)
                    .orElseThrow(() -> new Exception(String.format("Издатель %s не найден!", aLong)));
        }
        return pub;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
}
