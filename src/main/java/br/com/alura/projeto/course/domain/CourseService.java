package br.com.alura.projeto.course.domain;

import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.course.projection.CourseAndCategoryId;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final CategoryRepository categoryRepository;


    public Optional<Course> getById(Long id) {
        if (id == null) return Optional.empty();
        return courseRepository.findById(id);
    }


    public Optional<CourseAndCategoryId> findCourseAndCategoryIdByCourseBy(Long id) {
        var projection = courseRepository.findCourseAndCategoryIdByCourseBy(id);
        var isValidProjection = projection.getCourse() != null && projection.getCategoryId() != null;
        return Optional.ofNullable(
            isValidProjection ? projection : null
        );
    }

    public Course save(Course course, Long categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(
            () -> new IllegalArgumentException(
                "The category related to this course is missing. Please enter a valid value."
        ));
        course.setCategory(category);
        return save(course);
    }

    public Course save(Course course) {
        var id = course.getId();
        var code = course.getCode();
        var isCourseCodeDuplicated = courseRepository.countUniqueCodePerId(code, id) > 0;
        if (isCourseCodeDuplicated) throw new IllegalArgumentException(
            "Code '%s' already exists in the system database. Please set a new unique code.".formatted(code)
        );
        return courseRepository.save(course);
    }

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public List<Course> list(Example<Course> courseExample) {
        return courseRepository.findAll(courseExample);
    }

    public Page<Course> list(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Page<Course> list(Example<Course> courseExample, Pageable pageable) {
        return courseRepository.findAll(courseExample, pageable);
    }

    public boolean deactivateCourseByCode(String code) {
        return courseRepository.updateStatusByCode(code) > 0;
    }

}
