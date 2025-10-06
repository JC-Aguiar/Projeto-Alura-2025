package br.com.alura.projeto.login;

import br.com.alura.projeto.category.projection.SimpleCategoryAndCourse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleCategoryAndCourseTestImpl implements SimpleCategoryAndCourse {

    String course;
    String category;
    String categoryCode;
    String categoryColor;
    int categoryOrder;

}
