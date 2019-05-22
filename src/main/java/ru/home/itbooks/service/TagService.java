package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.repository.TagRepository;

@Service
public class TagService extends AbstractService<Tag, TagRepository> {
    @Autowired
    public TagService(TagRepository repository) {
        super(repository);
    }
}
