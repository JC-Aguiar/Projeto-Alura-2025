package br.com.alura.projeto.login.dto;

import java.util.List;
import java.util.Set;

public record LoginCategoryInfoDTO(
    String name,
    String code,
    String color,
    int order,
    List<String> courses) {

    public String stringifyCourses() {
        return String.join(", ", courses);
    }
}
