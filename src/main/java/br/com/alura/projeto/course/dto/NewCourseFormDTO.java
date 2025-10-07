package br.com.alura.projeto.course.dto;

import br.com.alura.projeto.course.domain.CourseStatusType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCourseFormDTO {

    @NotBlank
    @Length(min = 2, max = 256)
    private String name;

    @NotBlank
    @Length(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)*$")
    private String code;

    @Length(max = 300)
    private String description;

    @NotBlank
    @Email
    @Length(max = 150)
    private String instructorEmail;

    @NotNull
    private CourseStatusType status = CourseStatusType.ACTIVE;

    @PastOrPresent
    private OffsetDateTime inactivationDate;

    @NotNull
    @Positive
    private Long categoryId;

}
