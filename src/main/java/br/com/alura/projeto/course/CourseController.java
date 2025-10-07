package br.com.alura.projeto.course;

import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.category.dto.CategoryDTO;
import br.com.alura.projeto.course.domain.CourseMapper;
import br.com.alura.projeto.course.domain.CourseService;
import br.com.alura.projeto.course.domain.CourseStatusType;
import br.com.alura.projeto.course.dto.InactivateCourseDTO;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import br.com.alura.projeto.course.dto.SearchCourseDTO;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Data
@Controller
public class CourseController {

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final CategoryRepository categoryRepository;

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
        return sendToCourseForm(dto, null, model);
    }

    @GetMapping("/admin/course/edit/{id}")
    public String update(@PathVariable("id") Long id, NewCourseFormDTO dto, Model model) {
        log.info("JSP request: editing course id {}.", id);
        var courseAndCategoryId = courseService.findCourseAndCategoryIdByCourseBy(id);
        dto = courseMapper.toFormDTO(
            courseAndCategoryId.getCourse(),
            courseAndCategoryId.getCategoryId()
        );
        return sendToCourseForm(dto, id, model);
    }

    @Transactional
    @PostMapping("/admin/course/save/{id}")
    public String save(
        @PathVariable(value = "id", required = false) Long id,
        @Valid NewCourseFormDTO dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes) {

        log.info("JSP request: persisting new course.");
        log.info(dto.toString());

        Consumer<String> addError = text -> model.addAttribute("error", text);

        try {
            if (result.hasErrors()) throw new ValidationException(result.getAllErrors().toString());
            var entity = courseMapper.toEntity(dto, id);
            courseService.save(entity, dto.getCategoryId());
            redirectAttributes.addFlashAttribute("success", "Curso criado com sucesso!");
            return "redirect:/admin/courses";
        }
        catch (ValidationException e) {
            log.warn("Validation errors found for course: {}", result.getAllErrors());
        }
        catch (IllegalArgumentException e) {
            log.error("Invalid input for course creation: {}", e.getMessage());
            addError.accept(e.getMessage());
        }
        catch (Exception e) {
            log.error("Unexpected error during course creation: {}", e.getMessage(), e);
            addError.accept("Erro ao criar curso: " + e.getMessage());
        }
        return sendToCourseForm(dto, id, model);
    }

    private String sendToCourseForm(
        @NonNull NewCourseFormDTO dto,
        @Nullable Long id,
        @NonNull Model model) {

        var categories = categoryRepository.findAll()
            .stream()
            .map(CategoryDTO::new)
            .toList();
        model.addAttribute("listCategoryDTO", categories);
        model.addAttribute("courseStatusType", CourseStatusType.values());
        model.addAttribute(
            "newCourseFormDTO",
            ofNullable(dto).orElseGet(NewCourseFormDTO::new)
        );
        model.addAttribute("id", id);
        return "admin/course/form";
    }

    @Transactional
    @PostMapping("admin/course/{code}/inactive")
    public String inactivate(
        @PathVariable("code") String code,
        @Autowired LocalValidatorFactoryBean validator,
        RedirectAttributes redirectAttributes) {

        log.info("JSP request: inactivating course ode {}.", code);
        Consumer<String> addError = text -> redirectAttributes.addFlashAttribute("error", text);
        Consumer<String> addSuccess = text -> redirectAttributes.addFlashAttribute("success", text);
        try {
            var codeDTO = new InactivateCourseDTO(code);
            var errors = new BeanPropertyBindingResult(codeDTO, "parameter");
            validator.validate(codeDTO, errors);
            if (errors.hasErrors()) {
                log.warn("Validation errors for course code {}: {}", code, errors.getAllErrors());
                var errorMessage = errors.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
                addError.accept("Invalid course code: " + errorMessage);
                return "redirect:/admin/courses";
            }
            var hasUpdatedCourse = courseService.deactivateCourseByCode(code);
            if (hasUpdatedCourse) addSuccess.accept("Course successfully deactivated.");
            else addError.accept("Course not found.");
        }
        catch (IllegalArgumentException e) {
            log.error("Error deactivating course code {}: {}", code, e.getMessage());
            addError.accept(e.getMessage());
        }
        catch (Exception e) {
            log.error("Unexpected error deactivating course with ID {}: {}", id, e.getMessage(), e);
            addError.accept("Error trying to deactivate course. Try again later.");
        }
        return "redirect:/admin/courses";
    }

}
