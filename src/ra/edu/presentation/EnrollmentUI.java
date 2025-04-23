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

    public static void printMenuEnrollment(Scanner sc) {
        EnrollmentUI enrollmentUI = new EnrollmentUI();
        String RESET = "\u001B[0m";
        String CYAN = "\u001B[36m";
        do {
            System.out.println(CYAN + "╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║                 ==== QUẢN LÍ ĐĂNG KÝ KHÓA HỌC ====              ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Hiển thị học viên theo từng khóa học                        ║");
            System.out.println("║  2. Duyệt sinh viên đăng ký khóa học                            ║");
            System.out.println("║  3. Xóa học viên khỏi khóa học                                  ║");
            System.out.println("║  4. Quay về menu chính                                          ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝" + RESET);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    enrollmentUI.getRegisterStudentByCourse(sc);
                    break;
                case 2:
                    enrollmentUI.approvalStudent(sc);
                    break;
                case 3:
                    enrollmentUI.deleteStudentOfCourse(sc);
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        } while (true);
    }

    public void getRegisterStudentByCourse(Scanner sc) {
        String courseId = SelectCourse.selectCourse(sc);

        if (courseId == null) {
            System.out.println("Đã quay về menu chính.");
            return;
        }

        List<CourseRegistrationInfo> courseRegistrationInfoList = enrollmentService.findCourseRegistrationByStudent(courseId);

        List<CourseRegistrationInfo> filterStudentByCourse = courseRegistrationInfoList.stream()
                .filter(courseRegistrationInfo -> courseRegistrationInfo.getStatus() == Status.CONFIRMED)
                .toList();

        if (filterStudentByCourse.isEmpty()) {
            System.err.println("Khóa học chưa có sinh viên!");
            return;
        }

        Paginate.paginationEnrollment(sc, filterStudentByCourse);
    }

    public void approvalStudent(Scanner sc) {
        do {
            String courseId = SelectCourse.selectCourse(sc);

            if (courseId == null) {
                System.out.println("Đã quay về menu chính.");
                return;
            }

            String studentId = SelectCourse.selectStudent(sc, courseId);

            if ("no data".equals(studentId)) {
                System.err.println("Không có sinh viên nào đăng ký!");
                continue;
            }

            if (studentId == null) {
                System.out.println("Đã trở về menu khóa học!");
                continue;
            }

            SelectCourse.selectApprovalOrDeny(sc, studentId, courseId);
        } while (true);
    }

    public void deleteStudentOfCourse(Scanner sc) {
        do {
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

            System.out.println("Bạn có chắc chắn muốn xóa sinh viên đăng ký khỏi khóa học này không?");
            String confirm = sc.nextLine();

            if (confirm.equals("y")) {
                boolean result = enrollmentService.removeStudentOfEnrollment(studentId, courseId);
                if (result) {
                    System.out.println("Xóa thành công!");
                } else {
                    System.err.println("Xóa thất bại!");
                }
            } else if (confirm.equals("n")) {
                System.out.println("Hủy xác nhân xóa!");
            }
            break;
        } while (true);
    }
}
