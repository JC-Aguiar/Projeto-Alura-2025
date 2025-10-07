package br.com.alura.projeto.course.domain;

import br.com.alura.projeto.course.dto.CourseAndCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        select  c as course,
                c.category.id as categoryId
        from    Course c
        where   c.id = :id
    """)
    CourseAndCategoryId findCourseAndCategoryIdByCourseBy(Long id);

    @Query(nativeQuery = true, value = """
        select  count(1)
        from    Course
        where   code = :code
        and     id != :id;
    """)
    int countUniqueCodePerId(String code, Long id);


    @Modifying
    @Query(nativeQuery = true, value = """
        update  Course
        set     status = 'INACTIVE',
                inactivationDate = CURRENT_TIMESTAMP()
        where   code = :code
    """)
    int updateStatusByCode(String code);

}
