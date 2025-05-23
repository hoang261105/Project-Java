package ra.edu.presentation;

import ra.edu.validate.Validator;

import java.util.Scanner;

public class AdminUI {
    public static void printMenuAdmin(Scanner sc) {
        String RESET = "\u001B[0m";
        String ORANGE = "\u001B[38;5;208m";
        do {
            System.out.println(ORANGE + "╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║                   ==== MENU QUẢN TRỊ VIÊN ====                  ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Quản lí khóa học                                            ║");
            System.out.println("║  2. Quản lí học viên                                            ║");
            System.out.println("║  3. Quản lí đăng kí khóa học                                    ║");
            System.out.println("║  4. Thống kê học viên theo từng khóa học                        ║");
            System.out.println("║  5. Đăng xuất                                                   ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝" + RESET);

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    CourseUI.printMenuCourse(sc);
                    break;
                case 2:
                    StudentUI.printMenuStudent(sc);
                    break;
                case 3:
                    EnrollmentUI.printMenuEnrollment(sc);
                    break;
                case 4:
                    StatisticUI.printMenuStatistic(sc);
                    break;
                case 5:
                    System.out.println("Bạn có chắc chắn đăng xuất không?");
                    String confirm = sc.nextLine();

                    if(confirm.equals("y")){
                        return;
                    } else if (confirm.equals("n")) {
                        System.out.println("Hủy đăng xuất");
                    }
                    break;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 5!");
            }
        } while (true);
    }
}
