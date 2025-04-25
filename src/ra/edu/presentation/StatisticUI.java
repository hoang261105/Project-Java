package ra.edu.presentation;

import ra.edu.business.service.statistic.StatisticService;
import ra.edu.business.service.statistic.StatisticServiceImp;

import java.util.Map;
import java.util.Scanner;

public class StatisticUI {
    public static StatisticService statisticService = new StatisticServiceImp();

    public static void printMenuStatistic(Scanner sc) {
        final String BLUE = "\u001B[34m";
        final String RESET = "\u001B[0m";

        while (true) {
            System.out.println(BLUE + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(BLUE + "║                         ==== MENU THỐNG KÊ ====                      ║" + RESET);
            System.out.println(BLUE + "╠══════════════════════════════════════════════════════════════════════╣" + RESET);
            System.out.println(BLUE + "║  1. Thống kê tổng số lượng khóa học và tổng số học viên              ║" + RESET);
            System.out.println(BLUE + "║  2. Thống kê tổng số học viên theo từng khóa                         ║" + RESET);
            System.out.println(BLUE + "║  3. Thống kê top 5 khóa học đông sinh viên nhất                      ║" + RESET);
            System.out.println(BLUE + "║  4. Liệt kê các khóa học có trên 10 học viên                         ║" + RESET);
            System.out.println(BLUE + "║  5. Quay về menu chính                                               ║" + RESET);
            System.out.println(BLUE + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Lựa chọn của bạn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> showStatistic(
                            statisticService.getTotalCourseAndStudent(),
                            "THỐNG KÊ KHÓA HỌC VÀ SINH VIÊN ",
                            "Tổng số khóa học");
                    case 2 -> showStatistic(
                            statisticService.getTotalStudentOfCourse(),
                            "THỐNG KÊ SINH VIÊN THEO KHÓA HỌC ",
                            "Tên khóa học");
                    case 3 -> showStatistic(
                            statisticService.getTop5CourseMostStudent(),
                            "THỐNG KÊ TOP 5 KHÓA HỌC ĐÔNG SINH VIÊN ",
                            "Tên khóa học");
                    case 4 -> showStatistic(
                            statisticService.getCourseMoreThan10Students(),
                            "THỐNG KÊ KHÓA HỌC CÓ HƠN 10 SINH VIÊN ",
                            "Tên khóa học");
                    case 5 -> { return; }
                    default -> System.err.println("Vui lòng chọn lại từ 1 - 5!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    private static <K> void showStatistic(Map<K, Integer> data, String title, String column1) {
        if (data == null || data.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        int col1Width = 34;
        int col2Width = 16;
        int totalWidth = col1Width + col2Width; // 5 ký tự cho đường viền và khoảng trắng giữa các cột

        // Viền trên
        System.out.println("╔" + "═".repeat(totalWidth) + "╗");

        // Tiêu đề căn giữa
        int padding = totalWidth - title.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        System.out.println("║" + " ".repeat(leftPadding) + title + " ".repeat(rightPadding) + "║");

        // Viền tiêu đề -> cột
        System.out.println("╠" + "═".repeat(col1Width - 1) + "╦" + "═".repeat(col2Width) + "╣");

        // Hàng tiêu đề cột
        System.out.printf("║ %-"+(col1Width - 3)+"s ║ %-"+(col2Width - 2)+"s ║\n", column1, "Tổng sinh viên");

        // Viền giữa
        System.out.println("╠" + "═".repeat(col1Width - 1) + "╬" + "═".repeat(col2Width) + "╣");

        // Dữ liệu
        data.forEach((key, value) ->
                System.out.printf("║ %-"+(col1Width - 3)+"s ║ %-"+(col2Width - 2)+"d ║\n", key.toString(), value)
        );

        // Viền dưới
        System.out.println("╚" + "═".repeat(col1Width - 1) + "╩" + "═".repeat(col2Width) + "╝");
    }
}
