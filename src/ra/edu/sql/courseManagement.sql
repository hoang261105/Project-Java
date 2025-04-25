create database course_management;
use course_management;

-- Bảng account
create table account
(
    id       char(5) primary key unique,
    email    varchar(50)               not null unique,
    password varchar(255)              not null,
    role     enum ('ADMIN', 'STUDENT') default 'STUDENT',
    status   enum ('ACTIVE', 'INACTIVE', 'BLOCKED') default 'ACTIVE'
);

-- Bảng học viên
create table students
(
    student_id char(5) primary key unique,
    name       varchar(100) not null,
    dob        date         not null,
    email      varchar(100) not null unique,
    sex        bit          not null,
    phone      varchar(20)  not null unique,
    password   varchar(255) not null,
    created_at datetime default current_timestamp
);

INSERT INTO students (student_id, name, dob, email, sex, phone, password)
VALUES
    ('SV001', 'Nguyễn Văn A', '2000-01-01', 'vana01@gmail.com', 1, '0901000001', 'pass123'),
    ('SV002', 'Trần Thị B', '2000-02-02', 'tranb02@gmail.com', 0, '0901000002', 'pass123'),
    ('SV003', 'Lê Văn C', '2000-03-03', 'levanc03@gmail.com', 1, '0901000003', 'pass123'),
    ('SV004', 'Phạm Thị D', '2000-04-04', 'phamd04@gmail.com', 0, '0901000004', 'pass123'),
    ('SV005', 'Hoàng Văn E', '2000-05-05', 'hoange05@gmail.com', 1, '0901000005', 'pass123'),
    ('SV006', 'Đinh Thị F', '2000-06-06', 'dinhf06@gmail.com', 0, '0901000006', 'pass123'),
    ('SV007', 'Ngô Văn G', '2000-07-07', 'ngog07@gmail.com', 1, '0901000007', 'pass123'),
    ('SV008', 'Võ Thị H', '2000-08-08', 'voh08@gmail.com', 0, '0901000008', 'pass123'),
    ('SV009', 'Bùi Văn I', '2000-09-09', 'buivani09@gmail.com', 1, '0901000009', 'pass123'),
    ('SV010', 'Mai Thị J', '2000-10-10', 'maij10@gmail.com', 0, '0901000010', 'pass123'),
    ('SV011', 'Phan Văn K', '2000-11-11', 'phank11@gmail.com', 1, '0901000011', 'pass123'),
    ('SV012', 'Lý Thị L', '2000-12-12', 'lyl12@gmail.com', 0, '0901000012', 'pass123'),
    ('SV013', 'Trịnh Văn M', '2001-01-13', 'trinhm13@gmail.com', 1, '0901000013', 'pass123'),
    ('SV014', 'Đoàn Thị N', '2001-02-14', 'doann14@gmail.com', 0, '0901000014', 'pass123'),
    ('SV015', 'Hồ Văn O', '2001-03-15', 'hoovo15@gmail.com', 1, '0901000015', 'pass123');

-- Bảng khóa học
create table courses
(
    course_id  char(5) primary key unique,
    name       varchar(100) not null,
    duration   int          not null,
    instructor varchar(100) not null,
    created_at datetime default current_timestamp
);

INSERT INTO courses (course_id, name, duration, instructor)
VALUES ('C0001', 'Java Cơ Bản', 30, 'Nguyễn Văn A'),
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
create table enrollments
(
    id            int primary key auto_increment,
    student_id    char(5),
    course_id     char(5),
    foreign key (student_id) references students (student_id),
    foreign key (course_id) references courses (course_id),
    registered_at datetime default current_timestamp,
    status        enum ('WAITING', 'DENIED', 'CANCELLED', 'CONFIRMED') default 'WAITING'
);

-- Chức năng kiểm tra tài khoản đăng nhập là admin hay student
DELIMITER \\
create procedure check_account(
    email_in varchar(50),
    password_in varchar(255)
)
begin
    select *
    from account
    where email = email_in
      and password = password_in;
end;
DELIMITER \\

-- Các trường hợp tồn tại dữ liệu
DELIMITER \\
-- 1. Trường hợp trùng lặp id khóa học
create procedure is_exist_course_id(
    course_id_in char(5)
)
begin
    select count(*) > 0 as is_exist_courseId
    from courses
    where course_id = course_id_in;
end;

-- 2. Trường hợp trùng tên khóa học
CREATE PROCEDURE is_exist_name(
    IN name_in VARCHAR(100),
    IN id_in VARCHAR(20)
)
BEGIN
    DECLARE normalized_input VARCHAR(100);
    SET normalized_input = REGEXP_REPLACE(TRIM(IFNULL(name_in, '')), '[[:space:]]+', '');

    SELECT COUNT(*) > 0 AS is_exist_name
    FROM (
             SELECT course_id, REGEXP_REPLACE(TRIM(name), '[[:space:]]+', '') AS normalized_name
             FROM courses
         ) AS normalized_courses
    WHERE normalized_name = normalized_input
      AND (id_in IS NULL OR course_id != id_in);
END;

-- 3. Trường hợp trùng mã sinh viên
create procedure is_exist_student_id(
    student_id_in char(5)
)
begin
    select count(*) > 0 as exist_stu_id
    from students
    where student_id = student_id_in;
end;

-- 4. Trường hợp trùng email
create procedure is_exist_email(
    email_in varchar(100)
)
begin
    select count(*) > 0 as exist_email
    from students
    where email = email_in;
end;

-- 5. Trường hợp trùng số điện thoại
create procedure is_exist_phone(
    phone_number_in varchar(20)
)
begin
    select count(*) > 0 as exist_phone
    from students
    where phone = phone_number_in;
end;
DELIMITER \\

-- Các chức năng của bảng khóa học
DELIMITER \\
-- 1. Hiển thị danh sách khóa học và phân trang
create procedure get_all_courses(
    role varchar(20)
)
begin
    if role = 'admin' then
        select * from courses
        order by name;
    elseif role = 'student' then
        select name, duration, instructor, created_at from courses
        order by name;
    end if;
end;

create procedure pagination_courses(
    current_page int,
    items_per_page int
)
begin
    declare offset_value int;

    set offset_value = (current_page - 1) * items_per_page;

    select *
    from courses
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
    values (course_id_in, course_name, duration_in, instructor_in);
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

-- Chưc năng tìm khóa học theo id
create procedure find_course_by_id(
    course_id_in char(5)
)
begin
    select * from courses where course_id = course_id_in;
end;

-- Chức năng xóa khóa học
create procedure delete_course(
    course_id_in char(5)
)
begin
    delete
    from courses
    where course_id = course_id_in;
end;

-- Chức năng tìm kiếm khóa học có phân trang
create procedure search_course(
    course_name_in varchar(100)
)
begin
    select *
    from courses
    where name like concat('%', course_name_in, '%');
end;

-- Chức năng sắp xếp khóa học
create procedure sort_course(
    s_option int
)
begin
    if s_option = 1 then
        select *
        from courses
        order by course_id;
    elseif s_option = 2 then
        select *
        from courses
        order by course_id desc;
    elseif s_option = 3 then
        select *
        from courses
        order by name;
    elseif s_option = 4 then
        select *
        from courses
        order by name desc;
    end if;
end;
DELIMITER \\

-- Các chức năng của quản lí học viên
DELIMITER \\
-- 1. Xem danh sách học viên có phân trang
create procedure get_all_students()
begin
    select * from students
    order by name;
end;

create procedure paginate_students(
    current_page int,
    items_per_page int
)
begin
    declare offset_value int;

    set offset_value = (current_page - 1) * items_per_page;

    select *
    from students
    limit items_per_page offset offset_value;
end;

-- 2. Thêm mới học viên
create procedure add_student(
    student_id_in char(5),
    student_name varchar(100),
    dob_in date,
    email_in varchar(100),
    sex_in bit,
    phone_in varchar(20),
    password_in varchar(255)
)
begin
    insert into students(student_id, name, dob, email, sex, phone, password)
    values (student_id_in, student_name, dob_in, email_in, sex_in, phone_in, password_in);
end;

-- Tạo tài khoản cho học viên
create procedure add_account(
    stu_id_in char(5),
    email_in varchar(100),
    password_in varchar(255)
)
begin
    insert into account(id, email, password)
    values (stu_id_in,email_in, password_in);
end;

-- 3. Cập nhật thông tin học viên
create procedure update_student(
    student_id_in char(5),
    s_option int,
    student_name varchar(100),
    dob_in date,
    email_in varchar(100),
    sex_in bit,
    phone_in varchar(20),
    password_in varchar(255)
)
begin
    if s_option = 1 then
        update students
        set name = student_name
        where student_id = student_id_in;
    elseif s_option = 2 then
        update students
        set dob = dob_in
        where student_id = student_id_in;
    elseif s_option = 3 then
        update students
        set email = email_in
        where student_id = student_id_in;

        update account
        set email = email_in
        where id = student_id_in;
    elseif s_option = 4 then
        update students
        set sex = sex_in
        where student_id = student_id_in;
    elseif s_option = 5 then
        update students
        set phone = phone_in
        where student_id = student_id_in;
    elseif s_option = 6 then
        update students
        set password = password_in
        where student_id = student_id_in;

        update account
        set password = password_in
        where id = student_id_in;
    end if;
end;

create procedure find_student_by_id(
    student_id_in char(5)
)
begin
    select * from students
    where student_id = student_id_in;
end;

-- 4. Xóa học viên
create procedure delete_student(
    stu_id_in char(5)
)
begin
    delete from students
    where student_id = stu_id_in;

    delete from account
    where id = stu_id_in;
end;

-- 5. Tìm kiếm học viên
create procedure search_student(
    stu_id_in char(5),
    s_option int,
    stu_name_in varchar(100),
    email_in varchar(100)
)
begin
    if s_option = 1 then
        select * from students
        where student_id like concat('%', stu_id_in, '%');
    elseif s_option = 2 then
        select * from students
        where name like concat('%', stu_name_in, '%');
    elseif s_option = 3 then
        select * from students
        where email like concat('%', email_in, '%');
    end if;
end;

-- 6. Sắp xếp học viên
create procedure sort_student(
    s_option int
)
begin
    if s_option = 1 then
        select * from students
        order by name desc;
    elseif s_option = 2 then
        select * from students
        order by student_id;
    elseif s_option = 3 then
        select * from students
        order by student_id desc;
    end if;
end;
DELIMITER \\

-- Các chức năng bên học viên
DELIMITER \\
-- 1. Đăng ký khóa học
create procedure register_course(
    stu_id_in char(5),
    course_id_in char(5)
)
begin
    insert into enrollments(student_id, course_id)
    values (stu_id_in, course_id_in);
end;

-- Trường hợp sinh viên đã đăng ky khóa hoc đó rồi
create procedure check_enrollment_exist(
    stu_id_in char(5),
    course_id_in char(5)
)
begin
    select count(*) > 0 as enrollment_exist
    from enrollments
    where student_id = stu_id_in and course_id = course_id_in and status in ('WAITING', 'CONFIRMED');
end;

-- 3. Xem khóa học đã đăng ký
create procedure show_register_course(
    stu_id_in char(5)
)
begin
    select c.course_id, c.name, c.instructor, c.duration, e.registered_at, e.status
    from courses c
    join enrollments e on c.course_id = e.course_id
    where e.student_id = stu_id_in
    order by e.registered_at desc;
end;

-- 4. Hủy đăng ký
create procedure cancel_register_course(
    stu_id_in char(5),
    course_id_in char(5)
)
begin
    update enrollments
    set status = 'CANCELLED'
    where student_id = stu_id_in and course_id = course_id_in;
end;

create procedure find_register_course(
    stu_id_in char(5),
    course_id_in char(5)
)
begin
    select * from enrollments
    where student_id = stu_id_in and course_id = course_id_in;
end;

-- 5. Đổi mật khẩu
create procedure change_password(
    stu_id_in char(5),
    new_password varchar(255)
)
begin
    update account
    set password = new_password
    where id = stu_id_in;

    update students
    set password = new_password
    where student_id = stu_id_in;
end;
DELIMITER \\

-- Các chức năng của quản lý đăng ký khóa học
DELIMITER \\
-- 1. Hiển thị danh sách sinh viên đăng ký theo từng khóa học có phân trang
create procedure register_course_by_student(
    course_id_in char(5)
)
begin
    select
        c.course_id,
        c.name as course_name,
        s.student_id,
        s.name as student_name,
        e.registered_at,
        e.status
    from enrollments e
    join students s ON e.student_id = s.student_id
    join courses c ON e.course_id = c.course_id
    where c.course_id = course_id_in
    order by s.name;
end;

-- 2. Duyệt sinh viên đăng ký khóa học
create procedure student_approval(
    course_id_in char(5),
    student_id_in char(5),
    e_option int
)
begin
    if e_option = 1 then
        update enrollments
        set status = 'CONFIRMED'
        where student_id = student_id_in and course_id = course_id_in and status = 'WAITING';
    elseif e_option = 2 then
        update enrollments
        set status = 'DENIED'
        where student_id = student_id_in and course_id = course_id_in and status = 'WAITING';
    end if;
end;

-- 3. Xóa sinh viên đăng ký khỏi khóa học
create procedure remove_student(
    course_id_in char(5),
    student_id_in char(5)
)
begin
    delete from enrollments
    where course_id = course_id_in and student_id = student_id_in and status in ('CANCELLED', 'DENIED');
end;
DELIMITER \\

-- Các chức năng thống kê
DELIMITER \\
-- 1. Thống kê tổng số lượng khóa học và tổng số học viên
create procedure get_total_course_student()
begin
    SELECT
        (SELECT COUNT(course_id) FROM courses) AS count_course,
        (SELECT COUNT(student_id) FROM students) AS count_student;
end;

-- 2. Thống kê tổng số học viên theo từng khóa
create procedure get_total_student_of_course()
begin
    select c.name as course_name, count(CASE WHEN e.status = 'CONFIRMED' THEN s.student_id END) as count_student
    from courses c
    left join enrollments e on c.course_id = e.course_id
    left join students s on s.student_id = e.student_id
    group by c.name
    order by count_student desc, c.name;
end;

-- 3. Thống kê top 5 khóa học đông sinh viên nhất
create procedure get_top5_course_most_student()
begin
    select c.name as course_name, count(CASE WHEN e.status = 'CONFIRMED' THEN s.student_id END) as count_student
    from courses c
    join enrollments e on c.course_id = e.course_id
    join students s on s.student_id = e.student_id
    group by c.name
    order by count_student desc
    limit 5;
end;

-- 4. Liệt kê các khóa học có trên 10 học viên
create procedure get_course_more_than_10students()
begin
    select c.name as course_name, count(CASE WHEN e.status = 'CONFIRMED' THEN s.student_id END) as count_student
    from courses c
             join enrollments e on c.course_id = e.course_id
             join students s on s.student_id = e.student_id
    group by c.name
    having count_student > 10
    order by count_student desc;
end;
DELIMITER \\
