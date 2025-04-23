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
    public static EnrollmentService enrollmentService = new EnrollmentServiceImp();

    public static void printMenuUser(Scanner sc) {
        UserUI userUI = new UserUI();
        do {
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

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    userUI.displayCourse(sc);
                    break;
                case 2:
                    userUI.registerCourse(sc);
                    break;
                case 3:
                    userUI.getRegisterCourseByStudent();
                    break;
                case 4:
                    userUI.cancelRegisterCourse(sc);
                    break;
                case 5:
                    userUI.changePassword(sc);
                    break;
                case 6:
                    System.out.println("Bạn có chắc chắn đăng xuất không?");
                    String confirm = sc.nextLine();

                    if (confirm.equals("y")) {
                        return;
                    } else if (confirm.equals("n")) {
                        System.out.println("Hủy đăng xuất");
                    }
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 6!");
            }
        } while (true);
    }

    public void displayCourse(Scanner sc) {
        do {
            final String RESET = "\u001B[0m";
            final String OLIVE = "\u001B[33m";


            System.out.println(OLIVE + "╔══════════════════════════════════════════════════════════╗");
            System.out.println("║  1. Danh sách khóa học có phân trang                     ║");
            System.out.println("║  2. Tìm kiếm khóa học theo tên                           ║");
            System.out.println("║  3. Trở về menu học viên                                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝" + RESET);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    Paginate.paginateCourse(sc, CourseUI.courseService.findAllOfRole("student"), "student");
                    break;
                case 2:
                    System.out.println("Nhập vào tên khoa học cần tìm:");
                    String courseName = sc.nextLine();

                    List<Course> filterCourseName = CourseUI.courseService.searchCourseByName(courseName);

                    if (filterCourseName.isEmpty()){
                        System.err.println("Không có tên khóa học phù hợp!");
                        return;
                    }

                    Paginate.paginateCourse(sc, filterCourseName, "student");
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 3!");
            }
        } while (true);
    }

    public void registerCourse(Scanner sc) {
        System.out.println("Nhập vào số khóa học cần đăng ký:");
        int size = Integer.parseInt(sc.nextLine());

        Account acc = Session.currentAccount;
        System.out.println("Id" + acc.getId());

        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin đăng ký khóa học thứ " + (i + 1));

            Enrollment enrollment = new Enrollment();
            String courseId = SelectCourse.selectCourse(sc);

            if (courseId == null) {
                System.out.println("Đã quay về menu chính.");
                return;
            }

            enrollment.setCourseId(courseId);
            enrollment.setStudentId(acc.getId());

            boolean isExist = enrollmentService.existsEnrollment(acc.getId(), courseId);

            if (isExist) {
                System.err.println("Bạn đã đăng ký khóa học này!");
                continue;
            }

            boolean result = enrollmentService.registerCourse(enrollment);

            if (result) {
                System.out.println("Đăng kí khóa học thành công!");
            }else{
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
            System.out.println(cornflowerBlueText + "+-----------------+--------------------------+-----------------+--------------------------+----------------------+----------------------+" + reset);
        });
    }

    public void cancelRegisterCourse(Scanner sc){
        Account acc = Session.currentAccount;
        List<EnrolledCourses> getEnrollmentByStudent = enrollmentService.getEnrolledCoursesByStudent(acc.getId());

        List<EnrolledCourses> filterCourseRegistered = getEnrollmentByStudent.stream()
                .filter(enrolledCourses -> enrolledCourses.getStatus() == Status.WAITING)
                .toList();

        if (filterCourseRegistered.isEmpty()){
            System.err.println("Không có khóa học nào đăng ký để hủy!");
            return;
        }

        do {
            final String RESET = "\u001B[0m";
            final String AQUAMARINE = "\u001B[96m"; // Cyan nhạt gần giống Aquamarine

            System.out.println(AQUAMARINE + "╔══════════════════════════════════════╗");
            System.out.println("║    CHỌN KHÓA HỌC ĐĂNG KÝ MUỐN HỦY    ║");
            System.out.println("╠══════════════════════════════════════╣");

            for(int i = 0; i < filterCourseRegistered.size(); i++){
                System.out.printf("║ %d. %-33s ║\n", i + 1, filterCourseRegistered.get(i).getCourseName());
            }
            System.out.println("║ 0. Quay về menu học viên             ║");
            System.out.println("╚══════════════════════════════════════╝" + RESET);

            int choice = Validator.validateInputInt(sc, "Lụa chọn của bạn: ");

            if (choice == 0) {
                System.out.println("Đã thoát hủy đăng ký.");
                return;
            }

            if (choice < 1 || choice > filterCourseRegistered.size()) {
                System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                continue;
            }

            // Lấy khóa học được chọn
            EnrolledCourses selectedCourse = filterCourseRegistered.get(choice - 1);
            String courseId = selectedCourse.getCourseId();
            String studentId = acc.getId();

            System.out.println("Bạn có chắc chắn muốn hủy khóa học không?");
            String confirm = sc.nextLine();

            if (confirm.equals("y")){
                boolean result = enrollmentService.cancelRegistration(studentId, courseId);
                if (result) {
                    System.out.println("Đã hủy đăng ký khóa học thành công.");
                } else {
                    System.err.println("Hủy đăng ký thất bại hoặc khóa học không tồn tại.");
                }
            }else if (confirm.equals("n")){
                System.out.println("Hủy xác nhận!");
            }
        }while (true);
    }

    public void changePassword(Scanner sc){
        do {
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
            }else{
                System.err.println("Cập nhật mật khẩu thất bại!");
            }

            break;
        }while (true);
    }
}
