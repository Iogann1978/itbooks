package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.repository.PublisherRepository;

import java.util.*;

@Service
public class PublisherService extends AbstractService<Publisher, PublisherRepository> {
    @Autowired
    public PublisherService(PublisherRepository repository) {
        super(repository);
    }

    public Iterable<Publisher> findAll() {
        Set<Publisher> publishers = new HashSet<Publisher>() {
            {
                add(new Publisher(0L, "Publisher 1", Collections.EMPTY_SET));
                add(new Publisher(1L, "Publisher 2", Collections.EMPTY_SET));
                add(new Publisher(2L, "Publisher 3", Collections.EMPTY_SET));
            }
        };
        return publishers;
    }
}