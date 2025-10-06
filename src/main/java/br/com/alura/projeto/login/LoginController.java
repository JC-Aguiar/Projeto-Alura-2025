package br.com.alura.projeto.login;

import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.category.projection.SimpleCategoryAndCourse;
import br.com.alura.projeto.login.dto.LoginCategoryInfoDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import static java.util.stream.Collectors.*;

@Controller
@Data
public class LoginController {

    @Autowired
    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model) {
        var dto = categoryRepository.findSomeActiveCoursesWithCategory(3)
            .stream()
            .collect(groupingBy(
                record -> new LoginCategoryInfoDTO(
                    record.getCategory(),
                    record.getCategoryCode(),
                    record.getCategoryColor(),
                    record.getCategoryOrder(),
                    new ArrayList<>()
                ),
                mapping(SimpleCategoryAndCourse::getCourse, toList())
            ))
            .entrySet()
            .stream()
            .map(entry-> {
                var category = entry.getKey();
                var setOfCourses = entry.getValue();
                category.courses().addAll(setOfCourses);
                return category;
            })
            .sorted(Comparator.comparing(LoginCategoryInfoDTO::order))
            .toList();
        model.addAttribute("data", dto);
        return "login";
    }
}
