package ra.edu.utils;

public class TableUtils {
    public static void printHeaderTableCourse(){
        String horizontalBorder = "+------------+----------------------+------------+-----------------+------------+";

        System.out.println(horizontalBorder);
        System.out.printf("| %-10s | %-20s | %-10s | %-15s | %-10s |\n",
                "Mã KH", "Tên khóa học", "Thời gian", "Giảng viên", "Ngày tạo");
        System.out.println(horizontalBorder);
    }
}
