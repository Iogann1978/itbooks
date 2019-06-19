package ru.home.itbooks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.form.DescriptForm;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Descript implements Item<DescriptForm> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    @Column(length = 100000)
    private byte[] text;

    public String getHtml() {
        return text == null ? null : new String(text, StandardCharsets.UTF_8);
    }

    @Override
    public DescriptForm toItemForm() {
        return DescriptForm.builder()
                .id(id)
                .text(text)
                .build();
    }
}

