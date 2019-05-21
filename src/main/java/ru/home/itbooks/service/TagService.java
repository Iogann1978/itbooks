package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.repository.TagRepository;

import java.util.*;

@Service
public class TagService extends AbstractService<Tag, TagRepository> {
    @Autowired
    public TagService(TagRepository repository) {
        super(repository);
    }

    public Optional<Tag> findByTag(String tag) {
        return repository.findByTag(tag);
    }

    public Iterable<Tag> findAll() {
        Set<Tag> tags = new HashSet<Tag>() {
            {
                add(new Tag(3L, "Tag 1", Collections.EMPTY_SET));
                add(new Tag(4L, "Tag 2", Collections.EMPTY_SET));
                add(new Tag(5L, "Tag 3", Collections.EMPTY_SET));
            }
        };
        return tags;
    }
}
