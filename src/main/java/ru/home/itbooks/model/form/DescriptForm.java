package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescriptForm implements ItemForm {
    private Long id;
    private byte[] text;
}
