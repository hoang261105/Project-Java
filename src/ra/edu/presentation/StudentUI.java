package ra.edu.presentation;

import java.util.Scanner;

public class StudentUI {
    public static void printMenuStudent(Scanner sc) {
        do {
            System.out.println("+===========================================================+");
            System.out.println("|                  QUẢN LÍ HỌC VIÊN                         |");
            System.out.println("+===========================================================+");
            System.out.println("|  1. Xem danh sách học viên                                |");
            System.out.println("|  2. Thêm mới học viên                                     |");
            System.out.println("|  3. Cập nhật học viên                                     |");
            System.out.println("|  4. Xóa học viên                                          |");
            System.out.println("|  5. Tìm kiếm học viên                                     |");
            System.out.println("|  6. Sắp xếp học viên                                      |");
            System.out.println("|  7. Phân trang                                            |");
            System.out.println("|  8. Quay về menu chính                                    |");
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
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 8!");
            }
        } while (true);
    }
}
