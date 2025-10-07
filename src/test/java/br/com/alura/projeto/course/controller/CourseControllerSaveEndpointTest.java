package br.com.alura.projeto.course.controller;

import br.com.alura.projeto.category.domain.Category;
import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.course.CourseController;
import br.com.alura.projeto.course.domain.Course;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CourseController.class)
@Import({ CourseService.class, CourseMapper.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseControllerSaveEndpointTest {

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

    public static final String URI_SAVE_COURSE_PAGE = "/admin/course/save";

    @Test
    void testSave__valid_fields__saves_course_and_redirects() throws Exception {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
        when(courseRepository.save(any(Course.class))).thenReturn(new Course());
        when(courseRepository.countUniqueCodePerId(anyString(), anyLong()))
            .thenReturn(0);

        mockMvc.perform(
            post(URI_SAVE_COURSE_PAGE)
                .param("name", "Test Course")
                .param("code", "TEST")
                .param("description", "Description")
                .param("instructorEmail", "random.person@mock.com")
                .param("status", "ACTIVE")
                .param("categoryId", "1"))
            .andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/admin/courses"),
                flash().attribute("success", "Curso criado com sucesso!")
            );
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testSave__valid_fields__mismatch_category__returns_same_page_with_error() throws Exception {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
            post(URI_SAVE_COURSE_PAGE)
                .param("name", "Test Course")
                .param("code", "TEST")
                .param("description", "Description")
                .param("instructorEmail", "random.person@mock.com")
                .param("status", "ACTIVE")
                .param("categoryId", "1"))
            .andExpectAll(
                status().isOk(),
                model().attributeExists("newCourseFormDTO"),
                model().attributeExists("courses"),
                model().attribute("error", "The category related to this course is missing. Please enter a valid value.")
            );
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testSave__invalid_fields__returns_same_page_with_errors() throws Exception {
        when(courseRepository.save(any(Course.class))).thenReturn(new Course());
        when(courseRepository.countUniqueCodePerId(anyString(), anyLong()))
            .thenReturn(1);

        mockMvc.perform(
            post(URI_SAVE_COURSE_PAGE)
                .param("name", "")
                .param("code", "TEST-COURSE")
                .param("description", "Lorem Ipsum".repeat(50))
                .param("instructorEmail", "No thanks!"))
            .andExpectAll(
                status().isOk(),
                model().attributeExists("newCourseFormDTO"),
                model().attributeHasErrors("newCourseFormDTO"),
                model().attributeHasFieldErrors("newCourseFormDTO", "name"),
                model().attributeHasFieldErrors("newCourseFormDTO", "code"),
                model().attributeHasFieldErrors("newCourseFormDTO", "description"),
                model().attributeHasFieldErrors("newCourseFormDTO", "instructorEmail"),
                model().attributeHasFieldErrors("newCourseFormDTO", "status"),
                model().attributeHasFieldErrors("newCourseFormDTO", "categoryId")
            );
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testSave__IllegalArgumentException__redirects_with_error() throws Exception {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
        when(courseRepository.save(any(Course.class))).thenThrow(
            new IllegalArgumentException("Invalid course code")
        );
        mockMvc.perform(post(URI_SAVE_COURSE_PAGE)
                .param("name", "Test Course")
                .param("code", "TEST")
                .param("description", "Description")
                .param("instructorEmail", "random.person@mock.com")
                .param("status", "ACTIVE")
                .param("categoryId", "1"))
            .andExpectAll(
                status().isOk(),
                view().name("admin/course/form"),
                model().attributeExists("newCourseFormDTO"),
                model().attribute("error", "Invalid course code")
            );
    }

    @Test
    void testSave__UnexpectedException__redirects_with_error() throws Exception {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
        when(courseRepository.save(any(Course.class))).thenThrow(
            new RuntimeException("Database error")
        );
        mockMvc.perform(post(URI_SAVE_COURSE_PAGE)
                .param("name", "Test Course")
                .param("code", "TEST")
                .param("description", "Description")
                .param("instructorEmail", "random.person@mock.com")
                .param("status", "ACTIVE")
                .param("categoryId", "1"))
            .andExpectAll(
                status().isOk(),
                view().name("admin/course/form"),
                model().attributeExists("newCourseFormDTO"),
                model().attribute("error", "Erro ao criar curso: Database error")
            );
    }

}









