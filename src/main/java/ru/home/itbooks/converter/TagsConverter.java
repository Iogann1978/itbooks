package ru.home.itbooks.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.service.TagService;

@Component
@Slf4j
public class TagsConverter extends AbstractStringSetConverter<Tag, TagService> {
    @Autowired
    public TagsConverter(TagService tagService) {
        super(tagService, "Тэг");
    }

    @Override
    public Tag getNewItem(String s) {
        return getService().findById(Long.parseLong(s)).get();
    }
}
