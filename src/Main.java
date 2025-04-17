import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;
import ra.edu.business.service.admin.AdminService;
import ra.edu.business.service.admin.AdminServiceImp;
import ra.edu.business.service.student.StudentService;
import ra.edu.business.service.student.StudentServiceImp;
import ra.edu.presentation.AdminUI;
import ra.edu.presentation.UserUI;

import java.util.Scanner;

public class Main {
    public static AdminService adminService = new AdminServiceImp();
    public static StudentService studentService = new StudentServiceImp();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("+===========================================================+");
            System.out.println("|             HỆ THỐNG QUẢN LÝ ĐÀO TẠO                      |");
            System.out.println("+===========================================================+");
            System.out.println("|  1. Đăng nhập với tư cách là quản trị viên                |");
            System.out.println("|  2. Đăng nhập với tư cách là học viên                     |");
            System.out.println("|  3. Thoát                                                 |");
            System.out.println("+===========================================================+");
            System.out.printf("Lựa chọn của bạn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    loginAdmin(sc);
                    break;
                case 2:
                    loginUser(sc);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn la từ 1 - 3!");
            }
        } while (true);
    }

    public static void loginAdmin(Scanner sc) {
        do {
            try {
                Admin admin = new Admin();

                admin.inputData(sc);

                if(adminService.login(admin.getUserName(), admin.getPassword())) {
                    System.out.println("Đăng nhập thành công! Xin chào " + admin.getUserName());
                    AdminUI.printMenuAdmin(sc);
                    break;
                }

                System.err.println("Sai tài khoản hoặc mật khẩu. Vui lòng nhập lại!");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }

    public static void loginUser(Scanner sc) {
        do {
            try {
                System.out.println("Nhập email:");
                String email = sc.nextLine();
                System.out.println("Nhập mật khẩu: ");
                String password = sc.nextLine();

                if (studentService.checkLoginUser(email, password)) {
                    System.out.println("Đăng nhập thành công!");
                    UserUI.printMenuUser(sc);
                    break;
                }

                System.err.println("Email hoặc mật khâu không đúng. Vui lòng nhập lại");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }
}
