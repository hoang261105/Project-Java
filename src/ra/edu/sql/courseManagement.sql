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
    student_id char(5) primary key,
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
    course_id char(5) primary key,
    name varchar(100) not null,
    duration int not null,
    instructor varchar(100) not null,
    created_at datetime default current_timestamp
);

INSERT INTO courses (course_id, name, duration, instructor)
VALUES
    ('C0001', 'Java Cơ Bản', 30, 'Nguyễn Văn A'),
    ('C0002', 'Python Nâng Cao', 45, 'Trần Thị B'),
    ('C0003', 'Lập Trình Web Fullstack', 60, 'Lê Văn C'),
    ('C0004', 'SQL Cho Người Mới Bắt Đầu', 20, 'Phạm Hồng D'),
    ('C0005', 'HTML & CSS Cơ Bản', 15, 'Ngô Minh E'),
    ('C0006', 'Kỹ Thuật Lập Trình C++', 35, 'Hoàng Văn F'),
    ('C0007', 'Phân Tích Dữ Liệu Với Excel', 25, 'Lê Thị G'),
    ('C0008', 'Javascript Từ A đến Z', 40, 'Trần Minh H'),
    ('C0009', 'ReactJS Thực Chiến', 50, 'Vũ Thành I'),
    ('C0010', 'Spring Boot Cơ Bản', 55, 'Phan Anh J'),
    ('C0011', 'Flutter Cho Mobile', 45, 'Nguyễn Hữu K'),
    ('C0012', 'NodeJS & Express', 35, 'Đặng Quang L');


-- Bảng đăng ký khóa học
create table enrollments(
    id int primary key auto_increment,
    student_id char(5),
    course_id char(5),
    foreign key (student_id) references students(student_id),
    foreign key (course_id) references courses(course_id),
    registered_at datetime default current_timestamp,
    status enum('WAITING', 'DENIED', 'CANCER', 'CONFIRM')
);

-- Chức năng kiểm tra tài khoản đăng nhập với tư cách là quản trị viên
DELIMITER \\
create procedure check_account(
    user_name_in varchar(50),
    password_in varchar(255)
)
begin
    select * from admin
    where user_name = user_name_in and password = password_in;
end;
DELIMITER \\

-- Chức năng kiểm tra tài khoản đăng nhập với tư cách là học viên
DELIMITER \\
create procedure check_account_user(
    email_in varchar(100),
    password_in varchar(255)
)
begin
    select * from students
    where email = email_in and password = password_in;
end;
DELIMITER \\

-- Các trường hợp tồn tại dữ liệu
DELIMITER \\
-- 1. Trường hợp trùng lặp id khóa học
create procedure is_exist_course_id(
    course_id_in char(5)
)
begin
    select count(*) > 0 as is_exist_courseId from courses
    where course_id = course_id_in;
end;

-- 2. Trường hợp trùng tên khóa học
create procedure is_exist_name(
    name_in varchar(100)
)
begin
    select count(*) > 0 as is_exist_name from courses
    where name = name_in;
end;
DELIMITER \\

-- Các chức năng của bảng khóa học
DELIMITER \\
-- 1. Hiển thị danh sách khóa học và phân trang
create procedure get_all_courses()
begin
    select * from courses;
end;

create procedure pagination_courses(
    current_page int
)
begin
    declare items_per_page int default 5;
    declare offset_value int;

    set offset_value = (current_page - 1) * items_per_page;

    select * from courses
    limit items_per_page offset offset_value;
end;

-- 2. Thêm mới khóa học
create procedure add_course(
    course_id_in char(5),
    course_name varchar(100),
    duration_in int,
    instructor_in varchar(100)
)
begin
    insert into courses(course_id, name, duration, instructor)
    values(course_id_in, course_name, duration_in, instructor_in);
end;

-- 3. Cập nhật khóa học
create procedure update_course(
    course_id_in char(5),
    p_option int,
    course_name varchar(100),
    duration_in int,
    instructor_in varchar(100)
)
begin
    if p_option = 1 then
        update courses set name = course_name where course_id = course_id_in;
    elseif p_option = 2 then
        update courses set duration = duration_in where course_id = course_id_in;
    elseif p_option = 3 then
        update courses set instructor = instructor_in where course_id = course_id_in;
    end if;
end;
drop procedure update_course;

create procedure find_course_by_id(
    course_id_in char(5)
)
begin
    select * from courses where course_id = course_id_in;
end;
DELIMITER \\