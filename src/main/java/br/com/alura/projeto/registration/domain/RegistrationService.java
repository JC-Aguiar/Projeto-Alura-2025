package br.com.alura.projeto.registration.domain;

import br.com.alura.projeto.course.domain.CourseRepository;
import br.com.alura.projeto.exception.ServiceException;
import br.com.alura.projeto.registration.RegistrationReportItem;
import br.com.alura.projeto.registration.projection.RegistrationReportItemConcept;
import br.com.alura.projeto.user.UserRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


     /// Registra uma nova matrícula de um usuário em um curso específico.
     ///
     /// O método verifica a existência do usuário e do curso (deve estar ATIVO) e
     /// garante que o aluno ainda não esteja matriculado antes de criar o registro.
     ///
     /// @param studentEmail O e-mail do aluno que será matriculado. Deve ser não nulo.
     /// @param courseCode O código único do curso (slug) no qual o aluno será matriculado. Deve ser não nulo.
     /// @return O objeto {@link Enrollment} (matrícula) recém-criado e persistido no banco.
     /// @throws ServiceException Se o e-mail do aluno ou o código do curso não forem encontrados,
     /// ou se o aluno já estiver matriculado no curso.
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

     /// Gera um relatório com todas as matrículas ativas no sistema.
     ///
     /// Este método é utilizado para fins administrativos, buscando dados de matrícula
     /// através de uma Projection específica.
     ///
     /// @return Uma lista de objetos {@link RegistrationReportItemConcept} contendo dados
     /// agregados sobre as matrículas.
    public List<RegistrationReportItemConcept> reportAllRegistrations() {
        log.info("Retrieving overall Enrollment reports.");
        var result = enrollmentRepository.reportAllRegistrations();
        result.forEach(r -> log.info(r.toString()));
        return result;
    }
}