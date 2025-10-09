package br.com.alura.projeto.course.domain;

import br.com.alura.projeto.course.dto.CourseInfoDTO;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import br.com.alura.projeto.course.dto.SearchCourseDTO;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Data
@RestController
@RequestMapping("api")
public class CourseApiController {

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final CourseMapper courseMapper;


    @GetMapping("/user/courses")
    public ResponseEntity<Page<CourseInfoDTO>> list(
        Pageable pageable,
        @ModelAttribute @Valid SearchCourseDTO dto) {

        log.info("Receive request to list all courses within range configuration: {}", pageable);
        log.info(dto.toString());
        var example = Example.of(courseMapper.toEntity(dto));
        var courses = courseService.list(example, pageable);
        var totalSize = courses.getTotalElements();
        var result = courses.get()
            .map(courseMapper::toGenericResponseDTO)
            .toList();
        var dtoPage = new PageImpl<>(result, pageable, totalSize);
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    @PostMapping("/user/course/new")
    public ResponseEntity<CourseInfoDTO> create(@RequestBody @Valid NewCourseFormDTO dto) {
        log.info("Creating new course requested by admin.");
        log.info(dto.toString());
        var newCourse = courseService.save(courseMapper.toEntity(dto));
        var result = courseMapper.toGenericResponseDTO(newCourse);
        return ResponseEntity.ok(result);
    }

    @Transactional
    @PutMapping("/user/course/save/{id}")
    public ResponseEntity<CourseInfoDTO> save(
        @PathParam("id") Long id,
        @Valid NewCourseFormDTO dto) {

        log.info("Updating course ID {}.", id);
        log.info(dto.toString());
        var entity = courseMapper.toEntity(dto, id);
        var newCourse = courseService.save(entity);
        var result = courseMapper.toGenericResponseDTO(newCourse);
        return ResponseEntity.ok(result);
    }

}
