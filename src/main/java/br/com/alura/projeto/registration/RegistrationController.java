package br.com.alura.projeto.registration;

import br.com.alura.projeto.exception.ServiceException;
import br.com.alura.projeto.registration.domain.RegistrationService;
import br.com.alura.projeto.util.ErrorItemDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Data
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewRegistrationDTO dto) {
        try {
            var studentEmail = dto.getStudentEmail();
            var courseCode = dto.getCourseCode();
            registrationService.registry(studentEmail, courseCode);
            return ResponseEntity.status(CREATED).build();
        }
        catch (ServiceException e) {
            log.warn(e.toString());
            return ResponseEntity.status(BAD_REQUEST).body(e.toDTO());
        }
        catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorItemDTO.systemError());
        }
    }

    @GetMapping("/report")
    public ResponseEntity<List<RegistrationReportItem>> report() {
        List<RegistrationReportItem> items = new ArrayList<>();

        // TODO: Implementar a Questão 6 - Relatório de Cursos Mais Acessados aqui...

        // Dados fictícios abaixo que devem ser substituídos
        items.add(new RegistrationReportItem(
            "Java para Iniciantes",
            "java",
            "Charles",
            "charles@alura.com.br",
            10L
        ));

        items.add(new RegistrationReportItem(
            "Spring para Iniciantes",
            "spring",
            "Charles",
            "charles@alura.com.br",
            9L
        ));

        items.add(new RegistrationReportItem(
            "Maven para Avançados",
            "maven",
            "Charles",
            "charles@alura.com.br",
            9L
        ));

        return ResponseEntity.ok(items);
    }

}
