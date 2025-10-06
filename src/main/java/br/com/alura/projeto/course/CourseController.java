package br.com.alura.projeto.course;

import br.com.alura.projeto.course.domain.CourseMapper;
import br.com.alura.projeto.course.domain.CourseService;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import br.com.alura.projeto.course.dto.SearchCourseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    public String inactivate(
        @PathVariable("code")
        @Valid
        @Length(min = 4, max = 10)
        @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)*$")
        String courseCode,
        RedirectAttributes redirectAttributes) {

        Map.Entry<String, String> message;
        try {
            var hasUpdatedCourse = courseService.deactivateCourseByCode(courseCode);
            message = hasUpdatedCourse ?
                Map.entry("success", "Course successfully deactivated.") :
                Map.entry("error", "Course not found.");
        }
        catch (IllegalArgumentException e) {
            log.error("Error deactivating course code {}: {}", courseCode, e.getMessage());
            message = Map.entry("error", e.getMessage());
        }
        catch (Exception e) {
            log.error("Unexpected error deactivating course with ID {}: {}", id, e.getMessage(), e);
            message = Map.entry("error", "Error trying to deactivate course. Try again later.");
        }
        redirectAttributes.addFlashAttribute(message.getKey(), message.getValue());
        log.info("Message to delivery: {}", message);

        return "redirect:/admin/courses";
    }

}
