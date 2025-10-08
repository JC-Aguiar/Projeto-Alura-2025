package br.com.alura.projeto.category.domain;

import br.com.alura.projeto.course.domain.Course;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Length(min = 4, max = 10)
    private String code;

    @NotBlank
    private String color;

    @NotNull
    @Min(1)
    @Column(name = "`order`")
    private int order;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ToString.Exclude
    @JsonManagedReference("category-course")
    @OneToMany(fetch = LAZY, mappedBy = "category")
    private List<Course> courses = new ArrayList<>();

    @Deprecated
    public Category() {}

    public Category(String name, String code, String color, int order) {
        this.name = name;
        this.code = code;
        this.color = color;
        this.order = order;
    }

    public void addCourse(Course course) {
        if(course == null) return;
        courses.add(course);
        course.setCategory(this);
    }
}
