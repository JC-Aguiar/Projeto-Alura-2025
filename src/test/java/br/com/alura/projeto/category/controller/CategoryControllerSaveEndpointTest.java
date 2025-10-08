package br.com.alura.projeto.category.controller;

import br.com.alura.projeto.category.domain.Category;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CategoryController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerSaveEndpointTest {

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

    public static final String URI_SAVE_CATEGORY = "/admin/category/save";
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
            .map(safeId -> URI_SAVE_CATEGORY + "/" + safeId)
            .orElse(URI_SAVE_CATEGORY);
        return post(uri)
            .param("name", dto.getName())
            .param("code", dto.getCode())
            .param("order", String.valueOf(dto.getOrder()))
            .param("color", dto.getColor());
    }

    @Test
    void testSave__new_valid_category__should_succeed() throws Exception {
        // Arrange
        when(categoryRepository.countUniqueCodePerId(any(), any())).thenReturn(0);
        when(categoryRepository.save(any())).thenReturn(new Category());

        // Act & Assert
        mockMvc.perform(buildRequestForId(null))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/categories"))
            .andExpect(flash().attribute("success", "Categoria criada com sucesso!"));

        verify(categoryRepository).save(any());
    }

    @Test
    void testSave__invalid_fields____should_return_to_same_page_with_error() throws Exception {
        // Arrange
        dto = new NewCategoryFormDTO();
        dto.setName("as");
        dto.setCode("rgb(255, 255, 255)");
        var request = buildRequestForId(null);

        // Act & Assert
        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(view().name(URI_ADMIN_CATEGORY_FORM))
            .andExpect(model().attributeExists("newCategoryFormDTO"))
            .andExpect(model().attributeHasFieldErrors("newCategoryFormDTO", "code"));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testSave__with_duplicated_code__should_return_to_same_page_with_error() throws Exception {
        // Arrange
        when(categoryRepository.countUniqueCodePerId(dto.getCode(), null)).thenReturn(1);

        // Act & Assert
        mockMvc.perform(buildRequestForId(null))
            .andExpect(status().isOk())
            .andExpect(view().name(URI_ADMIN_CATEGORY_FORM))
            .andExpect(model().attribute(
                "error",
                "Category '%s' already exists. Please set a new unique code.".formatted(dto.getCode())
            ));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testSave__with_unexpected_error__should_return_to_same_page_with_error() throws Exception {
        // Arrange
        when(categoryRepository.countUniqueCodePerId(any(), any())).thenReturn(0);
        when(categoryRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(buildRequestForId(null))
            .andExpect(status().isOk())
            .andExpect(view().name(URI_ADMIN_CATEGORY_FORM))
            .andExpect(model().attribute("error", "Erro ao criar categoria: Database error"));

        verify(categoryRepository).save(any());
    }

    @Test
    void testUpdate__existing_category__should_redirect_with_error() throws Exception {
        // Arrange
        Long id = 1L;
        when(categoryRepository.countUniqueCodePerId(dto.getCode(), id)).thenReturn(0);
        when(categoryRepository.save(any())).thenReturn(new Category());

        // Act & Assert
        mockMvc.perform(buildRequestForId(id))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/categories"))
            .andExpect(flash().attribute("success", "Categoria criada com sucesso!"));

        verify(categoryRepository).save(argThat(category -> category.getId().equals(id)));
    }
}
