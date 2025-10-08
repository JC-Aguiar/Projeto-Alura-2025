package br.com.alura.projeto.category.controller;

import br.com.alura.projeto.category.domain.CategoryController;
import br.com.alura.projeto.category.domain.CategoryRepository;
import br.com.alura.projeto.category.dto.NewCategoryFormDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CategoryController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerNewEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Autowired
    private ObjectMapper objectMapper;

    private NewCategoryFormDTO dto;

    public static final String URI_CREATE_CATEGORY = "/admin/category/new";
    public static final String URI_ADMIN_CATEGORY_FORM = "admin/category/newForm";

    @BeforeEach
    void setUp() {
        dto = new NewCategoryFormDTO(
            "TEST",
            "Category-A",
            1,
            "#FFFFFF"
        );
    }

    private MockHttpServletRequestBuilder buildRequestForId(Long id) {
        var uri = Optional.ofNullable(id)
            .map(safeId -> URI_CREATE_CATEGORY + "/" + safeId)
            .orElse(URI_CREATE_CATEGORY);
        return post(uri)
            .param("name", dto.getName())
            .param("code", dto.getCode())
            .param("order", String.valueOf(dto.getOrder()))
            .param("color", dto.getColor());
    }


    @Test
    void testCreate__default_request__should_return_empty_form() throws Exception {
        // Act & Assert
        mockMvc.perform(get(URI_CREATE_CATEGORY))
            .andExpect(status().isOk())
            .andExpect(view().name(URI_ADMIN_CATEGORY_FORM))
            .andExpect(model().attributeExists("newCategoryFormDTO"))
            .andExpectAll(
                model().attribute("newCategoryFormDTO", hasProperty("name", nullValue())),
                model().attribute("newCategoryFormDTO", hasProperty("code", nullValue())),
                model().attribute("newCategoryFormDTO", hasProperty("order", is(0))),
                model().attribute("newCategoryFormDTO", hasProperty("color", nullValue()))
            );
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void testCreate_preFilledDto_shouldReturnFormWithDto() throws Exception {
        // Act & Assert
        mockMvc.perform(get(URI_CREATE_CATEGORY)
                .param("name", "Categoria Pré-preenchida")
                .param("code", "PREFILL123")
                .param("order", "3")
                .param("color", "#FF0000"))
            .andExpect(status().isOk())
            .andExpect(view().name(URI_ADMIN_CATEGORY_FORM))
            .andExpect(model().attributeExists("newCategoryFormDTO"))
            .andExpectAll(
                model().attribute("newCategoryFormDTO", hasProperty("name", is("Categoria Pré-preenchida"))),
                model().attribute("newCategoryFormDTO", hasProperty("code", is("PREFILL123"))),
                model().attribute("newCategoryFormDTO", hasProperty("order", is(3))),
                model().attribute("newCategoryFormDTO", hasProperty("color", is("#FF0000")))
            );

        verifyNoInteractions(categoryRepository);
    }
}
