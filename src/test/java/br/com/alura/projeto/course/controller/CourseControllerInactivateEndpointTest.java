package br.com.alura.projeto.course.controller;

import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.course.CourseController;
import br.com.alura.projeto.course.domain.CourseMapper;
import br.com.alura.projeto.course.domain.CourseRepository;
import br.com.alura.projeto.course.domain.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CourseController.class)
@Import({ CourseService.class, CourseMapper.class, LocalValidatorFactoryBean.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseControllerInactivateEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    public static final String URI_INACTIVATE_COURSE = "/admin/course/%s/inactive";

    @Test
    void testInactivate__valid_code__deactivates_and_redirects() throws Exception {
        var code = "TEST";
        when(courseRepository.updateStatusByCode(code)).thenReturn(1);
        mockMvc.perform(
            post(URI_INACTIVATE_COURSE.formatted(code)))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/courses"))
            .andExpect(flash().attribute("success", "Course successfully deactivated.")
        );
        verify(courseRepository, times(1)).updateStatusByCode(code);
    }

    @Test
    void testInactivate__valid_code_mismatch_any_course__redirects_with_error() throws Exception {
        var code = "UNKNOWN";
        when(courseRepository.updateStatusByCode(code)).thenReturn(0);
        mockMvc.perform(
            post(URI_INACTIVATE_COURSE.formatted(code)))
            .andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/admin/courses"),
                flash().attribute("error", "Course not found.")
            );
        verify(courseRepository, times(1)).updateStatusByCode(code);
    }

    @Test
    void testInactivate__invalid_code__redirects_with_error() throws Exception {
        var code = "INV@LID_!!!!!";
        when(courseRepository.updateStatusByCode(code)).thenThrow(
            new IllegalArgumentException("Invalid course code format")
        );
        mockMvc.perform(
            post(URI_INACTIVATE_COURSE.formatted(code)))
            .andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/admin/courses"),
                flash().attribute("error", "Invalid course code format")
            );
        verify(courseRepository, times(1)).updateStatusByCode(code);
    }

    @Test
    void testInactivate__unexpected_exception__redirects_with_error() throws Exception {
        var code = "TEST";
        when(courseRepository.updateStatusByCode(code)).thenThrow(
            new RuntimeException("Database error")
        );
        mockMvc.perform(
            post(URI_INACTIVATE_COURSE.formatted(code)))
            .andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/admin/courses"),
                flash().attribute("error", "Error trying to deactivate course. Try again later.")
            );
        verify(courseRepository, times(1)).updateStatusByCode(code);
    }

}









