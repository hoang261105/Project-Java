package ra.edu.presentation;

import java.util.Scanner;

public class EnrollmentUI {
    public static void printMenuEnrollment(Scanner sc) {
        do {
            System.out.println("+===========================================================+");
            System.out.println("|              QUẢN LÍ ĐĂNG KÝ KHÓA HỌC                     |");
            System.out.println("+===========================================================+");
            System.out.println("|  1. Hiển thị học viên theo từng khóa học                  |");
            System.out.println("|  2. Thêm học viên vào khóa học                            |");
            System.out.println("|  3. Xóa học viên khỏi khóa học                            |");
            System.out.println("|  4. Quay về menu chính                                    |");
            System.out.println("+===========================================================+");
            System.out.printf("Lựa chọn của bạn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        } while (true);
    }
}
