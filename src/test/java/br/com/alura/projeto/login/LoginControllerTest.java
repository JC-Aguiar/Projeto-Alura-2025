package br.com.alura.projeto.login;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.ArrayList;

import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.category.projection.SimpleCategoryAndCourse;
import br.com.alura.projeto.login.domain.LoginController;
import br.com.alura.projeto.login.domain.LoginMapper;
import br.com.alura.projeto.login.dto.LoginCategoryInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LoginController.class)
@Import({ LoginMapper.class })
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginMapper loginMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    private List<SimpleCategoryAndCourse> mockCategories;
    private List<LoginCategoryInfoDTO> mockCategoriesDto;

    @BeforeEach
    void setUp() {
        mockCategories = List.of(
            new SimpleCategoryAndCourseTestImpl("Lógica de programação", "Programação", "BACK-END", "#00C86F",  1),
            new SimpleCategoryAndCourseTestImpl(".NET", "Programação", "BACK-END", "#00C86F",  1),
            new SimpleCategoryAndCourseTestImpl("Automação e Produtividade", "Programação", "BACK-END", "#00C86F",  1),
            new SimpleCategoryAndCourseTestImpl("HTML", "Front-End", "FRONT-END", "#6BD1FF",  2),
            new SimpleCategoryAndCourseTestImpl("CSS", "Front-End", "FRONT-END", "#6BD1FF",  2),
            new SimpleCategoryAndCourseTestImpl("Svelte", "Front-End", "FRONT-END", "#6BD1FF",  2),
            new SimpleCategoryAndCourseTestImpl("SQL e Banco de Dados", "Data Science", "DATA-SCIENCE", "#9CD33B",  3),
            new SimpleCategoryAndCourseTestImpl("Engenharia de Dados", "Data Science", "DATA-SCIENCE", "#9CD33B",  3),
            new SimpleCategoryAndCourseTestImpl("Análise de dados", "Data Science", "DATA-SCIENCE", "#9CD33B",  3),
            new SimpleCategoryAndCourseTestImpl("IA para Criativos", "Inteligência Artificial", "IA", "#7B71FF",  4),
            new SimpleCategoryAndCourseTestImpl("IA para Programação", "Inteligência Artificial", "IA", "#7B71FF",  4),
            new SimpleCategoryAndCourseTestImpl("IA para Negócios", "Inteligência Artificial", "IA", "#7B71FF",  4),
            new SimpleCategoryAndCourseTestImpl("Linux", "DevOps", "DEVOPS", "#F16165",  5),
            new SimpleCategoryAndCourseTestImpl("FinOps", "DevOps", "DEVOPS", "#F16165",  5),
            new SimpleCategoryAndCourseTestImpl("Automação de Processos", "DevOps", "DEVOPS", "#F16165",  5),
            new SimpleCategoryAndCourseTestImpl("UI Design", "UX & Design", "UX-DESIGN", "#DC6EBE",  6),
            new SimpleCategoryAndCourseTestImpl("Design System", "UX & Design", "UX-DESIGN", "#DC6EBE",  6),
            new SimpleCategoryAndCourseTestImpl("UX Writing", "UX & Design", "UX-DESIGN", "#DC6EBE",  6),
            new SimpleCategoryAndCourseTestImpl("Flutter", "Mobile", "MOBILE", "#FFBA05",  7),
            new SimpleCategoryAndCourseTestImpl("Android", "Mobile", "MOBILE", "#FFBA05",  7),
            new SimpleCategoryAndCourseTestImpl("iOS", "Mobile", "MOBILE", "#FFBA05",  7),
            new SimpleCategoryAndCourseTestImpl("Agilidade", "Inovação & Gestão", "INOVA-GESTAO", "#FF8C2A",  8),
            new SimpleCategoryAndCourseTestImpl("Liderança", "Inovação & Gestão", "INOVA-GESTAO", "#FF8C2A",  8),
            new SimpleCategoryAndCourseTestImpl("Ensino e Aprendizagem", "Inovação & Gestão", "INOVA-GESTAO", "#FF8C2A",  8)
        );
        mockCategoriesDto = loginMapper.toInfoDTO(mockCategories);
    }

    @Test
    void testHome_ReturnsLoginViewWithCategories() throws Exception {

        when(categoryRepository.findSomeActiveCoursesWithCategory(3))
            .thenReturn(mockCategories);

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("data"))
            .andExpect(model().attribute("data", hasSize(8)))
            .andExpect(model().attribute("data", mockCategoriesDto));

        verify(categoryRepository, times(1))
            .findSomeActiveCoursesWithCategory(3);
    }
}
