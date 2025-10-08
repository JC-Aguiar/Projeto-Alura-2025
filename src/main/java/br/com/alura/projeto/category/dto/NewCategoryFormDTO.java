package br.com.alura.projeto.category.dto;

import br.com.alura.projeto.category.domain.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class NewCategoryFormDTO {

    @NotBlank
    @Length(min= 4, max = 50)
    private String name;

    @NotBlank
    @Length(min = 4, max = 50)
    private String code;

    @Min(1)
    @Length(min = 4, max = 50)
    private int order;

    @NotBlank
    @Pattern(
        regexp = "^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})$",
        message = "Invalid HEX color value")
    private String color;

    public Category toModel() {
        return new Category(name, code, color, order);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
