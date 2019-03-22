package ru.home.itbooks.service;

import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.repository.TagRepository;

@Service
public class TagService extends AbstractService<Tag, TagRepository> {
}
