package ru.home.itbooks.service;

import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.repository.PublisherRepository;

@Service
public class PublisherService extends AbstractService<Publisher, PublisherRepository> {
}
