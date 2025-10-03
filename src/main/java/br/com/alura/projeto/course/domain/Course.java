package br.com.alura.projeto.course.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Data
@Builder
@Entity
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
