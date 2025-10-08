package br.com.alura.projeto.registration.domain;

import br.com.alura.projeto.course.domain.Course;
import br.com.alura.projeto.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Optional;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Optional.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ToString.Exclude
    @JsonBackReference("user-enrollment")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ToString.Exclude
    @JsonBackReference("course-enrollment")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    private OffsetDateTime startedAt = OffsetDateTime.now();

    private OffsetDateTime pausedAt;

    private OffsetDateTime stopedAt;


    public Enrollment(User user, Course course) {
        addUser(user).addCouse(course);
    }

    public Enrollment addUser(User user) {
        ofNullable(user).ifPresent(safeUser -> {
            safeUser.getEnrollments().add(this);
            setUser(safeUser);
        });
        return this;
    }

    private Enrollment addCouse(Course course) {
        ofNullable(course).ifPresent(safeCourse -> {
            safeCourse.getEnrollments().add(this);
            setCourse(safeCourse);
        });
        return this;
    }

    public Optional<OffsetDateTime> getPausedAt() {
        return ofNullable(pausedAt);
    }

    public Optional<OffsetDateTime> getStopedAt() {
        return ofNullable(stopedAt);
    }
}