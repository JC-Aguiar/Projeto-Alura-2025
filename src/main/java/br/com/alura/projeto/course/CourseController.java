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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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
        BindingResult result,
        Model model) {

        if (result.hasErrors()) {
            log.warn("Validation errors found for course: {}", result.getAllErrors());
            model.addAttribute("searchCourseDTO", dto);
            return "admin/course/list";
        }
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

        Consumer<String> addError = text -> model.addAttribute("error", text);

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
            addError.accept(e.getMessage());
        }
        catch (Exception e) {
            log.error("Unexpected error during course creation: {}", e.getMessage(), e);
            addError.accept("Erro ao criar curso: " + e.getMessage());
        }
        model.addAttribute("newCourseFormDTO", dto);
        return "admin/course/form";
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

        Consumer<String> addError = text -> redirectAttributes.addFlashAttribute("error", text);
        Consumer<String> addSuccess = text -> redirectAttributes.addFlashAttribute("success", text);
        try {
            var hasUpdatedCourse = courseService.deactivateCourseByCode(courseCode);
            if (hasUpdatedCourse) addSuccess.accept("Course successfully deactivated.");
            else addError.accept("Course not found.");
        }
        catch (IllegalArgumentException e) {
            log.error("Error deactivating course code {}: {}", courseCode, e.getMessage());
            addError.accept(e.getMessage());
        }
        catch (Exception e) {
            log.error("Unexpected error deactivating course with ID {}: {}", id, e.getMessage(), e);
            addError.accept("Error trying to deactivate course. Try again later.");
        }
        return "redirect:/admin/courses";
    }

}
