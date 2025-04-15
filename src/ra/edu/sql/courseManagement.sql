create database course_management;
use course_management;

-- Bảng admin
create table admin(
    id int primary key auto_increment,
    user_name varchar(50) not null unique,
    password varchar(255) not null
);

-- Bảng học viên
create table students(
    student_id int primary key auto_increment,
    name varchar(100) not null,
    dob date not null,
    email varchar(100) not null unique,
    sex bit not null,
    phone varchar(20) not null unique,
    password varchar(255) not null,
    created_at datetime default current_timestamp
);

-- Bảng khóa học
create table courses(
    course_id int primary key auto_increment,
    name varchar(100) not null,
    duration int not null,
    instructor varchar(100) not null,
    created_at datetime default current_timestamp
);

-- Bảng đăng ký khóa học
create table enrollments(
    id int primary key auto_increment,
    student_id int,
    course_id int,
    foreign key (student_id) references students(student_id),
    foreign key (course_id) references courses(course_id),
    registered_at datetime default current_timestamp,
    status enum('WAITING', 'DENIED', 'CANCER', 'CONFIRM')
);
