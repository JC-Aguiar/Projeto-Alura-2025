package br.com.alura.projeto.course.domain;

import br.com.alura.projeto.course.dto.CourseInfoDTO;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import br.com.alura.projeto.course.dto.SearchCourseDTO;
import br.com.alura.projeto.course.dto.UpdateCourseFormDTO;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseInfoDTO toGenericResponseDTO(Course entity) {
        if (entity == null) return null;
        return new CourseInfoDTO(
            entity.getId(),
            entity.getName(),
            entity.getCode(),
            entity.getDescription().orElse(null),
            entity.getInstructorEmail(),
            entity.getStatus(),
            entity.getInactivationDate().orElse(null)
        );
    }

    public Course toEntity(SearchCourseDTO dto) {
        if (dto == null) return null;
        return Course
            .builder()
            .name(dto.name())
            .code(dto.code())
            .description(dto.description())
            .instructorEmail(dto.instructorEmail())
            .status(dto.status())
            .inactivationDate(dto.inactivationDate())
            .build();
    }

    public Course toEntity(NewCourseFormDTO dto) {
        if (dto == null) return null;
        return Course
            .builder()
            .name(dto.getName())
            .code(dto.getCode())
            .description(dto.getDescription())
            .instructorEmail(dto.getInstructorEmail())
            .build();
    }

    public Course toEntity(UpdateCourseFormDTO dto) {
        if (dto == null) return null;
        return Course
            .builder()
            .name(dto.name())
            .code(dto.code())
            .description(dto.description())
            .instructorEmail(dto.instructorEmail())
            .status(dto.status())
            .inactivationDate(dto.inactivationDate())
            .build();
    }


}
