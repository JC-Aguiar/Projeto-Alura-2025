package br.com.alura.projeto.enrollment;

import br.com.alura.projeto.category.domain.Category;
import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.course.domain.Course;
import br.com.alura.projeto.course.domain.CourseRepository;
import br.com.alura.projeto.registration.NewRegistrationDTO;
import br.com.alura.projeto.registration.domain.Enrollment;
import br.com.alura.projeto.registration.domain.EnrollmentRepository;
import br.com.alura.projeto.registration.domain.RegistrationService;
import br.com.alura.projeto.user.User;
import br.com.alura.projeto.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static br.com.alura.projeto.course.domain.CourseStatusType.ACTIVE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Data
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    private Course course;

    private Enrollment enrollment;

    private static final String URI_NEW_REGISTRATION = "/api/registration/new";
    public static final String URI_REPORT_REGISTRATIONS = "/api/registration/report";

    @BeforeEach
    public void persistingMockRecords() {
        user = new User();
        user.setEmail("charles@alura.com.br");
        user.setName("Charles");
        user.setPassword("mudar123");
        user = userRepository.save(user);

        var category = new Category(
            "TEST",
            "Category-A",
            "#FFFFFF",
            1
        );
        categoryRepository.save(category);

        course = Course.builder()
            .name("Test Course")
            .code("TEST")
            .description("Description")
            .instructorEmail("random.person@mock.com")
            .status(ACTIVE)
            .category(category)
            .build();
        course = courseRepository.save(course);
    }

    @Test
    void testCreateEnrollment__valid_dto__should_return_be_successful() throws Exception {
        var dto = new NewRegistrationDTO(course.getCode(), user.getEmail());

        mockMvc.perform(
            post(URI_NEW_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(status().isCreated());

        var isUserRegisteredInTheCourse = enrollmentRepository.existsByUserIdAndCourseId(
            user.getId(),
            course.getId()
        );
        Assertions.assertTrue(isUserRegisteredInTheCourse);
    }

    @Test
    void testCreateEnrollment__invalid_email_pattern__should_return_bad_request() throws Exception {
        var dto = new NewRegistrationDTO(course.getCode(), "none");

        mockMvc.perform(
            post(URI_NEW_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$").isArray(),
                jsonPath("$[0].field").value("studentEmail"),
                jsonPath("$[0].message").value("must be a well-formed email address")
            );

        var isUserRegisteredInTheCourse = enrollmentRepository.existsByUserIdAndCourseId(
            user.getId(),
            course.getId()
        );
        Assertions.assertFalse(isUserRegisteredInTheCourse);
    }

    @Test
    void testCreateEnrollment__user_not_found__should_return_bad_request() throws Exception {
        var dto = new NewRegistrationDTO(course.getCode(), "none@a.b.com");

        mockMvc.perform(
            post(URI_NEW_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.field").value("studentEmail"),
                jsonPath("$.message").value("Email não encontrado")
            );

        var isUserRegisteredInTheCourse = enrollmentRepository.existsByUserIdAndCourseId(
            user.getId(),
            course.getId()
        );
        Assertions.assertFalse(isUserRegisteredInTheCourse);
    }

    @Test
    void testCreateEnrollment__course_code_not_found__should_return_bad_request() throws Exception {
        var dto = new NewRegistrationDTO("code-empty", user.getEmail());

        mockMvc.perform(
            post(URI_NEW_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.field").value("courseCode"),
                jsonPath("$.message").value("Curso não encontrado")
            );

        var isUserRegisteredInTheCourse = enrollmentRepository.existsByUserIdAndCourseId(
            user.getId(),
            course.getId()
        );
        Assertions.assertFalse(isUserRegisteredInTheCourse);
    }

    @Test
    void testCreateEnrollment__user_already_registered_in_the_course__should_return_bad_request()
    throws Exception {
        var dto = new NewRegistrationDTO(course.getCode(), user.getEmail());
        var enrollment = new Enrollment(user, course);
        enrollmentRepository.saveAndFlush(enrollment);

        mockMvc.perform(
            post(URI_NEW_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.field").value("courseCode"),
                jsonPath("$.message").value("Aluno já matriculado para o curso")
            );
    }

    @Test
    void report__successful__returns_ok_with_reports() throws Exception {
        // Act & Assert
        mockMvc.perform(
                get(URI_REPORT_REGISTRATIONS)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

}
