import ra.edu.business.model.Account;
import ra.edu.business.model.Role;
import ra.edu.business.service.admin.AccountService;
import ra.edu.business.service.admin.AccountServiceImp;
import ra.edu.business.service.student.StudentService;
import ra.edu.business.service.student.StudentServiceImp;
import ra.edu.presentation.AdminUI;
import ra.edu.presentation.UserUI;
import ra.edu.validate.Validator;

import java.util.Scanner;

public class Main {
    public static AccountService accountService = new AccountServiceImp();
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

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    loginAccount(sc);
                    break;
                case 2:
                    loginAccount(sc);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn la từ 1 - 3!");
            }
        } while (true);
    }

    public static void loginAccount(Scanner sc) {
        do {
            try {
                Account account = new Account();
                account.inputData(sc);

                Account loggedInAccount = accountService.login(account.getEmail(), account.getPassword());

                if (loggedInAccount != null){
                    if (loggedInAccount.getRole() == Role.ADMIN) {
                        System.out.println("Đăng nhập vào admin thành công!");
                        AdminUI.printMenuAdmin(sc);
                        break;
                    }else if (loggedInAccount.getRole() == Role.STUDENT) {
                        System.out.println("Đăng nhập vào hoc viên thành công!");
                        UserUI.printMenuUser(sc);
                        break;
                    }
                }
                System.err.println("Sai tài khoản hoặc mật khẩu. Vui lòng nhập lại!");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }
}
