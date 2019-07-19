package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.Tag;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagForm implements ItemForm<Tag> {
    private Long id;
    private String name;

    @Override
    public Tag toItem() {
        return Tag.builder()
                .id(id)
                .tag(name)
                .build();
    }
}
