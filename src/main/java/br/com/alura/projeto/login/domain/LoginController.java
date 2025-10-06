package br.com.alura.projeto.login.domain;

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

import static java.util.stream.Collectors.*;

@Controller
@Data
public class LoginController {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final LoginMapper loginMapper;

    @GetMapping("/")
    public String home(Model model) {
        var flatContent = categoryRepository.findSomeActiveCoursesWithCategory(3);
        var data = loginMapper.toInfoDTO(flatContent);
        model.addAttribute("data", data);
        return "login";
    }
}
