package br.com.alura.projeto.category.domain;

import br.com.alura.projeto.category.projection.SimpleCategoryAndCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCode(String code);

    @Query(nativeQuery = true, value = """
        with Records as (
            select  Category.name           as category,
                    Category.code           as categoryCode,
                    Category.color          as categoryColor,
                    Category.order          as categoryOrder,
                    Course.name             as course,
                    row_number() over (
                        partition   by Category.id
                        order       by Course.id
                    )                       as rowNum
            from    Course
            join    Category
            on      Course.categoryId = Category.id
            where   Course.status = 'ACTIVE'
        )
        select  *
        from    Records
        where   Records.rowNum <= :totalCoursesPerCategory
    """)
    List<SimpleCategoryAndCourse> findSomeActiveCoursesWithCategory(int totalCoursesPerCategory);
}
