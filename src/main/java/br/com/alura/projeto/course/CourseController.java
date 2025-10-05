package br.com.alura.projeto.course;

import br.com.alura.projeto.course.domain.CourseMapper;
import br.com.alura.projeto.course.domain.CourseService;
import br.com.alura.projeto.course.dto.CourseInfoDTO;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import br.com.alura.projeto.course.dto.SearchCourseDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Controller
public class CourseController {

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final CourseMapper courseMapper;


    @GetMapping("/admin/courses")
    public String list(
        @ModelAttribute @Valid SearchCourseDTO dto,
        Model model) {

        log.info("JSP request: listing all courses filtered by users parameter.");
        log.info(dto.toString());
            var example = Example.of(courseMapper.toEntity(dto));
            var courses = courseService.list(example)
                .stream()
                .map(courseMapper::toGenericResponseDTO)
                .toList();
            model.addAttribute("courses", courses);
        return "admin/course/list";
    }

    @GetMapping("/admin/course/new")
    public String create(NewCourseFormDTO dto, Model model) {
        log.info("JSP request: creating new course.");
        model.addAttribute(
            "newCourseFormDTO",
            Optional.ofNullable(dto).orElseGet(NewCourseFormDTO::new)
        );
        return "admin/course/form";
    }

    @Transactional
    @PostMapping("/admin/course/save")
    public String save(
        @Valid NewCourseFormDTO dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes) {

        log.info("JSP request: persisting new course.");
        log.info(dto.toString());

        if (result.hasErrors()) {
            log.warn("Validation errors found for course: {}", result.getAllErrors());
            model.addAttribute("newCourseFormDTO", dto);
            return "admin/course/form";
        }
        try {
            var entity = courseMapper.toEntity(dto);
            courseService.create(entity);
            redirectAttributes.addFlashAttribute("success", "Curso criado com sucesso!");
            return "redirect:/admin/courses";
        }
        catch (IllegalArgumentException e) {
            log.error("Invalid input for course creation: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/admin/course/new";
        }
        catch (Exception e) {
            log.error("Unexpected error during course creation: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute(
                "erro",
                "Erro ao criar curso: " + e.getMessage()
            );
            return "redirect:/admin/course/new";
        }
    }

    @Transactional
    @PostMapping("/course/{code}/inactive")
    public ResponseEntity<?> updateStatus(@PathVariable("code") String courseCode) {
        // TODO: Implementar a Questão 2 - Inativação de Curso aqui...

        return ResponseEntity.ok().build();
    }

}
