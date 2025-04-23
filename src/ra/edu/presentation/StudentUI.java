package ra.edu.presentation;

import ra.edu.business.model.EnrolledCourses;
import ra.edu.business.model.Enrollment;
import ra.edu.business.model.Student;
import ra.edu.business.service.student.StudentService;
import ra.edu.business.service.student.StudentServiceImp;
import ra.edu.utils.Paginate;
import ra.edu.validate.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentUI {
    public static StudentService studentService = new StudentServiceImp();

    public static void printMenuStudent(Scanner sc) {
        String YELLOW = "\u001B[33m";
        String RESET = "\u001B[0m";
        StudentUI studentUI = new StudentUI();
        do {
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

            int choice = Validator.validateInputInt(sc, "Lựa chon của bạn: ");

            switch (choice) {
                case 1:
                    Paginate.paginateStudents(sc, studentService.findAll());
                    break;
                case 2:
                    studentUI.addStudent(sc);
                    break;
                case 3:
                    studentUI.updateStudent(sc);
                    break;
                case 4:
                    studentUI.deleteStudent(sc);
                    break;
                case 5:
                    studentUI.searchStudent(sc);
                    break;
                case 6:
                    studentUI.sortStudent(sc);
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 8!");
            }
        } while (true);
    }

    public void addStudent(Scanner sc) {
        System.out.println("Nhập vào số sinh viên cần thêm:");
        int size = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin sinh viên thứ " + (i + 1));
            Student student = new Student();
            student.inputData(sc);
            boolean result = studentService.add(student);
            boolean isCreate = studentService.createAccount(student);

            if (result && isCreate) {
                System.out.println("Thêm sinh viên thành công!");
            } else {
                System.err.println("Thêm sinh viên thất bại!");
            }
        }
    }

    public void updateStudent(Scanner sc) {
        System.out.println("Nhập vào id sinh viên cần cập nhật");
        String studentId = sc.nextLine();

        Student findStudent = studentService.findStudentById(studentId);

        if (findStudent != null) {
            do {
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

                int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

                switch (choice) {
                    case 1:
                        String studentName = Validator.validateInputString(sc, "Nhập tên học viên mới: ", new StringRule(0, 100, false, "Tên không hợp lệ"));
                        findStudent.setStudentName(studentName);
                        boolean result1 = studentService.update(findStudent, 1);

                        if (result1) {
                            System.out.println("Cập nhật tên thành công!");
                        } else {
                            System.err.println("Cập nhật tên thất bại!");
                        }
                        break;
                    case 2:
                        LocalDate newDob = Validator.validateInputLocalDate(sc, "Nhập ngày sinh mới:");
                        findStudent.setDateOfBirth(newDob);

                        boolean result2 = studentService.update(findStudent, 2);

                        if (result2) {
                            System.out.println("Cập nhật ngày sinh thành công!");
                        } else {
                            System.err.println("Cập nhật ngày sinh thất bại!");
                        }
                        break;
                    case 3:
                        String email = Validator.validateInputRegex(sc, "Nhập email mới: ", new RegexRule("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$", "Email không hợp lệ"));
                        findStudent.setEmail(ExistValidator.validateExist(sc, email, new ExistRule("email", "Email đã tồn tại")));

                        boolean result3 = studentService.update(findStudent, 3);

                        if (result3) {
                            System.out.println("Cập nhật email thành công!");
                        } else {
                            System.err.println("Cập nhật email thất bại!");
                        }
                        break;
                    case 4:
                        boolean newSex = Validator.validateInputBoolean(sc, "Nhập giới tính mới:");
                        findStudent.setSex(newSex);

                        boolean result4 = studentService.update(findStudent, 4);

                        if (result4) {
                            System.out.println("Cập nhật giơi tính thành công!");
                        } else {
                            System.err.println("Cập nhật giới tính thất bại!");
                        }
                        break;
                    case 5:
                        String phoneNumber = Validator.validateInputRegex(sc, "Nhập số điện thoại mới: ", new RegexRule("^(0(3|5|7|8|9)\\d{8}|\\+84(3|5|7|8|9)\\d{8})$", "Số điêện thoại không hợp lệ"));
                        findStudent.setPhoneNumber(ExistValidator.validateExist(sc, phoneNumber, new ExistRule("phone", "Số điện thoại đã tồn tại")));

                        boolean result5 = studentService.update(findStudent, 5);

                        if (result5) {
                            System.out.println("Câp nhật số điện thoại thành công!");
                        } else {
                            System.err.println("Câp nhật số điện thoại thất bại");
                        }
                        break;
                    case 6:
                        String newPassword = Validator.validateInputString(sc, "Nhập mật khẩu mới: ", new StringRule(0, 255, false, "Mật khẩu không hop lệ"));
                        findStudent.setPassword(newPassword);

                        boolean result6 = studentService.update(findStudent, 6);

                        if (result6) {
                            System.out.println("Cập nhât mật khẩu thành công!");
                        } else {
                            System.err.println("Câp nhật mật khẩu");
                        }
                        break;
                    case 7:
                        return;
                    default:
                        System.err.println("Vui lòng chọn lại từ 1 - 7!");
                }
            } while (true);
        } else {
            System.err.println("Không tìm thấy sinh viên!");
        }
    }

    public void deleteStudent(Scanner sc) {
        System.out.println("Nhập vào id sinh viên cần xoá: ");
        String studentId = sc.nextLine();

        if (studentService.findStudentById(studentId) != null) {
            List<EnrolledCourses> enrolledCourses = EnrollmentUI.enrollmentService.getEnrolledCoursesByStudent(studentId);
            if (!enrolledCourses.isEmpty()) {
                System.err.println("Sinh viên đang đăng ký khóa học, không thể xoá!");
                return;
            }

            System.out.println("Bạn có chắc chắn muốn xóa không?");
            String confirm = sc.nextLine();

            if (confirm.equals("y")) {
                Student student = new Student();
                student.setStudentId(studentId);
                boolean result = studentService.delete(student);

                if (result) {
                    System.out.println("Xóa sinh viên thành công!");
                } else {
                    System.err.println("Xóa sinh viên thất bại!");
                }
            } else if (confirm.equals("n")) {
                System.out.println("Hủy xác nhận xóa!");
            }
        } else {
            System.err.println("Không tìm thấy sinh viên!");
        }
    }

    public void searchStudent(Scanner sc) {
        do {
            System.out.println("\u001B[33m╔═════════════════════════════════════════════════════════════╗\u001B[0m");
            System.out.println("\u001B[33m║                     ==== MENU TÌM KIẾM ====                 ║\u001B[0m");
            System.out.println("\u001B[33m╠═════════════════════════════════════════════════════════════╣\u001B[0m");
            System.out.println("\u001B[33m║  1. Tìm kiếm theo ID                                        ║\u001B[0m");
            System.out.println("\u001B[33m║  2. Tìm kiếm theo tên                                       ║\u001B[0m");
            System.out.println("\u001B[33m║  3. Tìm kiếm theo email                                     ║\u001B[0m");
            System.out.println("\u001B[33m║  4. Trở về menu quản lí                                     ║\u001B[0m");
            System.out.println("\u001B[33m╚═════════════════════════════════════════════════════════════╝\u001B[0m");

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn: ");

            switch (choice) {
                case 1:
                    System.out.println("Nhập vào id cần tìm:");
                    String studentId = sc.nextLine();

                    List<Student> filterStudentId = studentService.searchStudent(studentId, 1);
                    if (filterStudentId.isEmpty()) {
                        System.err.println("Không có kết quả phù hợp!");
                        return;
                    }

                    Paginate.paginateStudents(sc, filterStudentId);
                    break;
                case 2:
                    System.out.println("Nhập vào tên cần tìm:");
                    String studentName = sc.nextLine();

                    List<Student> filterStudentName = studentService.searchStudent(studentName, 2);

                    if (filterStudentName.isEmpty()) {
                        System.err.println("Không có kết quả theo tên phù hợp!");
                        return;
                    }

                    Paginate.paginateStudents(sc, filterStudentName);
                    break;
                case 3:
                    System.out.println("Nhập vào email cần tìm:");
                    String studentEmail = sc.nextLine();

                    List<Student> filterStudentEmail = studentService.searchStudent(studentEmail, 3);

                    if (filterStudentEmail.isEmpty()) {
                        System.err.println("Không có kết quả theo email phù hợp!");
                        return;
                    }

                    Paginate.paginateStudents(sc, filterStudentEmail);
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        } while (true);
    }

    public void sortStudent(Scanner sc) {
        do {
            String goldText = "\u001B[38;5;220m";  // Màu chữ vàng gold
            String reset = "\u001B[0m";

            System.out.println(goldText + "╔════════════════════════════════════════════════════════════════╗" + reset);
            System.out.println(goldText + "║               ============ SẮP XẾP THEO ============           ║" + reset);
            System.out.println(goldText + "║  1. Tên giảm dần                                               ║" + reset);
            System.out.println(goldText + "║  2. Id tăng dần                                                ║" + reset);
            System.out.println(goldText + "║  3. Id giảm dần                                                ║" + reset);
            System.out.println(goldText + "║  4. Trở về menu chính                                          ║" + reset);
            System.out.println(goldText + "╚════════════════════════════════════════════════════════════════╝" + reset);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn: ");

            switch (choice) {
                case 1:
                    List<Student> sortStudentNameDesc = studentService.sortStudent(1);
                    Paginate.paginateStudents(sc, sortStudentNameDesc);
                    break;
                case 2:
                    List<Student> sortStudentId = studentService.sortStudent(2);
                    Paginate.paginateStudents(sc, sortStudentId);
                    break;
                case 3:
                    List<Student> sortStudentIdDesc = studentService.sortStudent(3);
                    Paginate.paginateStudents(sc, sortStudentIdDesc);
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Vui long chọn lại từ 1 - 4!");
            }
        } while (true);
    }
}
