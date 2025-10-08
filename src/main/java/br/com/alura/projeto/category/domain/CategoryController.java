package br.com.alura.projeto.category.domain;

import br.com.alura.projeto.category.dto.CategoryDTO;
import br.com.alura.projeto.category.dto.NewCategoryFormDTO;
import br.com.alura.projeto.course.domain.Course;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

@Slf4j
@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/categories")
    public String list(Model model) {
        List<CategoryDTO> list = categoryRepository.findAll()
                .stream()
                .map(CategoryDTO::new)
                .toList();

        model.addAttribute("categories", list);

        return "admin/category/list";
    }

    @GetMapping("/admin/category/new")
    public String create(NewCategoryFormDTO dto, Model model) {
        log.info("JSP request: creating new category.");
        return sendToCourseForm(dto, null, model);
    }

    @GetMapping("/admin/category/edit/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        log.info("JSP request: editing category id {}.", id);
        return categoryRepository.findById(id)
            .map(entity -> new NewCategoryFormDTO(
                entity.getName(),
                entity.getCode(),
                entity.getOrder(),
                entity.getColor()
            ))
            .map(dto -> sendToCourseForm(dto, id, model))
            .orElseGet(() -> {
                model.addAttribute("error", "Course not found");
                var dto = new NewCategoryFormDTO();
                return sendToCourseForm(dto, null, model);
            });
    }

    @Transactional
    @PostMapping("/admin/category/save")
    public String save(
        @Valid NewCategoryFormDTO dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes) {
        return save(null, dto, result, model, redirectAttributes);
    }

    @Transactional
    @PostMapping("/admin/category/save/{id}")
    public String save(
        @PathVariable(value = "id", required = false) Long id,
        @Valid NewCategoryFormDTO dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes) {

        log.info("JSP request: persisting category.");
        log.info(dto.toString());

        Consumer<String> addError = text -> model.addAttribute("error", text);

        try {
            if (result.hasErrors()) throw new ValidationException(result.getAllErrors().toString());
            var entity = dto.toModel();
            entity.setId(id);
            var isCategoryCodeDuplicated = categoryRepository.countUniqueCodePerId(dto.getCode(), id) > 0;
            if (isCategoryCodeDuplicated) throw new IllegalArgumentException(
                "Category '%s' already exists. Please set a new unique code.".formatted(dto.getCode())
            );
            categoryRepository.save(entity);
            redirectAttributes.addFlashAttribute("success", "Categoria criada com sucesso!");
            return "redirect:/admin/categories";
        }
        catch (ValidationException e) {
            log.warn("Validation errors found for category: {}", result.getAllErrors());
        }
        catch (IllegalArgumentException e) {
            log.error("Invalid input for category creation: {}", e.getMessage());
            addError.accept(e.getMessage());
        }
        catch (Exception e) {
            log.error("Unexpected error during category creation: {}", e.getMessage(), e);
            addError.accept("Erro ao criar categoria: " + e.getMessage());
        }
        return sendToCourseForm(dto, id, model);
    }

    private String sendToCourseForm(
        @NonNull NewCategoryFormDTO dto,
        @Nullable Long id,
        @NonNull Model model) {

        model.addAttribute(
            "newCategoryFormDTO",
            ofNullable(dto).orElseGet(NewCategoryFormDTO::new)
        );
        model.addAttribute("id", id);
        return "admin/category/newForm";
    }

}
