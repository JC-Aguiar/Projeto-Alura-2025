package br.com.alura.projeto.course.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

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


    public NewCourseFormDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }
}
