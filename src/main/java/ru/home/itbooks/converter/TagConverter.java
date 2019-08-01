package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.service.TagService;

@Component
@Slf4j
public class TagConverter implements Converter<String, Tag> {
    private TagService tagService;

    @Override
    @SneakyThrows
    public Tag convert(String s) {
        log.debug("TagConverter: {}", s);
        Tag tag = null;
        if(s != null && !s.isEmpty()) {
            if(s.matches("\\d+")) {
                tag = tagService.findById(Long.valueOf(s))
                        .orElseThrow(() -> new Exception(String.format("Тэг %s не найден!", s)));
            } else {
                tag = Tag.builder().tag(s).build();
            }
        }
        return tag;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
