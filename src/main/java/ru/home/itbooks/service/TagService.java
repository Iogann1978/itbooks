package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.model.form.TagForm;
import ru.home.itbooks.repository.TagRepository;

@Service
public class TagService extends AbstractService<Tag, TagForm, TagRepository> {
    private static final Sort sort = new Sort(Sort.Direction.ASC, "tag");

    @Autowired
    public TagService(TagRepository repository) {
        super(repository);
    }

    public Tag save(TagForm tagForm) {
        val tag = Tag.builder()
                .id(tagForm.getId())
                .tag(tagForm.getName()).build();
        return getRepository().save(tag);
    }

    @Override
    protected Sort getSort() {
        return sort;
    }
}
