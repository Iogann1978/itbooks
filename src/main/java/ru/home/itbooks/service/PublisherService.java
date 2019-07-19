package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.model.form.PublisherForm;
import ru.home.itbooks.repository.PublisherRepository;

@Service
public class PublisherService extends AbstractService<Publisher, PublisherForm, PublisherRepository> {
    private static final Sort sort = new Sort(Sort.Direction.ASC, "name");

    @Autowired
    public PublisherService(PublisherRepository repository) {
        super(repository);
    }

    @Override
    protected Sort getSort() {
        return sort;
    }
}