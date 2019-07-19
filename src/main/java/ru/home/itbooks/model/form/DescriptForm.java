package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.Descript;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescriptForm implements ItemForm<Descript> {
    private Long id;
    private byte[] text;

    @Override
    public Descript toItem() {
        return Descript.builder()
                .id(id)
                .text(text)
                .build();
    }
}
