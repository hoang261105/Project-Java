package ra.edu.presentation;

import ra.edu.business.service.statistic.StatisticService;
import ra.edu.business.service.statistic.StatisticServiceImp;

import java.util.Map;
import java.util.Scanner;

public class StatisticUI {
    public static StatisticService statisticService = new StatisticServiceImp();
    public static void printMenuStatistic(Scanner sc) {
        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";
        do {
            System.out.println(BLUE + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(BLUE + "║                         ==== MENU THỐNG KÊ ====                      ║" + RESET);
            System.out.println(BLUE + "╠══════════════════════════════════════════════════════════════════════╣" + RESET);
            System.out.println(BLUE + "║  1. Thống kê tổng số lượng khóa học và tổng số học viên              ║" + RESET);
            System.out.println(BLUE + "║  2. Thống kê tổng số học viên theo từng khóa                         ║" + RESET);
            System.out.println(BLUE + "║  3. Thống kê top 5 khóa học đông sinh viên nhất                      ║" + RESET);
            System.out.println(BLUE + "║  4. Liệt kê các khóa học có trên 10 học viên                         ║" + RESET);
            System.out.println(BLUE + "║  5. Quay về menu chính                                               ║" + RESET);
            System.out.println(BLUE + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.printf("Lựa chọn của bạn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    getTotalCourseAndStudent();
                    break;
                case 2:
                    getTotalStudentOfCourse();
                    break;
                case 3:
                    getTop5CourseMostStudent();
                    break;
                case 4:
                    getCourseMoreThan10Students();
                    break;
                case 5:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 5!");
            }
        } while (true);
    }

    public static void getTotalCourseAndStudent() {
        Map<Integer, Integer> totalCourseAndStudent = statisticService.getTotalCourseAndStudent();

        if (totalCourseAndStudent.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       THỐNG KÊ KHÓA HỌC VÀ SINH VIÊN     ║");
        System.out.println("╠════════════════════════╦═════════════════╣");
        System.out.printf ("║ %-22s ║ %-15s ║\n", "Tổng số khóa học", "Tổng sinh viên");
        System.out.println("╠════════════════════════╬═════════════════╣");

        totalCourseAndStudent.forEach((key, value) -> {
            System.out.printf("║ %-22d ║ %-15d ║\n", key, value);
        });

        System.out.println("╚════════════════════════╩═════════════════╝");
    }

    public static void getTotalStudentOfCourse() {
        Map<String, Integer> totalStudentOfCourse = statisticService.getTotalStudentOfCourse();

        if (totalStudentOfCourse.isEmpty()) {
            System.err.println("Không có dữ liệu!");
            return;
        }

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         THỐNG KÊ SINH VIÊN THEO KHÓA HỌC         ║");
        System.out.println("╠════════════════════════════════╦═════════════════╣");
        System.out.printf ("║ %-30s ║ %-15s ║\n", "Tên khóa học", "Tổng sinh viên");
        System.out.println("╠════════════════════════════════╬═════════════════╣");

        totalStudentOfCourse.forEach((key, value) -> {
            System.out.printf("║ %-30s ║ %-15d ║\n", key, value);
        });

        System.out.println("╚════════════════════════════════╩═════════════════╝");
    }

    public static void getTop5CourseMostStudent() {
        Map<String, Integer> top5CourseMostStudent = statisticService.getTop5CourseMostStudent();

        if (top5CourseMostStudent.isEmpty()) {
            System.err.println("Không có kết quả!");
            return;
        }

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║     THỐNG KÊ TOP 5 KHÓA HỌC ĐÔNG SINH VIÊN       ║");
        System.out.println("╠════════════════════════════════╦═════════════════╣");
        System.out.printf ("║ %-30s ║ %-15s ║\n", "Tên khóa học", "Tổng sinh viên");
        System.out.println("╠════════════════════════════════╬═════════════════╣");

        top5CourseMostStudent.forEach((key, value) -> {
            System.out.printf("║ %-30s ║ %-15d ║\n", key, value);
        });

        System.out.println("╚════════════════════════════════╩═════════════════╝");
    }

    public static void getCourseMoreThan10Students() {
        Map<String, Integer> courseMoreThan10Students = statisticService.getCourseMoreThan10Students();

        if (courseMoreThan10Students.isEmpty()) {
            System.err.println("Không có kết quả!");
            return;
        }

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      THỐNG KÊ KHÓA HỌC CÓ HƠN 10 SINH VIÊN       ║");
        System.out.println("╠════════════════════════════════╦═════════════════╣");
        System.out.printf ("║ %-30s ║ %-15s ║\n", "Tên khóa học", "Tổng sinh viên");
        System.out.println("╠════════════════════════════════╬═════════════════╣");

        courseMoreThan10Students.forEach((key, value) -> {
            System.out.printf("║ %-30s ║ %-15d ║\n", key, value);
        });

        System.out.println("╚════════════════════════════════╩═════════════════╝");
    }
}
