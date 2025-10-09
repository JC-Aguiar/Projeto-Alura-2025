package br.com.alura.projeto.registration.domain;

import br.com.alura.projeto.exception.ServiceException;
import br.com.alura.projeto.registration.NewRegistrationDTO;
import br.com.alura.projeto.registration.RegistrationReportItem;
import br.com.alura.projeto.util.ErrorItemDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Data
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    /// Registra uma nova matrícula (inscrição) de um aluno em um curso.
    ///
    /// O corpo da requisição deve conter o e-mail do aluno e o código único (slug) do curso.
    /// A validação do formato dos dados é feita pelo Bean Validation.
    ///
    /// @param dto Objeto contendo {@code studentEmail} e {@code courseCode} para registro. Deve ser válido.
    /// @return {@link ResponseEntity} com o status da operação:
    /// <ul>
    /// <li>{@code 201 CREATED} em caso de sucesso na matrícula.</li>
    /// <li>{@code 400 BAD_REQUEST} se o aluno/curso não for encontrado ou se o aluno já estiver matriculado ({@link ServiceException}).</li>
    /// <li>{@code 500 INTERNAL_SERVER_ERROR} em caso de falha sistêmica inesperada.</li>
    /// </ul>
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

    /// Retorna um relatório agregado de todas as matrículas ativas no sistema.
    ///
    /// O método delega ao serviço a coleta dos dados e mapeia o resultado para uma lista de DTOs de relatório.
    ///
    /// @return {@link ResponseEntity} contendo:
    /// <ul>
    /// <li>{@code 200 OK} com uma lista de objetos {@link RegistrationReportItem} em caso de sucesso.</li>
    /// <li>{@code 500 INTERNAL_SERVER_ERROR} em caso de falha sistêmica na geração do relatório.</li>
    /// </ul>
    @GetMapping("/report")
    public ResponseEntity report() {
        try {
            var dtos = registrationService.reportAllRegistrations()
                .stream()
                .map(RegistrationReportItem::new)
                .toList();
            return ResponseEntity.ok(dtos);
        }
        catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorItemDTO.systemError());
        }
    }

}
