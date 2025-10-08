package br.com.alura.projeto.registration.domain;

import br.com.alura.projeto.course.domain.CourseRepository;
import br.com.alura.projeto.exception.ServiceException;
import br.com.alura.projeto.user.UserRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.alura.projeto.course.domain.CourseStatusType.ACTIVE;

@Slf4j
@Data
@Service
public class RegistrationService {

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseRepository courseRepository;


    @Transactional
    public Enrollment registry(@NonNull String studentEmail, @NonNull String courseCode)
    throws ServiceException {
        log.info("Registering enrollment for user {} in course {}", studentEmail, courseCode);
        var user = userRepository.findByEmail(studentEmail).orElseThrow(
            () -> new ServiceException("studentEmail", "Email não encontrado")
        );
        var course = courseRepository.findByCodeAndStatus(courseCode, ACTIVE).orElseThrow(
            () -> new ServiceException("courseCode", "Curso não encontrado")
        );
        var isUserAlreadyEnrolled = enrollmentRepository.existsByUserIdAndCourseId(
            user.getId(),
            course.getId()
        );
        if (isUserAlreadyEnrolled) throw new ServiceException(
            "courseCode", "Aluno já matriculado para o curso"
        );
        var enrollment = new Enrollment(user, course);
        var entity = enrollmentRepository.save(enrollment);

        log.info("Enrollment succeed!");
        log.info(entity.toString());
        return entity;
    }
}