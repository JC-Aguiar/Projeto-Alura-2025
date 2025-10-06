package br.com.alura.projeto.category.domain;

import br.com.alura.projeto.category.projection.SimpleCategoryAndCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCode(String code);

    @Query(nativeQuery = true, value = """
        with records as (
            select  category.name           as category,
                    category.code           as categoryCode,
                    category.color          as categoryColor,
                    category.order          as categoryOrder,
                    course.name             as course,
                    row_number() over (
                        partition   by category.id
                        order       by course.id
                    )                       as rowNum
            from    course
            join    category
            on      course.categoryId = category.id
            where   course.status = 'ACTIVE'
        )
        select  *
        from    records
        where   records.rowNum <= :totalCoursesPerCategory
    """)
    List<SimpleCategoryAndCourse> findSomeActiveCoursesWithCategory(int totalCoursesPerCategory);
}
