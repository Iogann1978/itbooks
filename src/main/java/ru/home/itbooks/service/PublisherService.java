package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.repository.PublisherRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PublisherService extends AbstractService<Publisher, PublisherRepository> {
    @Autowired
    public PublisherService(PublisherRepository repository) {
        super(repository);
    }

    public Iterable<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<Publisher>() {
            {
                add(new Publisher(0L, "Publisher 1", Collections.EMPTY_LIST));
                add(new Publisher(1L, "Publisher 2", Collections.EMPTY_LIST));
                add(new Publisher(3L, "Publisher 3", Collections.EMPTY_LIST));
            }
        };
        return publishers;
    }
}