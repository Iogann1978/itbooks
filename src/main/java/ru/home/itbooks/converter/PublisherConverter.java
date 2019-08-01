package ru.home.itbooks.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.service.PublisherService;

@Component
public class PublisherConverter extends AbstractStringIdConverter<Publisher, PublisherService> {
    @Autowired
    public PublisherConverter(PublisherService publisherService) {
        super(publisherService, "Издатель");
    }

    @Override
    public Publisher getItem(String s) {
        return Publisher.builder().name(s).build();
    }
}
