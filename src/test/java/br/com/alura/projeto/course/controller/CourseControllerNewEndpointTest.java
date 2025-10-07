package br.com.alura.projeto.course.controller;

import br.com.alura.projeto.course.CourseController;
import br.com.alura.projeto.course.domain.CourseMapper;
import br.com.alura.projeto.course.domain.CourseRepository;
import br.com.alura.projeto.course.domain.CourseService;
import br.com.alura.projeto.course.dto.NewCourseFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CourseController.class)
@Import({ CourseService.class, CourseMapper.class })
class CourseControllerNewEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    public static final String URI_NEW_COURSE_PAGE = "/admin/course/new";

    @Test
    void testCreate__no_input__returns_form_view_with_new_DTO() throws Exception {
        mockMvc.perform(get(URI_NEW_COURSE_PAGE))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/course/form"))
            .andExpect(model().attributeExists("newCourseFormDTO"))
            .andExpect(model().attribute(
                "newCourseFormDTO",
                instanceOf(NewCourseFormDTO.class)
            ));
    }

    @Test
    void testCreate__valid_input__returns_form_view_with_provided_DTO() throws Exception {
        mockMvc.perform(get(URI_NEW_COURSE_PAGE)
                .param("name", "Test Course")
                .param("code", "EX-COURSE"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/course/form"))
            .andExpect(model().attribute(
                "newCourseFormDTO",
                hasProperty("name", is("Test Course"))
            ))
            .andExpect(model().attribute(
                "newCourseFormDTO",
                hasProperty("code", is("EX-COURSE"))
            ));
    }

}









