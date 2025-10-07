package br.com.alura.projeto.course.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCode(String code);

    @Modifying
    @Query(nativeQuery = true, value = """
        UPDATE  Course
        SET     status = 'INACTIVE',
                inactivationDate = CURRENT_TIMESTAMP()
        WHERE   code = :code
    """)
    int updateStatusByCode(String code);

}
