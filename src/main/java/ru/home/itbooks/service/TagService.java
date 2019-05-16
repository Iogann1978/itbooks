package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.repository.TagRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagService extends AbstractService<Tag, TagRepository> {
    @Autowired
    public TagService(TagRepository repository) {
        super(repository);
    }

    public Iterable<Tag> findAll() {
        List<Tag> tags = new ArrayList<Tag>() {
            {
                add(new Tag(0L, "Tag 1", Collections.EMPTY_LIST));
                add(new Tag(1L, "Tag 2", Collections.EMPTY_LIST));
                add(new Tag(3L, "Tag 3", Collections.EMPTY_LIST));
            }
        };
        return tags;
    }
}
