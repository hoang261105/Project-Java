package ra.edu.presentation;

import ra.edu.business.model.*;
import ra.edu.business.service.enrollment.EnrollmentService;
import ra.edu.business.service.enrollment.EnrollmentServiceImp;
import ra.edu.utils.Paginate;
import ra.edu.utils.SelectCourse;
import ra.edu.utils.TableUtils;
import ra.edu.validate.RegexRule;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class UserUI {

    private static final EnrollmentService enrollmentService = new EnrollmentServiceImp();

    public static void printMenuUser(Scanner sc) {
        UserUI userUI = new UserUI();
        while (true) {
            printMainMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1 -> userUI.displayCourse(sc);
                case 2 -> userUI.registerCourse(sc);
                case 3 -> userUI.getRegisterCourseByStudent();
                case 4 -> userUI.cancelRegisterCourse(sc);
                case 5 -> userUI.changePassword(sc);
                case 6 -> {
                    if (confirmLogout(sc)) return;
                }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 6!");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\u001B[36m╔═════════════════════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[35m║                      MENU HỌC VIÊN                      ║\u001B[0m");
        System.out.println("\u001B[36m╠═════════════════════════════════════════════════════════╣\u001B[0m");
        System.out.println("\u001B[32m║ 1. Xem danh sách khóa học                               ║\u001B[0m");
        System.out.println("\u001B[33m║ 2. Đăng ký khóa học                                     ║\u001B[0m");
        System.out.println("\u001B[34m║ 3. Xem khóa học đã đăng ký                              ║\u001B[0m");
        System.out.println("\u001B[31m║ 4. Hủy đăng ký khóa học                                 ║\u001B[0m");
        System.out.println("\u001B[35m║ 5. Đổi mật khẩu                                         ║\u001B[0m");
        System.out.println("\u001B[37m║ 6. Đăng xuất                                            ║\u001B[0m");
        System.out.println("\u001B[36m╚═════════════════════════════════════════════════════════╝\u001B[0m");
    }

    private static boolean confirmLogout(Scanner sc) {
        System.out.println("Bạn có chắc chắn đăng xuất không?");
        String confirm = sc.nextLine();
        return confirm.equalsIgnoreCase("y");
    }

    public void displayCourse(Scanner sc) {
        while (true) {
            printCourseMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1 -> Paginate.paginateCourse(sc, CourseUI.courseService.findAllOfRole("student"), "student");
                case 2 -> searchCourseByName(sc);
                case 3 -> {return;}
                default -> System.err.println("Vui lòng chọn lại từ 1 - 3!");
            }
        }
    }

    private static void printCourseMenu() {
        System.out.println("\u001B[33m╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  1. Danh sách khóa học có phân trang                     ║");
        System.out.println("║  2. Tìm kiếm khóa học theo tên                           ║");
        System.out.println("║  3. Trở về menu học viên                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\u001B[0m");
    }

    private void searchCourseByName(Scanner sc) {
        System.out.println("Nhập vào tên khóa học cần tìm:");
        String courseName = sc.nextLine();
        List<Course> filterCourseName = CourseUI.courseService.searchCourseByName(courseName);

        if (filterCourseName.isEmpty()) {
            System.err.println("Không có tên khóa học phù hợp!");
        } else {
            Paginate.paginateCourse(sc, filterCourseName, "student");
        }
    }

    public void registerCourse(Scanner sc) {
        System.out.println("Nhập vào số khóa học cần đăng ký:");
        int size = Integer.parseInt(sc.nextLine());

        Account acc = Session.currentAccount;
        System.out.println("Id" + acc.getId());

        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin đăng ký khóa học thứ " + (i + 1));
            String courseId = SelectCourse.selectCourse(sc);
            if (courseId == null) {
                System.out.println("Đã quay về menu chính.");
                return;
            }

            registerCourseForStudent(sc, acc.getId(), courseId);
        }
    }

    private void registerCourseForStudent(Scanner sc, String studentId, String courseId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseId);
        enrollment.setStudentId(studentId);

        if (enrollmentService.existsEnrollment(studentId, courseId)) {
            System.err.println("Bạn đã đăng ký khóa học này!");
        } else {
            boolean result = enrollmentService.registerCourse(enrollment);
            if (result) {
                System.out.println("Đăng kí khóa học thành công!");
            } else {
                System.err.println("Đăng kí khóa học thất bại");
            }
        }
    }

    public void getRegisterCourseByStudent() {
        Account acc = Session.currentAccount;
        List<EnrolledCourses> enrolledCoursesList = enrollmentService.getEnrolledCoursesByStudent(acc.getId());

        String cornflowerBlueText = "\u001B[38;5;33m";  // Mã màu ANSI cho chữ Cornflower Blue
        String reset = "\u001B[0m";

        TableUtils.printRegisterCourseHeader();
        enrolledCoursesList.forEach(enrolledCourses -> {
            enrolledCourses.displayData();
            System.out.println(cornflowerBlueText + "+-----------------+------------------------------------------+-----------------+--------------------------+----------------------+----------------------+" + reset);
        });
    }

    public void cancelRegisterCourse(Scanner sc) {
        Account acc = Session.currentAccount;
        List<EnrolledCourses> getEnrollmentByStudent = enrollmentService.getEnrolledCoursesByStudent(acc.getId());

        List<EnrolledCourses> filterCourseRegistered = getEnrollmentByStudent.stream()
                .filter(enrolledCourses -> enrolledCourses.getStatus() == Status.WAITING)
                .toList();

        if (filterCourseRegistered.isEmpty()) {
            System.err.println("Không có khóa học nào đăng ký để hủy!");
            return;
        }

        cancelSelectedCourse(sc, filterCourseRegistered);
    }

    private void cancelSelectedCourse(Scanner sc, List<EnrolledCourses> filterCourseRegistered) {
        while (true) {
            printCancelCourseMenu(filterCourseRegistered);
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            if (choice == 0) {
                System.out.println("Đã thoát hủy đăng ký.");
                return;
            }

            if (choice < 1 || choice > filterCourseRegistered.size()) {
                System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                continue;
            }

            EnrolledCourses selectedCourse = filterCourseRegistered.get(choice - 1);
            String courseId = selectedCourse.getCourseId();
            String studentId = Session.currentAccount.getId();

            if (confirmCancellation(sc)) {
                cancelCourseRegistration(studentId, courseId);
            }
        }
    }

    private static void printCancelCourseMenu(List<EnrolledCourses> filterCourseRegistered) {
        System.out.println("\u001B[96m╔══════════════════════════════════════╗");
        System.out.println("║    CHỌN KHÓA HỌC ĐĂNG KÝ MUỐN HỦY    ║");
        System.out.println("╠══════════════════════════════════════╣");

        for (int i = 0; i < filterCourseRegistered.size(); i++) {
            System.out.printf("║ %d. %-33s ║\n", i + 1, filterCourseRegistered.get(i).getCourseName());
        }
        System.out.println("║ 0. Quay về menu học viên             ║");
        System.out.println("╚══════════════════════════════════════╝\u001B[0m");
    }

    private boolean confirmCancellation(Scanner sc) {
        System.out.println("Bạn có chắc chắn muốn hủy khóa học không?");
        String confirm = sc.nextLine();
        return confirm.equalsIgnoreCase("y");
    }

    private void cancelCourseRegistration(String studentId, String courseId) {
        boolean result = enrollmentService.cancelRegistration(studentId, courseId);
        if (result) {
            System.out.println("Đã hủy đăng ký khóa học thành công.");
        } else {
            System.err.println("Hủy đăng ký thất bại hoặc khóa học không tồn tại.");
        }
    }

    public void changePassword(Scanner sc) {
        while (true) {
            Account acc = Session.currentAccount;
            String currentPassword = Validator.validateInputString(sc, "Nhập mật khẩu hiện tại:", new StringRule(0, 255, false, "Mật khẩu không hợp lệ!"));
            String currentEmail = Validator.validateInputRegex(sc, "Nhập email: ", new RegexRule("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$", "Email không hợp lệ"));

            if (!acc.getPassword().equals(currentPassword) || !acc.getEmail().equals(currentEmail)) {
                System.err.println("Email hoặc mật khẩu không đúng. Vui lòng nhập lại");
                continue;
            }

            String newPassword = Validator.validateInputString(sc, "Nhập mật khẩu mới:", new StringRule(0, 255, false, "Mật khẩu không hợp lệ!"));
            boolean result = StudentUI.studentService.changePassword(acc.getId(), newPassword);

            if (result) {
                System.out.println("Cập nhật mât khẩu thành công!");
                break;
            } else {
                System.err.println("Cập nhật mật khẩu thất bại!");
            }
        }
    }
}
