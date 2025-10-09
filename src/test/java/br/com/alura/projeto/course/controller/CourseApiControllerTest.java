package br.com.alura.projeto.course.controller;

import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.course.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CourseApiController.class)
@Import({ CourseService.class, CourseMapper.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseApiControllerTest {

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

    @Autowired
    private ObjectMapper objectMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private final OffsetDateTime dateReference = OffsetDateTime.of(
        2025,10, 2, 18, 22, 27, 169511500, ZoneOffset.UTC
    );
    private List<Course> courses;
    private PageRequest pageable;
    private PageImpl<Course> pageOfCourses;

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

        courses = Arrays.asList(course1, course2);
        pageable = PageRequest.of(0, 20, Sort.unsorted());
        pageOfCourses = new PageImpl<>(courses, pageable, courses.size());

        log.info("Records mocked!");
    }

    @BeforeEach
    public void mockingRepositoryResult() {
        when(courseRepository.findAll(
            any(Example.class),
            any(Pageable.class))
        ).thenReturn(pageOfCourses);
    }

    @Test
    void listAllCourses__noParameters_should_list_all_courses() throws Exception {
//        when(courseRepository.findAll()).thenReturn(courses);

        mockMvc.perform(
            get("/api/user/courses")
                .contentType(APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpectAll(
                // First mocked record validation
                jsonPath("$.content.[0].name").value("Course XPTO"),
                jsonPath("$.content.[0].code").value("xpto"),
                jsonPath("$.content.[0].description")
                    .value("Its a very important and relevant technology in today's market"),
                jsonPath("$.content.[0].instructorEmail").value("john.doe.instructor@alura.com"),
                jsonPath("$.content.[0].status").value(CourseStatusType.ACTIVE.name()),
                jsonPath("$.content.[0].inactivationDate")
                    .value(dateReference.format(FORMATTER))
            )
            .andExpectAll(
                // Second mocked record validation
                jsonPath("$.content.[1].name").value("Extreme Go Horse!"),
                jsonPath("$.content.[1].code").value("ex-go-horse"),
                jsonPath("$.content.[1].description").value(
                "It's the universal, must know, best practice of all times. The number one. " +
                    "You must learn this course if you want became a top developer"
                ),
                jsonPath("$.content.[1].instructorEmail").value("john.doe.instructor@alura.com"),
                jsonPath("$.content.[1].status").value(CourseStatusType.INACTIVE.name()),
                jsonPath("$.content.[1].inactivationDate")
                    .value(dateReference.minusMinutes(90).format(FORMATTER))
            );
    }

    @Test
    void listAllCourses__usingParameters__should_list_only_first_course() throws Exception {
        mockMvc.perform(
                get("/api/user/courses")
                    .contentType(APPLICATION_JSON)
                    .param("name", "Course XPTO")
                    .param ("code", "xpto")
                    .param ("instructorEmail", "john.doe.instructor@alura.com")
                    .param ("status", CourseStatusType.ACTIVE.name())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                // First mocked record validation
                jsonPath("$.content.[0].name").value("Course XPTO"),
                jsonPath("$.content.[0].code").value("xpto"),
                jsonPath("$.content.[0].description")
                    .value("Its a very important and relevant technology in today's market"),
                jsonPath("$.content.[0]instructorEmail")
                    .value("john.doe.instructor@alura.com"),
                jsonPath("$.content.[0].status").value(CourseStatusType.ACTIVE.name()),
                jsonPath("$.content.[0].inactivationDate")
                    .value(dateReference.format(FORMATTER))
            );
    }

    @Test
    void listAllCourses__usingParameters__should_list_only_second_course() throws Exception {
        mockMvc.perform(
                get("/api/user/courses")
                    .contentType(APPLICATION_JSON)
                    .param("name", "Extreme Go Horse!")
                    .param ("instructorEmail", "john.doe.instructor@alura.com")
            )
            .andExpect(status().isOk())
            .andExpectAll(
                // Second mocked record validation
                jsonPath("$.content[1].name").value("Extreme Go Horse!"),
                jsonPath("$.content[1].code").value("ex-go-horse"),
                jsonPath("$.content[1].description").value(
                    "It's the universal, must know, best practice of all times. The number one. " +
                    "You must learn this course if you want became a top developer"
                ),
                jsonPath("$.content[1].instructorEmail")
                    .value("john.doe.instructor@alura.com"),
                jsonPath("$.content[1].status").value(CourseStatusType.INACTIVE.name()),
                jsonPath("$.content[1].inactivationDate")
                    .value(dateReference.minusMinutes(90).format(FORMATTER))
            );
    }

    @Test
    void listAllCourses__invalidParam_Code__should_abort_with_error_400() throws Exception {
        mockMvc.perform(
            get("/api/user/courses")
                .contentType(APPLICATION_JSON)
                .param("name", "Course XPTO")
                .param ("code", "xptoxptoxptoxptoxptoxptoxptoxptoxpto")
                .param ("instructorEmail", "john.doe.instructor@alura.com")
                .param ("status", CourseStatusType.ACTIVE.name())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].field").value("code"))
            .andExpect(jsonPath("$[0].message").value("length must be between 0 and 10"))
        ;
    }

}









