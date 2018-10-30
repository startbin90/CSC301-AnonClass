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
  selected_course int REFERENCES course_section(id),
  -- course_name varchar(40),
  -- section_number varchar(10),
  user_email varchar(50),
  FOREIGN KEY(user_email) REFERENCES client(email)
);

CREATE TABLE course_section(
  id bigserial PRIMARY KEY,
  -- course_code e.g. csc301
  course_code char(6) not null,
  -- e.g. intro to software enginerring
  course_name varchar(40),
  -- e.g L0101
  section_number varchar(10) not null,
  instructor_email varchar(50),
  instructor_name varchar(20) not null,
  time_created date not null,
  locations varchar(20),
  -- geo_location stores the latitude and longitude of the class location
  -- the data type point - combines (x,y) which can be your lat / long. 
  -- Occupies 16 bytes: 2 float8 numbers internally.
  -- can be null 
  geo_location point,
  UNIQUE (course_name, section_number),
  FOREIGN KEY(instructor_email) REFERENCES client(email)
);
