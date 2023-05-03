CREATE TABLE IF NOT EXISTS university_groups(group_id int not null, group_name varchar(5), student_number int DEFAULT 0);
CREATE TABLE IF NOT EXISTS students(student_id int not null DEFAULT 0, first_name varchar(22), last_name varchar(22), group_name varchar(7));
CREATE TABLE IF NOT EXISTS courses(course_id int not null, course_name varchar(55), course_description varchar(255));
CREATE TABLE IF NOT EXISTS course_assignment(assignment_id int not null,student_id int, course_id int, group_id int);
CREATE TABLE IF NOT EXISTS teachers (teacher_id int not null,  teacher_name varchar(22), teacher_surname varchar(22), teacher_activity varchar(50), primary key(teacher_id));
