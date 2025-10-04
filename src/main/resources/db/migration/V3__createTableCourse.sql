CREATE TABLE course (
    id                  bigint(20) AUTO_INCREMENT,
    name                varchar(256) NOT NULL,
    code                varchar(10) NOT NULL UNIQUE,
    description         varchar(300),
    instructorEmail     varchar(150) NOT NULL,
    status              enum('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    inactivationDate    datetime,
    categoryId          bigint(20),
    PRIMARY KEY (id),
    FOREIGN KEY (categoryId) REFERENCES category(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;