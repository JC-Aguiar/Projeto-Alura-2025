CREATE TABLE Enrollment (
    id          bigint(20)  NOT NULL AUTO_INCREMENT,
    userId      bigint(20)  NOT NULL,
    courseId    bigint(20)  NOT NULL,
    startedAt   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pausedAt    datetime    NULL,
    stopedAt    datetime    NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId)    REFERENCES User(id),
    FOREIGN KEY (courseId)  REFERENCES Course(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
