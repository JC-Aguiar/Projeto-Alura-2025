package br.com.alura.projeto.login.domain;

import br.com.alura.projeto.category.projection.SimpleCategoryAndCourse;
import br.com.alura.projeto.login.dto.LoginCategoryInfoDTO;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.*;

@Component
public class LoginMapper {

    public List<LoginCategoryInfoDTO> toInfoDTO(SimpleCategoryAndCourse...entities) {
        if (entities == null) return null;
        return toInfoDTO(Arrays.asList(entities));
    }

    public List<LoginCategoryInfoDTO> toInfoDTO(Collection<SimpleCategoryAndCourse> entities) {
        if (entities == null) return null;
        return entities.stream()
            .collect(groupingBy(
                record -> new LoginCategoryInfoDTO(
                    record.getCategory(),
                    record.getCategoryCode(),
                    record.getCategoryColor(),
                    record.getCategoryOrder(),
                    new ArrayList<>()
                ),
                mapping(SimpleCategoryAndCourse::getCourse, toList())
            ))
            .entrySet()
            .stream()
            .map(entry-> {
                var category = entry.getKey();
                var setOfCourses = entry.getValue();
                category.courses().addAll(setOfCourses);
                return category;
            })
            .sorted(Comparator.comparing(LoginCategoryInfoDTO::order))
            .toList();
    }


}
