package br.com.alura.projeto.course.controller;

import br.com.alura.projeto.course.CourseController;
import br.com.alura.projeto.course.domain.*;
import br.com.alura.projeto.course.dto.CourseInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CourseController.class)
@Import({ CourseService.class, CourseMapper.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseControllerListEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    public static final String URI_LIST_COURSES_PAGE = "/admin/courses";
    private final OffsetDateTime dateReference = OffsetDateTime.of(
        2025,10, 2, 18, 22, 27, 169511500, ZoneOffset.UTC
    );
    private List<Course> courses;
    private PageRequest pageable;
    private PageImpl<Course> pageOfCourses;
    private List<CourseInfoDTO> responseCoursesDto;

    @BeforeAll
    public void prepareMockedRecords() {
        log.info("Adding mocked Course records.");
        var course1 = Course.builder()
            .name("Course XPTO")
            .code("xpto")
            .description("Its a very important and relevant technology in today's market")
            .instructorEmail("john.doe.instructor@alura.com")
            .status(CourseStatusType.ACTIVE)
            .inactivationDate(dateReference)
            .build();
        log.info(course1.toString());

        var course2 = Course.builder()
            .name("Extreme Go Horse!")
            .code("ex-go-horse")
            .description(
                "It's the universal, must know, best practice of all times. The number one. " +
                "You must learn this course if you want became a top developer")
            .instructorEmail("john.doe.instructor@alura.com")
            .status(CourseStatusType.INACTIVE)
            .inactivationDate(dateReference.minusMinutes(90))
            .build();
        log.info(course2.toString());

        courses = List.of(course1, course2);
        responseCoursesDto = courses.stream().map(courseMapper::toGenericResponseDTO).toList();
        pageable = PageRequest.of(0, 20, Sort.unsorted());
        pageOfCourses = new PageImpl<>(courses, pageable, courses.size());

        log.info("Records mocked!");
    }

    @Test
    void listAllCourses__no_parameters__should_list_all_courses() throws Exception {
        log.info("Mocking records in persistence layer:");
        log.info("- Retrieving records using only example matcher.");
        when(courseRepository.findAll(any(Example.class)))
            .thenReturn(courses);

        log.info("Executing request.");
        mockMvc.perform(get(URI_LIST_COURSES_PAGE))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/course/list"))
            .andExpect(model().attributeExists("courses"))
            .andExpect(model().attribute("courses", responseCoursesDto));
    }

    @Test
    void listAllCourses__using_parameters__match_one__should_list_only_first_course() throws Exception {
        log.info("Mocking records in persistence layer:");
        log.info("- Retrieving records using only example matcher.");
        var mockedCourse = List.of(courses.get(0));
        when(courseRepository.findAll(any(Example.class)))
            .thenReturn(mockedCourse);

        log.info("Executing request.");
        mockMvc.perform(
            get(URI_LIST_COURSES_PAGE)
                .param("name", "Course XPTO")
                .param("code", "xpto")
                .param("instructorEmail", "john.doe.instructor@alura.com")
                .param("status", CourseStatusType.ACTIVE.name())
            )
            .andExpect(status().isOk())
            .andExpect(view().name("admin/course/list"))
            .andExpect(model().attributeExists("courses"))
            .andExpect(model().attribute(
                "courses",
                hasItem(responseCoursesDto.get(0))
            ));
    }

    @Test
    void listAllCourses__using_parameters__mismatch_all__should_not_list_any_course() throws Exception {
        log.info("Mocking empty collection in persistence layer:");
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        log.info("Executing request.");
        mockMvc.perform(
            get(URI_LIST_COURSES_PAGE)
                .param("name", "Spring High Performance")
                .param("code", "spring-hp")
                .param("instructorEmail", "joao.costal@alura.com.br")
                .param("status", CourseStatusType.ACTIVE.name())
            )
            .andExpect(status().isOk())
            .andExpect(view().name("admin/course/list"))
            .andExpect(model().attributeExists("courses"))
            .andExpect(model().attribute("courses", emptyIterable()));
    }

    @Test
    void listAllCourses__invalid_parameter_code__should_report_erro() throws Exception {
        log.info("Executing request.");
        mockMvc.perform(
            get(URI_LIST_COURSES_PAGE)
                .param("name", "Course XPTO")
                .param ("code", "xptoxptoxptoxptoxptoxptoxptoxptoxpto")
                .param ("instructorEmail", "john.doe.instructor@alura.com")
                .param ("status", CourseStatusType.ACTIVE.name())
            )
            .andExpectAll(
                status().isOk(),
                view().name("admin/course/list"),
                model().attributeExists("searchCourseDTO"),
                model().attributeHasErrors("searchCourseDTO"),
                model().attributeHasFieldErrors("searchCourseDTO", "code")
            );
        ;
    }

}









