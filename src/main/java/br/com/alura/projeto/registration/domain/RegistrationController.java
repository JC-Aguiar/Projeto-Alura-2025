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
