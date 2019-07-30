package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.service.TagService;

import java.util.HashSet;
import java.util.Set;

@Component
public class TagsConverter implements Converter<String, Set<Tag>> {
    private TagService tagService;

    @Override
    @SneakyThrows
    public Set<Tag> convert(String s) {
        Set<Tag> listTags = new HashSet<>();
        if(s != null && !s.isEmpty()) {
            val tags = s.split(",");
            for(val tag : tags) {
                val tg = tagService.findById(Long.parseLong(tag))
                        .orElseThrow(() -> new Exception(String.format("Тэг %s не найден!", tag)));
                listTags.add(tg);
            }
        }
        return listTags;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
