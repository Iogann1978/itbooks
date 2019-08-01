package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.service.TagService;

@Component
public class TagsConverter extends AbstractStringSetConverter<Tag, TagService> {
    @Autowired
    public TagsConverter(TagService tagService) {
        super(tagService);
    }

    @Override
    @SneakyThrows
    public Tag getItem(String s) {
        return getService().findById(Long.parseLong(s))
                .orElseThrow(() -> new Exception(String.format("Тэг %s не найден!", s)));
    }
}
