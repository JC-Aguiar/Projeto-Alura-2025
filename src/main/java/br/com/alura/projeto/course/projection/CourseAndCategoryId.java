package br.com.alura.projeto.course.projection;

import br.com.alura.projeto.course.domain.Course;

public interface CourseAndCategoryId {
    Course getCourse();
    Long getCategoryId();
}
