package ra.edu.utils;

import ra.edu.business.model.Course;
import ra.edu.business.model.CourseRegistrationInfo;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Status;
import ra.edu.presentation.CourseUI;
import ra.edu.presentation.EnrollmentUI;
import ra.edu.validate.Validator;

import java.security.PublicKey;
import java.util.List;
import java.util.Scanner;

public class SelectCourse {
    public static String selectCourse(Scanner sc) {
        List<Course> courseList = CourseUI.courseService.findAllOfRole("admin");

        if (courseList.isEmpty()) {
            System.err.println("Không có khóa học nào để chọn.");
            return null;
        }

        do {
            final String RESET = "\u001B[0m";
            final String DARK_ORANGE = "\u001B[93m"; // Gần giống màu cam đậm (dark orange)

            System.out.println(DARK_ORANGE + "╔═══════════════════════════════════════╗");
            System.out.println("║       ==== CHỌN KHÓA HỌC ====         ║");
            System.out.println("╠═══════════════════════════════════════╣");

            for (int i = 0; i < courseList.size(); i++) {
                System.out.printf("║ %-2d. %-33s ║\n", i + 1, courseList.get(i).getCourseName());
            }
            System.out.println("║ 0 . Trở về menu chính                 ║");
            System.out.println("╚═══════════════════════════════════════╝" + RESET);
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            if (choice == 0) {
                return null;
            }

            if (choice < 1 || choice > courseList.size()) {
                System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                continue;
            }

            Course selectCourse = courseList.get(choice - 1);
            String courseId = selectCourse.getCourseId();

            boolean isExist = courseList.stream().anyMatch(course -> course.getCourseId().equals(courseId));

            if (isExist) {
                return courseId;
            }

            System.err.println("Id không tồn tại. Vui lòng nhập lại");
        } while (true);
    }

    public static String selectStudent(Scanner sc, String courseId) {
        List<CourseRegistrationInfo> courseRegistrationInfoList = EnrollmentUI.enrollmentService.findCourseRegistrationByStudent(courseId);

        List<CourseRegistrationInfo> filterStudentByCourse = courseRegistrationInfoList.stream()
                .filter(courseRegistrationInfo -> courseRegistrationInfo.getStatus() == Status.WAITING)
                .toList();

        if (filterStudentByCourse.isEmpty()) {
            return "no data";
        }

        do {
            final String RESET = "\u001B[0m";
            final String LIME_GREEN = "\u001B[92m"; // Màu gần giống lime green

            System.out.println(LIME_GREEN + "╔═══════════════════════════════════════╗");
            System.out.println("║  ==== CHỌN SINH VIÊN MUỐN DUYỆT ====  ║");
            System.out.println("╠═══════════════════════════════════════╣");

            for (int i = 0; i < filterStudentByCourse.size(); i++) {
                System.out.printf("║ %-2d. %-33s ║\n", i + 1, filterStudentByCourse.get(i).getStudentName());
            }
            System.out.println("║ 0 . Trở về menu chính                 ║");
            System.out.println("╚═══════════════════════════════════════╝" + RESET);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            if (choice == 0) {
                return null;
            }

            if (choice < 1 || choice > filterStudentByCourse.size()) {
                System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                continue;
            }

            CourseRegistrationInfo selectStudent = filterStudentByCourse.get(choice - 1);
            String studentId = selectStudent.getStudentId();

            boolean isExist = filterStudentByCourse.stream().anyMatch(courseRegistrationInfo -> courseRegistrationInfo.getStudentId().equals(studentId));

            if (isExist) {
                return studentId;
            }

            System.err.println("Id sinh viên không tồn tại. Vui lòng nhập lại");
        } while (true);
    }

    public static String selectStudentToDelete(Scanner sc, String courseId) {
        List<CourseRegistrationInfo> courseRegistrationInfoList = EnrollmentUI.enrollmentService.findCourseRegistrationByStudent(courseId);

        List<CourseRegistrationInfo> filterStudentByCourse = courseRegistrationInfoList.stream()
                .filter(courseRegistrationInfo -> courseRegistrationInfo.getStatus() == Status.CANCELLED || courseRegistrationInfo.getStatus() == Status.DENIED)
                .toList();

        if (filterStudentByCourse.isEmpty()) {
            return "no data";
        }

        do {
            final String RESET = "\u001B[0m";
            final String BLUE_VIOLET = "\u001B[95m"; // Gần giống màu blue violet (bright magenta)

            System.out.println(BLUE_VIOLET + "╔════════════════════════════════════════════════════════════╗");
            System.out.println("║              ==== CHỌN SINH VIÊN MUỐN XÓA ====             ║");
            System.out.println("╠════════════════════════════════════════════════════════════╣");

            for (int i = 0; i < filterStudentByCourse.size(); i++) {
                System.out.printf("║ %-2d. %-43s (%s) ║\n",
                        i + 1,
                        filterStudentByCourse.get(i).getStudentName(),
                        filterStudentByCourse.get(i).getStatus());
            }
            System.out.println("║ 0 . Trở về menu chính                                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝" + RESET);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            if (choice == 0) {
                return null;
            }

            if (choice < 1 || choice > filterStudentByCourse.size()) {
                System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                continue;
            }

            CourseRegistrationInfo selectStudent = filterStudentByCourse.get(choice - 1);
            String studentId = selectStudent.getStudentId();

            boolean isExist = filterStudentByCourse.stream().anyMatch(courseRegistrationInfo -> courseRegistrationInfo.getStudentId().equals(studentId));

            if (isExist) {
                return studentId;
            }

            System.err.println("Id sinh viên không tồn tại. Vui lòng nhập lại");
        } while (true);
    }

    public static void selectApprovalOrDeny(Scanner sc, String studentId, String courseId) {
        String hotPinkText = "\u001B[38;5;213m";  // Mã màu ANSI cho chữ hot pink
        String reset = "\u001B[0m";  // Mã reset màu

        System.out.println(hotPinkText + "╔══════════════════════════════════╗" + reset);
        System.out.println(hotPinkText + "║       ==== MENU TÙY CHỌN ====    ║" + reset);
        System.out.println(hotPinkText + "╠══════════════════════════════════╣" + reset);
        System.out.println(hotPinkText + "║  1. Duyệt                        ║" + reset);
        System.out.println(hotPinkText + "║  2. Từ chối                      ║" + reset);
        System.out.println(hotPinkText + "╚══════════════════════════════════╝" + reset);

        int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

        switch (choice) {
            case 1:
                System.out.println("Bạn có chắc chắn muốn duyệt khóa học cho sinh viên này không?");
                String confirm = sc.nextLine();

                if (confirm.equals("y")) {
                    boolean result = EnrollmentUI.enrollmentService.studentApproval(courseId, studentId, 1);
                    if (result) {
                        System.out.println("Duyệt thành công!");
                    } else {
                        System.err.println("Duyêt thất bại!");
                    }
                } else if (confirm.equals("n")) {
                    System.out.println("Hủy xác nhân duyệt!");
                }
                return;
            case 2:
                System.out.println("Bạn có chắc chắn muốn từ chối đăng ký khóa học cho sinh viên này không?");
                String confirmDeny = sc.nextLine();

                if (confirmDeny.equals("y")) {
                    boolean result = EnrollmentUI.enrollmentService.studentApproval(courseId, studentId, 2);
                    if (result) {
                        System.out.println("Tu chối thành công!");
                    } else {
                        System.err.println("Từ chối thất bại!");
                    }
                } else if (confirmDeny.equals("n")) {
                    System.out.println("Hủy xác nhân từ chối!");
                }
                return;
            default:
                System.err.println("Vui lòng chon lại 1 - 2!");
        }
    }
}
