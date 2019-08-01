package ru.home.itbooks.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.service.TagService;

@Component
public class TagConverter extends AbstractStringIdConverter<Tag, TagService> {
    @Autowired
    public TagConverter(TagService tagService) {
        super(tagService, "Тэг");
    }

    @Override
    public Tag getItem(String s) {
        return Tag.builder().tag(s).build();
    }
}
