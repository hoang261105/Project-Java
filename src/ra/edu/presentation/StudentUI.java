package ra.edu.presentation;

import ra.edu.business.model.EnrolledCourses;
import ra.edu.business.model.Student;
import ra.edu.business.service.student.StudentService;
import ra.edu.business.service.student.StudentServiceImp;
import ra.edu.utils.Paginate;
import ra.edu.validate.*;

import java.util.List;
import java.util.Scanner;

public class StudentUI {
    public static StudentService studentService = new StudentServiceImp();
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public static void printMenuStudent(Scanner sc) {
        StudentUI studentUI = new StudentUI();
        while (true) {
            printStudentMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn: ");
            switch (choice) {
                case 1 -> Paginate.paginateStudents(sc, studentService.findAll());
                case 2 -> studentUI.addStudent(sc);
                case 3 -> studentUI.updateStudent(sc);
                case 4 -> studentUI.deleteStudent(sc);
                case 5 -> studentUI.searchStudent(sc);
                case 6 -> studentUI.sortStudent(sc);
                case 7 -> {
                    return;
                }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 7!");
            }
        }
    }

    private static void printStudentMenu() {
        System.out.println(YELLOW + "╔═══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(YELLOW + "║                 ==== QUẢN LÍ HỌC VIÊN ====                ║" + RESET);
        System.out.println(YELLOW + "╠═══════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(YELLOW + "║  1. Xem danh sách học viên                                ║" + RESET);
        System.out.println(YELLOW + "║  2. Thêm mới học viên                                     ║" + RESET);
        System.out.println(YELLOW + "║  3. Cập nhật học viên                                     ║" + RESET);
        System.out.println(YELLOW + "║  4. Xóa học viên                                          ║" + RESET);
        System.out.println(YELLOW + "║  5. Tìm kiếm học viên                                     ║" + RESET);
        System.out.println(YELLOW + "║  6. Sắp xếp học viên                                      ║" + RESET);
        System.out.println(YELLOW + "║  7. Quay về menu chính                                    ║" + RESET);
        System.out.println(YELLOW + "╚═══════════════════════════════════════════════════════════╝" + RESET);
    }

    public void addStudent(Scanner sc) {
        int size = Validator.validateInputInt(sc, "Nhập vào số sinh viên cần thêm: ");
        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin sinh viên thứ " + (i + 1));
            Student student = new Student();
            student.inputData(sc);
            boolean result = studentService.add(student);
            boolean isCreate = studentService.createAccount(student);
            System.out.println((result && isCreate) ? "Thêm sinh viên thành công!" : "Thêm sinh viên thất bại!");
        }
    }

    public void updateStudent(Scanner sc) {
        String studentId = Validator.validateInputString(sc, "Nhập vào id sinh viên cần cập nhật: ", new StringRule(1, 20, false, "ID không hợp lệ"));
        Student findStudent = studentService.findStudentById(studentId);
        if (findStudent == null) {
            System.err.println("Không tìm thấy sinh viên!");
            return;
        }

        while (true) {
            printUpdateMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn: ");
            boolean result;
            switch (choice) {
                case 1 -> {
                    findStudent.setStudentName(Validator.validateInputString(sc, "Nhập tên học viên mới: ", new StringRule(0, 100, false, "Tên không hợp lệ")));
                    result = studentService.update(findStudent, 1);
                    printUpdateResult(result, "tên sinh viên");
                }
                case 2 -> {
                    findStudent.setDateOfBirth(Validator.validateInputLocalDate(sc, "Nhập ngày sinh mới:"));
                    result = studentService.update(findStudent, 2);
                    printUpdateResult(result, "ngày sinh");
                }
                case 3 -> {
                    String email = Validator.validateInputRegex(sc, "Nhập email mới: ", new RegexRule("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$", "Email không hợp lệ"));
                    findStudent.setEmail(ExistValidator.validateExist(sc, email, new ExistRule("email", "Email đã tồn tại")));
                    result = studentService.update(findStudent, 3);
                    printUpdateResult(result, "email");
                }
                case 4 -> {
                    findStudent.setSex(Validator.validateInputBoolean(sc, "Nhập giới tính mới:"));
                    result = studentService.update(findStudent, 4);
                    printUpdateResult(result, "giới tính");
                }
                case 5 -> {
                    String phone = Validator.validateInputRegex(sc, "Nhập số điện thoại mới: ", new RegexRule("^(0(3|5|7|8|9)\\d{8}|\\+84(3|5|7|8|9)\\d{8})$", "Số điện thoại không hợp lệ"));
                    findStudent.setPhoneNumber(ExistValidator.validateExist(sc, phone, new ExistRule("phone", "Số điện thoại đã tồn tại")));
                    result = studentService.update(findStudent, 5);
                    printUpdateResult(result, "số điện thoại");
                }
                case 6 -> {
                    findStudent.setPassword(Validator.validateInputString(sc, "Nhập mật khẩu mới: ", new StringRule(0, 255, false, "Mật khẩu không hợp lệ")));
                    result = studentService.update(findStudent, 6);
                    printUpdateResult(result, "mật khẩu");
                }
                case 7 -> {
                    return;
                }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 7!");
            }
        }
    }

    private void printUpdateMenu() {
        System.out.println("\u001B[34m╔══════════════════════════════════════════════════════════════════════════════╗\u001B[0m");
        System.out.println("\u001B[36m║                    ======= CẬP NHẬT THÔNG TIN HỌC VIÊN =======               ║\u001B[0m");
        System.out.println("\u001B[34m╠══════════════════════════════════════════════════════════════════════════════╣\u001B[0m");
        System.out.println("\u001B[32m║  1. Cập nhật tên sinh viên                                                   ║\u001B[0m");
        System.out.println("\u001B[33m║  2. Cập nhật ngày sinh                                                       ║\u001B[0m");
        System.out.println("\u001B[35m║  3. Cập nhật email                                                           ║\u001B[0m");
        System.out.println("\u001B[36m║  4. Cập nhật giới tính                                                       ║\u001B[0m");
        System.out.println("\u001B[32m║  5. Cập nhật số điện thoại                                                   ║\u001B[0m");
        System.out.println("\u001B[33m║  6. Cập nhật mật khẩu                                                        ║\u001B[0m");
        System.out.println("\u001B[35m║  7. Trở về menu chính                                                        ║\u001B[0m");
        System.out.println("\u001B[34m╚══════════════════════════════════════════════════════════════════════════════╝\u001B[0m");
    }

    private void printUpdateResult(boolean result, String fieldName) {
        System.out.println(result ? "Cập nhật " + fieldName + " thành công!" : "Cập nhật " + fieldName + " thất bại!");
    }

    public void deleteStudent(Scanner sc) {
        String studentId = Validator.validateInputString(sc, "Nhập vào id sinh viên cần xoá: ", new StringRule(1, 20, false, "ID không hợp lệ"));
        if (studentService.findStudentById(studentId) == null) {
            System.err.println("Không tìm thấy sinh viên!");
            return;
        }

        List<EnrolledCourses> enrolledCourses = EnrollmentUI.enrollmentService.getEnrolledCoursesByStudent(studentId);
        if (!enrolledCourses.isEmpty()) {
            System.err.println("Sinh viên đang đăng ký khóa học, không thể xoá!");
            return;
        }

        System.out.println("Bạn có chắc chắn muốn xóa không? (y/n): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            Student student = new Student();
            student.setStudentId(studentId);
            boolean result = studentService.delete(student);
            System.out.println(result ? "Xóa sinh viên thành công!" : "Xóa sinh viên thất bại!");
        } else {
            System.out.println("Hủy xác nhận xóa!");
        }
    }

    public void searchStudent(Scanner sc) {
        final String[] searchLabels = {"ID", "tên", "email"};

        do {
            System.out.println("\u001B[33m╔═════════════════════════════════════════════════════════════╗\u001B[0m");
            System.out.println("\u001B[33m║                     ==== MENU TÌM KIẾM ====                 ║\u001B[0m");
            System.out.println("\u001B[33m╠═════════════════════════════════════════════════════════════╣\u001B[0m");
            for (int i = 0; i < searchLabels.length; i++) {
                System.out.printf("\u001B[33m║  %d. Tìm kiếm theo %-41s ║\u001B[0m\n", i + 1, searchLabels[i]);
            }
            System.out.println("\u001B[33m║  4. Trở về menu quản lí                                     ║\u001B[0m");
            System.out.println("\u001B[33m╚═════════════════════════════════════════════════════════════╝\u001B[0m");

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn: ");

            if (choice >= 1 && choice <= 3) {
                System.out.printf("Nhập vào %s cần tìm:\n", searchLabels[choice - 1]);
                String keyword = sc.nextLine();
                List<Student> filteredStudents = studentService.searchStudent(keyword, choice);

                if (filteredStudents.isEmpty()) {
                    System.err.printf("Không có kết quả theo %s phù hợp!\n", searchLabels[choice - 1]);
                } else {
                    Paginate.paginateStudents(sc, filteredStudents);
                }
            } else if (choice == 4) {
                return;
            } else {
                System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        } while (true);
    }

    public void sortStudent(Scanner sc) {
        String goldText = "\u001B[38;5;220m";
        String reset = "\u001B[0m";

        final String[] sortOptions = {
                "Tên giảm dần",
                "ID tăng dần",
                "ID giảm dần"
        };

        do {
            System.out.println(goldText + "╔════════════════════════════════════════════════════════════════╗" + reset);
            System.out.println(goldText + "║               ============ SẮP XẾP THEO ============           ║" + reset);
            for (int i = 0; i < sortOptions.length; i++) {
                System.out.printf(goldText + "║  %d. %-59s║" + reset + "\n", i + 1, sortOptions[i]);
            }
            System.out.println(goldText + "║  4. Trở về menu chính                                          ║" + reset);
            System.out.println(goldText + "╚════════════════════════════════════════════════════════════════╝" + reset);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn: ");

            if (choice >= 1 && choice <= 3) {
                List<Student> sortedList = studentService.sortStudent(choice);
                Paginate.paginateStudents(sc, sortedList);
            } else if (choice == 4) {
                return;
            } else {
                System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        } while (true);
    }
}
