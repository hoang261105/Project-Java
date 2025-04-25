package ra.edu.presentation;

import ra.edu.business.model.*;
import ra.edu.business.service.course.CourseService;
import ra.edu.business.service.course.CourseServiceImp;
import ra.edu.utils.Paginate;
import ra.edu.validate.ExistValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class CourseUI {
    public static CourseService courseService = new CourseServiceImp();
    private static final String RESET = "\u001B[0m";
    private static final String LIME_GREEN = "\u001B[38;5;10m";
    private static final String ORANGE_RED = "\u001B[38;5;202m";

    public static void printMenuCourse(Scanner sc) {
        CourseUI courseUI = new CourseUI();
        while (true) {
            showCourseMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");
            switch (choice) {
                case 1 -> Paginate.paginateCourse(sc, courseService.findAllOfRole("admin"), "admin");
                case 2 -> courseUI.addCourse(sc);
                case 3 -> courseUI.updateCourse(sc);
                case 4 -> courseUI.deleteCourse(sc);
                case 5 -> courseUI.searchCourseByName(sc);
                case 6 -> courseUI.sortCourse(sc);
                case 7 -> { return; }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 7!");
            }
        }
    }

    private static void showCourseMenu() {
        System.out.println(LIME_GREEN + """
                ╔═══════════════════════════════════════════════════════════╗
                ║                 ==== QUẢN LÍ KHÓA HỌC ====                ║
                ╠═══════════════════════════════════════════════════════════╣
                ║  1. Hiển thị danh sách khóa học có phân trang             ║
                ║  2. Thêm mới khóa học                                     ║
                ║  3. Cập nhật khóa học                                     ║
                ║  4. Xóa khóa học                                          ║
                ║  5. Tìm kiếm khóa học                                     ║
                ║  6. Sắp xếp khóa học                                      ║
                ║  7. Quay về menu chính                                    ║
                ╚═══════════════════════════════════════════════════════════╝""" + RESET);
    }

    public void addCourse(Scanner sc) {
        int size = Validator.validateInputInt(sc, "Nhập số khóa học cần thêm:");
        for (int i = 0; i < size; i++) {
            System.out.printf("Nhập thông tin khóa học thứ %d%n", i + 1);
            Course course = new Course();
            course.inputData(sc);
            boolean result = courseService.add(course);
            System.out.println(result ? "Thêm khóa học thành công!" : "Thêm thất bại!");
        }
    }

    public void updateCourse(Scanner sc) {
        System.out.println("Nhập vào id khóa học cần cập nhật:");
        String courseId = sc.nextLine().trim();
        Course course = courseService.findCourseById(courseId);

        if (course == null) {
            System.err.println("Không tìm thấy khóa học!");
            return;
        }

        while (true) {
            showUpdateMenu();
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1 -> updateCourseName(sc, course);
                case 2 -> updateCourseDuration(sc, course);
                case 3 -> updateCourseInstructor(sc, course);
                case 4 -> { return; }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 4!");
            }
        }
    }

    private void showUpdateMenu() {
        System.out.println(ORANGE_RED + """
                ╔═══════════════════════════════════════════════════╗
                ║             CẬP NHẬT THÔNG TIN KHÓA HỌC           ║
                ╠═══════════════════════════════════════════════════╣
                ║ 1. Cập nhật tên khóa học                          ║
                ║ 2. Cập nhật thời lượng                            ║
                ║ 3. Cập nhật giảng viên                            ║
                ║ 4. Trở về menu quản lí                            ║
                ╚═══════════════════════════════════════════════════╝""" + RESET);
    }

    private void updateCourseName(Scanner sc, Course course) {
        String newName = Validator.validateInputString(sc, "Nhập tên khóa học mới: ",
                new StringRule(0, 100, true, "Tên khóa học không hợp lệ"));
        course.setCourseName(ExistValidator.validateExist(sc, newName,
                new ExcludeId("courseName", "Tên khóa học đã tồn tại", course.getCourseId())));
        printUpdateResult(courseService.update(course, 1), "tên khóa học");
    }

    private void updateCourseDuration(Scanner sc, Course course) {
        int newDuration = Validator.validateInputInt(sc, "Nhập thời lượng mới:");
        course.setDuration(newDuration);
        printUpdateResult(courseService.update(course, 2), "thời lượng");
    }

    private void updateCourseInstructor(Scanner sc, Course course) {
        String newInstructor = Validator.validateInputString(sc, "Nhập tên giảng viên: ",
                new StringRule(0, 100, false, "Tên giảng viên không hợp lệ"));
        course.setInstructor(newInstructor);
        printUpdateResult(courseService.update(course, 3), "giảng viên");
    }

    private void printUpdateResult(boolean result, String fieldName) {
        System.out.println(result ? "Cập nhật " + fieldName + " thành công!" : "Cập nhật " + fieldName + " thất bại");
    }

    public void deleteCourse(Scanner sc) {
        System.out.println("Nhập vào id khóa học cần cập xóa:");
        String courseId = sc.nextLine().trim();
        Course course = courseService.findCourseById(courseId);

        if (course == null) {
            System.err.println("Không tìm thấy khóa học");
            return;
        }

        List<CourseRegistrationInfo> registered = EnrollmentUI.enrollmentService
                .findCourseRegistrationByStudent(courseId).stream()
                .filter(info -> info.getStatus().equals(Status.CONFIRMED))
                .toList();

        if (!registered.isEmpty()) {
            System.err.println("Khóa học có sinh viên học, không thể xoá!");
            return;
        }

        System.out.println("Bạn có chắc chắn muốn xóa không? (y/n)");
        if ("y".equalsIgnoreCase(sc.nextLine().trim())) {
            boolean result = courseService.delete(course);
            System.out.println(result ? "Xóa khóa học thành công!" : "Xóa thất bại!");
        } else {
            System.out.println("Hủy xác nhận xóa");
        }
    }

    public void searchCourseByName(Scanner sc) {
        System.out.println("Nhập vào tên khóa học cần tìm:");
        String courseName = sc.nextLine().trim();
        List<Course> filterCourse = courseService.searchCourseByName(courseName);

        if (filterCourse.isEmpty()) {
            System.err.println("Không có kết quả phù hợp");
        } else {
            Paginate.paginateCourse(sc, filterCourse, "admin");
        }
    }

    public void sortCourse(Scanner sc) {
        while (true) {
            System.out.println("""
                    ==================SẮP XẾP THEO==================
                    1. Id
                    2. Tên khóa học
                    3. Trở về menu quản lí""");

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");
            switch (choice) {
                case 1 -> sortCourseByAttribute(sc, "courseId");
                case 2 -> sortCourseByAttribute(sc, "courseName");
                case 3 -> { return; }
                default -> System.err.println("Vui lòng chọn lại từ 1 - 3!");
            }
        }
    }

    public void sortCourseByAttribute(Scanner sc, String attribute) {
        while (true) {
            System.out.println("""
                    1. Tăng dần
                    2. Giảm dần
                    3. Trở về menu sắp xếp""");

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");
            List<Course> sortedCourses = null;
            String direction = "";

            switch (choice) {
                case 1:
                    sortedCourses = courseService.sortCourse(attribute.equals("courseId") ? 1 : 3);
                    direction = "tăng dần";
                    break;
                case 2:
                    sortedCourses = courseService.sortCourse(attribute.equals("courseId") ? 2 : 4);
                    direction = "giảm dần";
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1- 3!");
                    continue;
            }

            Paginate.paginateCourse(sc, sortedCourses, "admin");
            System.out.printf("Sắp xếp theo %s %s thành công%n", attribute.equals("courseId") ? "mã khóa học" : "tên", direction);
        }
    }
}
