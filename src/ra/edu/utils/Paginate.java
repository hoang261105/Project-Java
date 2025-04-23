package ra.edu.utils;

import ra.edu.business.model.Course;
import ra.edu.business.model.CourseRegistrationInfo;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Student;

import java.util.List;
import java.util.Scanner;

public class Paginate {
    public static final int COURSES_PER_PAGE = 5;
    public static final int STUDENTS_PER_PAGE = 10;

    public static void paginateCourse(Scanner sc, List<Course> courseList, String role){
        String purpleTextColor = "\u001B[38;5;93m";  // Mã màu ANSI cho màu chữ purple
        String reset = "\u001B[0m";
        int totalPage = (int) Math.ceil((double) courseList.size() / COURSES_PER_PAGE);

        if (courseList.isEmpty()){
            System.err.println("Danh sách trống!");
            return;
        }

        do {
            for (int i = 1; i <= totalPage; i++) {
                System.out.printf("Trang %d\n", i);
            }
            System.out.printf("Lựa chọn của bạn (hoặc nhấn 0 để thoát): ");

            int curPage = Integer.parseInt(sc.nextLine());

            if (curPage == 0) {
                return;
            }

            if (curPage < 0 || curPage > totalPage) {
                System.err.println("Trang không hợp lệ. Vui lòng nhập lại!");
                continue;
            }

            int fromIndex = (curPage - 1) * COURSES_PER_PAGE;
            int toIndex = Math.min(fromIndex + COURSES_PER_PAGE, courseList.size());
            List<Course> paginatedList = courseList.subList(fromIndex, toIndex);

            TableUtils.printHeaderTableCourse(role);
            paginatedList.forEach(course -> {
                course.displayData(role);
                System.out.println(role.equalsIgnoreCase("admin")
                        ? purpleTextColor + "+------------+--------------------------------+------------+-----------------+------------+" + reset
                        : purpleTextColor + "+--------------------------------+------------+-----------------+------------+");
            });
        } while (true);
    }

    public static void paginateStudents(Scanner sc, List<Student> studentList){
        int totalPages = (int) Math.ceil((double) studentList.size() / STUDENTS_PER_PAGE);
        String royalBlueTextColor = "\u001B[38;5;69m";  // Mã màu ANSI cho màu chữ royal blue
        String reset = "\u001B[0m";

        if (studentList.isEmpty()){
            System.err.println("Danh sách sinh viên trống!");
            return;
        }

        do {
            for (int i = 1; i <= totalPages; i++) {
                System.out.printf("Trang %d\n", i);
            }
            System.out.printf("Lựa chọn của bạn (hoặc nhấn 0 để thoát): ");

            int curPage = Integer.parseInt(sc.nextLine());

            if (curPage == 0) {
                return;
            }
            if (curPage < 0 || curPage > totalPages) {
                System.err.println("Trang không hợp lệ. Vui lòng nhập lại!");
                continue;
            }

            int fromIndex = (curPage - 1) * STUDENTS_PER_PAGE;
            int toIndex = Math.min(fromIndex + STUDENTS_PER_PAGE, studentList.size());
            List<Student> paginatedList = studentList.subList(fromIndex, toIndex);

            TableUtils.printHeaderTableStudent();
            paginatedList.forEach(student -> {
                student.displayData();
                System.out.println(royalBlueTextColor + "+------------+--------------------------------+-----------------+----------------------+------------+-----------------+---------------------+" + reset);
            });
        } while (true);
    }

    public static void paginationEnrollment(Scanner sc, List<CourseRegistrationInfo> enrollmentList){
        int totalPages = (int) Math.ceil((double) enrollmentList.size() / STUDENTS_PER_PAGE);
        String cornflowerBlueText = "\u001B[38;5;33m";  // Mã màu ANSI cho chữ Cornflower Blue
        String reset = "\u001B[0m";

        if (enrollmentList.isEmpty()){
            System.err.println("Danh sách trống!");
            return;
        }

        do {
            for (int i = 1; i <= totalPages; i++) {
                System.out.printf("Trang %d\n", i);
            }
            System.out.printf("Lựa chọn của bạn (hoặc nhấn 0 để thoát): ");

            int curPage = Integer.parseInt(sc.nextLine());

            if (curPage == 0) {
                return;
            }
            if (curPage < 0 || curPage > totalPages) {
                System.err.println("Trang không hợp lệ. Vui lòng nhập lại!");
                continue;
            }

            int fromIndex = (curPage - 1) * STUDENTS_PER_PAGE;
            int toIndex = Math.min(fromIndex + STUDENTS_PER_PAGE, enrollmentList.size());
            List<CourseRegistrationInfo> paginatedList = enrollmentList.subList(fromIndex, toIndex);

            TableUtils.printHeaderRegisterStudent();
            paginatedList.forEach(courseRegistrationInfo -> {
                courseRegistrationInfo.displayData();
                System.out.println(cornflowerBlueText + "+-----------------+--------------------------------+--------------+--------------------------------+----------------------+----------------------+" + reset);
            });
        } while (true);
    }
}
