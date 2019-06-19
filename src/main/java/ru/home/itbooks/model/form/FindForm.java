package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.BookState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindForm {
    private String title, descript, contents;
    private Long authorId, publisherId, tagId;
    private BookState state;
}
