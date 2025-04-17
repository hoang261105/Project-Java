package ra.edu.presentation;

import java.util.Scanner;

public class StatisticUI {
    public static void printMenuStatistic(Scanner sc) {
        do {
            System.out.println("+===============================================================+");
            System.out.println("|                        MENU THỐNG KÊ                          |");
            System.out.println("+===============================================================+");
            System.out.println("|  1. Thống kê tổng số lượng khóa học và tổng số học viên       |");
            System.out.println("|  2. Thống kê tổng số học viên theo từng khóa                  |");
            System.out.println("|  3. Thống kê top 5 khóa học đông sinh viên nhất               |");
            System.out.println("|  4. Liệt kê các khóa học có trên 10 học viên                  |");
            System.out.println("|  5. Quay về menu chính                                        |");
            System.out.println("+===============================================================+");
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
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 5!");
            }
        } while (true);
    }
}
