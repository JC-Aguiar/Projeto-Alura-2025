package br.com.alura.projeto.course.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Optional<Course> getById(Long id) {
        if (id == null) throw new IllegalArgumentException(
            "Invalid operation: couldn't find a Course record because we receive a null id value"
        );
        return courseRepository.findById(id);
    }

    public Course create(Course course) {
        var code = course.getCode();
        var isCourseCodeDuplicated = courseRepository.existsByCode(code);
        if (isCourseCodeDuplicated) throw new IllegalArgumentException(
            "Code '%s' already exists in the system database. Please set a new unique code.".formatted(code)
        );
        return save(course);
    }

    public Course update(Course course) {
        return save(course);
    }

    private Course save(Course course) {
        if (course == null) throw new IllegalArgumentException(
            "Invalid operation: couldn't save a Course record because we receive a null register"
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

}
