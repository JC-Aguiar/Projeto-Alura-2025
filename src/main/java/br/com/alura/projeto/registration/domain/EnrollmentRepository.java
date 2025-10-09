package br.com.alura.projeto.registration.domain;

import br.com.alura.projeto.registration.RegistrationReportItem;
import br.com.alura.projeto.registration.projection.RegistrationReportItemConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    @Query(nativeQuery = true, value = """
        SELECT      c.name      AS courseName,
                    c.code      AS courseCode,
                    u.name      AS instructorName,
                    u.email     AS instructorEmail,
                    COUNT(e.id) AS totalRegistrations
        FROM        Course c
        LEFT JOIN   User u
        ON          c.instructorEmail = u.email
        AND         u.role = 'INSTRUCTOR'
        LEFT JOIN   Enrollment e
        ON          c.id = e.courseId
        GROUP BY    c.id, c.name, c.code, u.name, u.email;
        """)
    List<RegistrationReportItemConcept> reportAllRegistrations();

}