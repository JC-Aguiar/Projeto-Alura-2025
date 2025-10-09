INSERT INTO Enrollment (userId, courseId, startedAt)
select u.id, c.id, NOW()
FROM User u
INNER JOIN Course c
ON  c.instructorEmail = u.email
WHERE u.email IN (
    'joao.costal@alura.com',
    'felipe.andrade@alura.com',
    'alberto.santos@alura.com',
    'carol.luz@alura.com',
    'maria.alberta@alura.com',
    'carol.luz@alura.com',
    'felipe.andrade@alura.com',
    'victor.cunha@alura.com',
    'victor.cunha@alura.com',
    'cezar.arquiles@alura.com',
    'lucas.montano@alura.com',
    'galego.oficial@alura.com',
    'pero.baptista@alura.com',
    'anderson.freitas@alura.com',
    'dev.instructor@alura.com',
    'dev.instructor@alura.com',
    'carol.luz@alura.com',
    'mario.luiz@alura.com',
    'felipe.braga@alura.com',
    'calor.luz@alura.com',
    'lucas.montano@alura.com',
    'antonio.junqueira@alura.com',
    'lucas.montano@alura.com',
    'alberto.santos@alura.com',
    'anderson.freitas@alura.com'
);