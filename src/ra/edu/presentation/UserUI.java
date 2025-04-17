package ra.edu.presentation;

import java.util.Scanner;

public class UserUI {
    public static void printMenuUser(Scanner sc) {
        do {
            System.out.println("===================MENU HỌC VIÊN=================");
            System.out.println("1. Xem danh sách khóa học");
            System.out.println("2. Đăng ký khóa học");
            System.out.println("3. Xem khóa học đã đăng ký");
            System.out.println("4. Hủy đăng ký khóa học");
            System.out.println("5. Đôỉ mật khẩu");
            System.out.println("6. Đăng xuất");
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
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Bạn có chắc chắn đăng xuất không?");
                    String confirm = sc.nextLine();

                    if(confirm.equals("y")){
                        return;
                    } else if (confirm.equals("n")) {
                        System.out.println("Hủy đăng xuất");
                    }
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 6!");
            }
        } while (true);
    }
}
