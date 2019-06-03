package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFileForm implements ItemForm {
    private Long id;
    private String filename;
    private Long size;
}
