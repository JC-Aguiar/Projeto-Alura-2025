package br.com.alura.projeto.course.domain;

import br.com.alura.projeto.category.domain.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String description;

    private String instructorEmail;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CourseStatusType status = CourseStatusType.ACTIVE;

    private OffsetDateTime inactivationDate;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "categoryId")
    @ManyToOne(fetch = LAZY)
    private Category category;

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<OffsetDateTime> getInactivationDate() {
        return Optional.ofNullable(inactivationDate);
    }
}
