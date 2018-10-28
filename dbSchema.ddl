drop schema if exists dbSchema cascade;
create schema dbSchema;
set search_path to dbSchema;

create table user(
	email varchar(50) primary key,
	pwdHash char(32) not null,
	utorid char(8) unique,
	firstName varchar(10) not null,
	lastName varchar(10) not null,
	studentFlag boolean not null

);

create table course_user(
	id int auto_increment key,
  course_name varchar(40),
  section_number int,
	user_email varchar(50),
  FOREIGN KEY(user_email) REFERENCES user(email)
);

create table course_section(
  id int auto_increment key,
	course_name varchar(40),
  section_number int,
  teacher_id int,
  CONSTRAINT course_section UNIQUE (course_name, section_number)
);

