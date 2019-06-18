package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.model.form.PublisherForm;
import ru.home.itbooks.model.form.TagForm;
import ru.home.itbooks.repository.PublisherRepository;

@Service
public class PublisherService extends AbstractService<Publisher, PublisherForm, PublisherRepository> {
    @Autowired
    public PublisherService(PublisherRepository repository) {
        super(repository);
    }

    public Publisher save(PublisherForm publisherForm) {
        val publisher = Publisher.builder()
                .id(publisherForm.getId())
                .name(publisherForm.getName()).build();
        return repository.save(publisher);
    }
}