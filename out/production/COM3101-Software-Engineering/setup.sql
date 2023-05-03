CREATE TABLE IF NOT EXISTS staff
(
    staff_id VARCHAR(10) PRIMARY KEY,
    name     VARCHAR(50)  NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS course
(
    course_code VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS session
(
    session_id  VARCHAR(10) NOT NULL,
    weekday     TEXT        NOT NULL,
    time        TEXT        NOT NULL,
    course_code VARCHAR(10) NOT NULL,
    teacher     VARCHAR(50) NOT NULL,
    capacity    INTEGER     NOT NULL,
    PRIMARY KEY (session_id, course_code),
    FOREIGN KEY (course_code) REFERENCES course (course_code) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS student
(
    student_id VARCHAR(10) PRIMARY KEY,
    gender     VARCHAR(10) NOT NULL,
    name       VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS enrollment
(
    student_id  VARCHAR(10) NOT NULL,
    course_code VARCHAR(10) NOT NULL,
    session_id  VARCHAR(10) NOT NULL,
    PRIMARY KEY (student_id, course_code, session_id),
    FOREIGN KEY (student_id) REFERENCES student (student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_code, session_id) REFERENCES session (course_code, session_id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS add_drop_entry
(
    entry_id            INTEGER     NOT NULL PRIMARY KEY AUTOINCREMENT,
    student_id          VARCHAR(10) NOT NULL,
    course_code_to_add  VARCHAR(10),
    session_id_to_add   VARCHAR(10),
    course_code_to_drop VARCHAR(10),
    session_id_to_drop  VARCHAR(10),
    status              VARCHAR(10) NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (student_id) REFERENCES student (student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_code_to_add, session_id_to_add) REFERENCES session (course_code, session_id) ON DELETE CASCADE,
    FOREIGN KEY (course_code_to_drop, session_id_to_drop) REFERENCES session (course_code, session_id) ON DELETE CASCADE
);
-- Insert data into the staff table
INSERT INTO staff (staff_id, name, password)
VALUES ('S001', 'Prof Poon', 'password'),
       ('S002', 'Dr Kong', 'password'),
       ('S003', 'Dr KKC', 'password');
-- Insert data into the course table
INSERT INTO course (course_code, course_name)
VALUES ('COM1000',
        'Contemporary Information Technologies'),
       ('COM1005', 'Excel VBA Programming'),
       ('COM1006',
        'Artificial Intelligence and Its Applications'),
       ('COM1101', 'Programming Methodology'),
       ('COM1102', 'Programming and Data Structures'),
       ('COM2001', 'Information Systems in Business'),
       ('COM2003', 'Introduction to Financial Computing'),
       ('COM2103', 'Database Design and Management'),
       ('COM3101', 'Software Engineering'),
       ('COM3102',
        'Computing Technologies in Web Applications'),
       ('COM3103', 'Artificial Intelligence'),
       ('COM3015', 'E-commerce Application Development');
-- Insert data into the session table
INSERT INTO session (session_id,
                     weekday,
                     time,
                     course_code,
                     teacher,
                     capacity)
VALUES ('L01',
        'Mon',
        'Evening',
        'COM1000',
        'Prof Poon',
        10),
       ('L02',
        'Tue',
        'Evening',
        'COM1000',
        'Dr Kong',
        15),
       ('L01', 'Sat', 'Morning', 'COM2001', 'Dr KKC', 10);
-- Insert data into the student table
INSERT INTO student (student_id, gender, name)
VALUES ('A001', 'Male', 'John'),
       ('A002', 'Female', 'Jane'),
       ('A003', 'Male', 'Bob'),
       ('A004', 'Female', 'Alice');
-- Insert data into the enrollment table
INSERT INTO enrollment (student_id, course_code, session_id)
VALUES ('A001', 'COM1000', 'L01'),
       ('A002', 'COM1000', 'L01'),
       ('A003', 'COM1000', 'L01'),
       ('A004', 'COM1000', 'L01'),
       ('A001', 'COM1000', 'L02'),
       ('A002', 'COM1000', 'L02'),
       ('A003', 'COM1000', 'L02'),
       ('A001', 'COM2001', 'L01'),
       ('A002', 'COM2001', 'L01'),
       ('A003', 'COM2001', 'L01');
-- Insert data into the add_drop_entry table
INSERT INTO add_drop_entry (student_id,
                            course_code_to_add,
                            session_to_add,
                            course_code_to_drop,
                            session_to_drop)
VALUES ('A001', 'COM1000', 'L02', 'COM1000', 'L01'),
       ('A002', 'COM1000', 'L02', 'COM1000', 'L01'),
       ('A003', 'COM2001', 'L01', 'COM1000', 'L01');