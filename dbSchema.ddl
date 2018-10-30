DROP SCHEMA IF EXISTS dbSchema CASCADE;
CREATE SCHEMA dbSchema;
SET search_path TO dbSchema;

CREATE TABLE client(
  email varchar(50) PRIMARY KEY,
  pwdHash char(32) NOT null,
  utorid char(8) UNIQUE,
  firstName varchar(10) NOT null,
  lastName varchar(10) NOT null,
  studentFlag boolean NOT null  
);

CREATE TABLE course_user(
  id bigserial PRIMARY KEY,
  course_name varchar(40),
  section_number int,
  user_email varchar(50),
  FOREIGN KEY(user_email) REFERENCES client(email)
);

CREATE TABLE course_section(
  id bigserial PRIMARY KEY,
  course_name varchar(40),
  section_number int,
  instructor_email varchar(50),
  instructor_name varchar(20),
  locations varchar(20),
  UNIQUE (course_name, section_number),
  FOREIGN KEY(instructor_email) REFERENCES client(email)
);
