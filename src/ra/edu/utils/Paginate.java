package ra.edu.utils;

import ra.edu.business.model.Course;
import ra.edu.presentation.CourseUI;

import java.util.List;
import java.util.Scanner;

public class Paginate {
    public static final int ITEMS_PER_PAGE = 5;
    public static void paginateCourse(Scanner sc, List<Course> courseList){
        int totalPage = (int) Math.ceil((double) courseList.size() / ITEMS_PER_PAGE);

        do {
            for (int i = 1; i <= totalPage; i++) {
                System.out.printf("Trang %d\n", i);
            }
            System.out.printf("Lựa chọn của bạn (hoặc nhấn 0 để thoát): ");

            int curPage = Integer.parseInt(sc.nextLine());

            if (curPage == 0) {
                return;
            }

            if (curPage < 0 || curPage > totalPage) {
                System.err.println("Trang không hợp lệ. Vui lòng nhập lại!");
                continue;
            }

            courseList = CourseUI.courseService.paginationCourse(curPage, ITEMS_PER_PAGE);

            TableUtils.printHeaderTableCourse();
            courseList.forEach(course -> {
                course.displayData();
                System.out.println("+------------+--------------------------------+------------+-----------------+------------+");
            });
        } while (true);
    }
}
