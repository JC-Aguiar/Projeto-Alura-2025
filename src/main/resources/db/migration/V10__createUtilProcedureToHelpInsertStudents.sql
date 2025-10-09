
DROP PROCEDURE IF EXISTS EnrollStudentsToCourse;

DELIMITER $$
$$
CREATE PROCEDURE EnrollStudentsToCourse (
    IN p_courseCode VARCHAR(50),
    IN p_maxEnrollments INT
)
BEGIN
   INSERT INTO Enrollment (userId, courseId, startedAt)
    SELECT userId, courseId, NOW()
    FROM (
        SELECT u.id AS userId, 
               vc.courseId 
        FROM User u
        CROSS JOIN (
            SELECT c.id AS courseId
            FROM Course c
            WHERE c.code = p_courseCode COLLATE utf8mb4_unicode_ci
        ) AS vc
        WHERE u.role = 'STUDENT'
        AND u.email LIKE '%@example%'
        AND NOT EXISTS (
            SELECT 1 
            FROM Enrollment e
            WHERE e.userId = u.id
              AND e.courseId = vc.courseId
        )
        limit p_maxEnrollments
    ) AS StudentsToEnroll;
end$$
DELIMITER ;
