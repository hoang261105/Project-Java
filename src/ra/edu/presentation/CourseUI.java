package ra.edu.presentation;

import ra.edu.business.model.Course;
import ra.edu.business.service.course.CourseService;
import ra.edu.business.service.course.CourseServiceImp;
import ra.edu.utils.TableUtils;
import ra.edu.validate.ExistRule;
import ra.edu.validate.ExistValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class CourseUI {
    public static CourseService courseService = new CourseServiceImp();
    public static final int ITEMS_PER_PAGE = 5;

    public static void printMenuCourse(Scanner sc) {
        CourseUI courseUI = new CourseUI();
        do {
            System.out.println("+===========================================================+");
            System.out.println("|                  QUẢN LÍ KHÓA HỌC                         |");
            System.out.println("+===========================================================+");
            System.out.println("|  1. Hiển thị danh sách khóa học có phân trang             |");
            System.out.println("|  2. Thêm mới khóa học                                     |");
            System.out.println("|  3. Cập nhật khóa học                                     |");
            System.out.println("|  4. Xóa khóa học                                          |");
            System.out.println("|  5. Tìm kiếm khóa học                                     |");
            System.out.println("|  6. Sắp xếp khóa học                                      |");
            System.out.println("|  7. Phân trang                                            |");
            System.out.println("|  8. Quay về menu chính                                    |");
            System.out.println("+===========================================================+");
            System.out.printf("Lựa chọn của bạn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    courseUI.paginationCourses(sc);
                    break;
                case 2:
                    courseUI.addCourse(sc);
                    break;
                case 3:
                    courseUI.updateCourse(sc);
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
                    System.err.println("Vui lòng chọn lại từ 1 - 7!");
            }
        } while (true);
    }

    public void paginationCourses(Scanner sc) {
        do {
            List<Course> courseList = courseService.findAll();

            int totalPages = (int) Math.ceil((double) courseList.size() / ITEMS_PER_PAGE);

            System.out.println("Danh sách trang");
            for (int i = 1; i <= totalPages; i++) {
                System.out.printf("Trang %d\n", i);
            }
            System.out.printf("Lựa chọn của bạn (Hoặc nhấn 0 để thoát): ");

            int currentPage = Integer.parseInt(sc.nextLine());

            if (currentPage == 0) {
                break;
            }

            if (currentPage < 0 || currentPage > totalPages) {
                System.err.println("Trang không hợp lệ. Vui lòng nhập lại!");
                continue;
            }

            List<Course> paginateCourses = courseService.paginationCourse(currentPage);

            TableUtils.printHeaderTableCourse();
            paginateCourses.forEach(course -> {
                course.displayData();
                System.out.println("+------------+----------------------+------------+-----------------+------------+");
            });
        } while (true);
    }

    public void addCourse(Scanner sc) {
        System.out.println("Nhập số khóa học cần thêm:");
        int size = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < size; i++) {
            System.out.println("Nhập thông tin khóa học thứ " + (i + 1));
            Course course = new Course();
            course.inputData(sc);
            boolean result = courseService.add(course);
            if (result) {
                System.out.println("Thêm khóa học thành công!");
            } else {
                System.err.println("Thêm thất bại!");
            }
        }
    }

    public void updateCourse(Scanner sc) {
        System.out.println("Nhập vào id khóa học cần câp nhật");
        String courseId = sc.nextLine();

        Course course = courseService.findCourseById(courseId);

        if (course != null) {
            do {
                System.out.println("==================CẬP NHẬT THÔNG TIN================");
                System.out.println("1. Cập nhật tên khóa học");
                System.out.println("2. Cập nhật thời lượng");
                System.out.println("3. Cập nhật giảng viên");
                System.out.println("4. Trở về menu chính");
                System.out.printf("Lựa chọn của bạn: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        String courseName = Validator.validateInputString(sc, "Nhập tên khóa học: ", new StringRule(0, 100, "Tên khóa học không hợp lệ"));
                        course.setCourseName(ExistValidator.validateExist(sc, courseName, new ExistRule("courseName", "Tên khóa học đã tồn tại")));
                        boolean result1 = courseService.update(course, 1);

                        if (result1){
                            System.out.println("Cập nhật tên khóa học thành công!");
                        }else{
                            System.err.println("Cập nhật tên khóa học thất bại");
                        }
                        break;
                    case 2:
                        int newDuration = Validator.validateInputInt(sc, "Nhập thời lượng mới:");
                        course.setDuration(newDuration);

                        boolean result2 = courseService.update(course, 2);

                        if (result2){
                            System.out.println("Cập nhật thời lương thành công!");
                        }else{
                            System.err.println("Cập nhật thời lượng thất bại");
                        }
                        break;
                    case 3:
                        String newInstructor =  Validator.validateInputString(sc, "Nhập tên giảng viên: ", new StringRule(0, 100, "Tên giảng viên không hợp lệ"));
                        course.setInstructor(newInstructor);

                        boolean result3 = courseService.update(course, 3);

                        if (result3){
                            System.out.println("Cập nhật tên giảng viên thành công!");
                        }else{
                            System.err.println("Cập nhật tên giảng viên thất bại");
                        }
                        break;
                    case 4:
                        return;
                    default:
                        System.err.println("Vui lòng chọn lại từ 1 - 4!");
                }
            } while (true);
        }else{
            System.err.println("Không tìm thấy khóa học!");
        }
    }
}
