package ra.edu.utils;

public class TableUtils {
    public static void printHeaderTableCourse(String role) {
        String purpleTextColor = "\u001B[38;5;93m";  // Mã màu ANSI cho màu chữ purple
        String reset = "\u001B[0m";  // Mã reset màu

        if ("admin".equalsIgnoreCase(role)) {
            // In ra bảng với màu chữ purple
            System.out.println(purpleTextColor + "+------------+--------------------------------+------------+-----------------+------------+" + reset);
            System.out.printf(purpleTextColor + "| %-10s | %-30s | %-10s | %-15s | %-10s |" + reset + "\n",
                    "Course ID", "Name", "Duration", "Instructor", "Time");
            System.out.println(purpleTextColor + "+------------+--------------------------------+------------+-----------------+------------+" + reset);
        } else {
            // In ra bảng với màu chữ purple
            System.out.println(purpleTextColor + "+--------------------------------+------------+-----------------+------------+" + reset);
            System.out.printf(purpleTextColor + "| %-30s | %-10s | %-15s | %-10s |" + reset + "\n",
                    "Name", "Duration", "Instructor", "Time");
            System.out.println(purpleTextColor + "+--------------------------------+------------+-----------------+------------+" + reset);
        }
    }


    public static void printHeaderTableStudent() {
        String royalBlueTextColor = "\u001B[38;5;69m";  // Mã màu ANSI cho màu chữ royal blue
        String reset = "\u001B[0m";  // Mã reset màu

        String horizontalBorder = "+------------+--------------------------------+-----------------+----------------------+------------+-----------------+---------------------+";

        // In bảng với màu chữ royal blue
        System.out.println(royalBlueTextColor + horizontalBorder + reset);
        System.out.printf(royalBlueTextColor + "| %-10s | %-30s | %-15s | %-20s | %-10s | %-15s | %-19s |" + reset + "\n",
                "Mã SV", "Tên sinh viên", "Ngày sinh", "Email", "Giới tính", "So điện thoại", "Ngày tạo");
        System.out.println(royalBlueTextColor + horizontalBorder + reset);
    }

    public static void printRegisterCourseHeader() {
        String cornflowerBlueText = "\u001B[38;5;33m";  // Mã màu ANSI cho chữ Cornflower Blue
        String reset = "\u001B[0m";  // Mã reset màu

        String border = "+-----------------+------------------------------------------+-----------------+--------------------------+----------------------+----------------------+";

        System.out.println(cornflowerBlueText + border + reset);
        System.out.printf(cornflowerBlueText + "| %-15s | %-40s | %-15s | %-24s | %-20s | %-20s |" + reset + "\n",
                "Mã khóa học", "Tên khóa học", "Thời lượng (h)", "Giảng viên", "Ngày đăng ký", "Trạng thái");
        System.out.println(cornflowerBlueText + border + reset);
    }

    public static void printHeaderRegisterStudent() {
        String cornflowerBlueText = "\u001B[38;5;33m";  // Mã màu ANSI cho chữ Cornflower Blue
        String reset = "\u001B[0m";  // Mã reset màu

        String border = "+-----------------+--------------------------------+--------------+--------------------------------+----------------------+----------------------+";

        System.out.println(cornflowerBlueText + border + reset);
        System.out.printf(cornflowerBlueText + "| %-15s | %-30s | %-10s | %-30s | %-20s | %-20s |" + reset + "\n",
                "Mã khóa học", "Tên khóa học", "Mã sinh viên", "Tên sinh viên", "Ngày đăng ký", "Trạng thái");
        System.out.println(cornflowerBlueText + border + reset);
    }
}
