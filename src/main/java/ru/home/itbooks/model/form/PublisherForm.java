package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.Publisher;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherForm implements ItemForm<Publisher> {
    private Long id;
    private String name;

    @Override
    public Publisher toItem() {
        return Publisher.builder()
                .id(id)
                .name(name)
                .build();
    }
}
