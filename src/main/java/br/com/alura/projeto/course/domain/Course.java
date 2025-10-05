package br.com.alura.projeto.course.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<OffsetDateTime> getInactivationDate() {
        return Optional.ofNullable(inactivationDate);
    }
}
