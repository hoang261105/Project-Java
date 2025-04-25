package ra.edu.presentation;

import ra.edu.business.model.CourseRegistrationInfo;
import ra.edu.business.model.Status;
import ra.edu.business.service.enrollment.EnrollmentService;
import ra.edu.business.service.enrollment.EnrollmentServiceImp;
import ra.edu.utils.Paginate;
import ra.edu.utils.SelectCourse;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class EnrollmentUI {
    public static EnrollmentService enrollmentService = new EnrollmentServiceImp();
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";

    public static void printMenuEnrollment(Scanner sc) {
        EnrollmentUI ui = new EnrollmentUI();
        while (true) {
            printMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");
            switch (choice) {
                case 1 -> ui.displayStudentsByCourse(sc);
                case 2 -> ui.handleStudentApproval(sc);
                case 3 -> ui.handleStudentDeletion(sc);
                case 4 -> { return; }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        }
    }

    private static void printMenu() {
        System.out.println(CYAN + "╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ==== QUẢN LÍ ĐĂNG KÝ KHÓA HỌC ====              ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Hiển thị học viên theo từng khóa học                        ║");
        System.out.println("║  2. Duyệt sinh viên đăng ký khóa học                            ║");
        System.out.println("║  3. Xóa học viên khỏi khóa học                                  ║");
        System.out.println("║  4. Quay về menu chính                                          ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝" + RESET);
    }

    private void displayStudentsByCourse(Scanner sc) {
        String courseId = SelectCourse.selectCourse(sc);
        if (courseId == null) {
            System.out.println("Đã quay về menu chính.");
            return;
        }

        List<CourseRegistrationInfo> confirmedStudents = enrollmentService
                .findCourseRegistrationByStudent(courseId).stream()
                .filter(info -> info.getStatus() == Status.CONFIRMED)
                .toList();

        if (confirmedStudents.isEmpty()) {
            System.err.println("Khóa học chưa có sinh viên!");
        } else {
            Paginate.paginationEnrollment(sc, confirmedStudents);
        }
    }

    private void handleStudentApproval(Scanner sc) {
        while (true) {
            String courseId = SelectCourse.selectCourse(sc);
            if (courseId == null) {
                System.out.println("Đã quay về menu chính.");
                return;
            }

            String studentId = SelectCourse.selectStudent(sc, courseId);
            if ("no data".equals(studentId)) {
                System.err.println("Không có sinh viên nào đăng ký!");
            } else if (studentId == null) {
                System.out.println("Đã trở về menu khóa học!");
            } else {
                SelectCourse.selectApprovalOrDeny(sc, studentId, courseId);
            }
        }
    }

    private void handleStudentDeletion(Scanner sc) {
        while (true) {
            String courseId = SelectCourse.selectCourse(sc);
            if (courseId == null) {
                System.out.println("Đã quay về menu chính.");
                return;
            }

            String studentId = SelectCourse.selectStudentToDelete(sc, courseId);
            if ("no data".equals(studentId)) {
                System.err.println("Không có dữ liệu!");
                continue;
            }

            if (studentId == null) {
                System.out.println("Đã trở về menu khóa học!");
                continue;
            }

            System.out.println("Bạn có chắc chắn muốn xóa sinh viên đăng ký khỏi khóa học này không? (y/n)");
            String confirm = sc.nextLine().trim().toLowerCase();

            switch (confirm) {
                case "y" -> {
                    boolean result = enrollmentService.removeStudentOfEnrollment(studentId, courseId);
                    System.out.println(result ? "Xóa thành công!" : "Xóa thất bại!");
                }
                case "n" -> System.out.println("Hủy xác nhận xóa!");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
            break;
        }
    }
}
