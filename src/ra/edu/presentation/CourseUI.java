package ra.edu.presentation;

import ra.edu.business.model.Course;
import ra.edu.business.service.course.CourseService;
import ra.edu.business.service.course.CourseServiceImp;
import ra.edu.utils.Paginate;
import ra.edu.utils.TableUtils;
import ra.edu.validate.ExistRule;
import ra.edu.validate.ExistValidator;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class CourseUI {
    public static CourseService courseService = new CourseServiceImp();

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
            System.out.println("|  7. Quay về menu chính                                    |");
            System.out.println("+===========================================================+");

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    Paginate.paginateCourse(sc, courseService.findAll());
                    break;
                case 2:
                    courseUI.addCourse(sc);
                    break;
                case 3:
                    courseUI.updateCourse(sc);
                    break;
                case 4:
                    courseUI.deleteCourse(sc);
                    break;
                case 5:
                    courseUI.searchCourseByName(sc);
                    break;
                case 6:
                    courseUI.sortCourse(sc);
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 7!");
            }
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
                System.out.println("4. Trở về menu quản lí");

                int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

                switch (choice) {
                    case 1:
                        String courseName = Validator.validateInputString(sc, "Nhập tên khóa học: ", new StringRule(0, 100, "Tên khóa học không hợp lệ"));
                        course.setCourseName(ExistValidator.validateExist(sc, courseName, new ExistRule("courseName", "Tên khóa học đã tồn tại")));
                        boolean result1 = courseService.update(course, 1);

                        if (result1) {
                            System.out.println("Cập nhật tên khóa học thành công!");
                        } else {
                            System.err.println("Cập nhật tên khóa học thất bại");
                        }
                        break;
                    case 2:
                        int newDuration = Validator.validateInputInt(sc, "Nhập thời lượng mới:");
                        course.setDuration(newDuration);

                        boolean result2 = courseService.update(course, 2);

                        if (result2) {
                            System.out.println("Cập nhật thời lương thành công!");
                        } else {
                            System.err.println("Cập nhật thời lượng thất bại");
                        }
                        break;
                    case 3:
                        String newInstructor = Validator.validateInputString(sc, "Nhập tên giảng viên: ", new StringRule(0, 100, "Tên giảng viên không hợp lệ"));
                        course.setInstructor(newInstructor);

                        boolean result3 = courseService.update(course, 3);

                        if (result3) {
                            System.out.println("Cập nhật tên giảng viên thành công!");
                        } else {
                            System.err.println("Cập nhật tên giảng viên thất bại");
                        }
                        break;
                    case 4:
                        return;
                    default:
                        System.err.println("Vui lòng chọn lại từ 1 - 4!");
                }
            } while (true);
        } else {
            System.err.println("Không tìm thấy khóa học!");
        }
    }

    public void deleteCourse(Scanner sc) {
        System.out.println("Nhập vào id khóa học cần xóa:");
        String courseId = sc.nextLine();

        if (courseService.findCourseById(courseId) != null) {
            System.out.println("Bạn có chắc chắn muốn xóa không?");
            String confirm = sc.nextLine();

            if (confirm.equals("y")) {
                Course course = new Course();
                course.setCourseId(courseId);
                boolean result = courseService.delete(course);

                if (result) {
                    System.out.println("Xóa khóa học thành công!");
                } else {
                    System.err.println("Xóa thất bại!");
                }
            } else if (confirm.equals("n")) {
                System.out.println("Hủy xác nhận xóa");
            }
        } else {
            System.err.println("Không tìm thấy khóa học");
        }
    }

    public void searchCourseByName(Scanner sc) {
        System.out.println("Nhập vào tên khóa học câần tìm:");
        String courseName = sc.nextLine();

        List<Course> filterCourse = courseService.searchCourseByName(courseName);

        if (filterCourse.isEmpty()) {
            System.err.println("Không có kết quả phù hợp");
            return;
        }

        Paginate.paginateCourse(sc, filterCourse);
    }

    public void sortCourse(Scanner sc) {
        do {
            System.out.println("==================SẮP XẾP THEO==================");
            System.out.println("1. Id");
            System.out.println("2. Tên khóa học");
            System.out.println("3. Trở về menu quản lí");
            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice) {
                case 1:
                    sortCourseByAttribute(sc, "courseId");
                    break;
                case 2:
                    sortCourseByAttribute(sc, "courseName");
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1 - 3!");
            }

        } while (true);
    }

    public void sortCourseByAttribute(Scanner sc, String attribute) {
        do {
            System.out.println("1. Tăng dần");
            System.out.println("2. Giảm dần");
            System.out.println("3. Trở về menu sắp xêp");

            int choice = Validator.validateInputInt(sc, "Lựa chọn của bạn:");

            switch (choice){
                case 1:
                    switch (attribute){
                        case "courseName":
                            List<Course> sortCourseName = courseService.sortCourse(3);
                            Paginate.paginateCourse(sc, sortCourseName);
                            System.out.println("Sắp xếp theo tên tăng dần thành công");
                            break;
                        case "courseId":
                            List<Course> sortCourseId = courseService.sortCourse(1);
                            Paginate.paginateCourse(sc, sortCourseId);
                            System.out.println("Sắp xếp theo mã khóa học tăng dần thành công");
                            break;
                    }
                    break;
                case 2:
                    switch (attribute){
                        case "courseName":
                            List<Course> sortCourseNameDesc = courseService.sortCourse(4);
                            Paginate.paginateCourse(sc, sortCourseNameDesc);
                            System.out.println("Sắp xếp theo tên giảm dân thành công");
                            break;
                        case "courseId":
                            List<Course> sortCourseIdDesc = courseService.sortCourse(2);
                            Paginate.paginateCourse(sc, sortCourseIdDesc);
                            System.out.println("Sắp xếp theo mã khóa học giảm dần thành công");
                            break;
                    }
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Vui lòng chọn lại từ 1- 3!");
            }
        }while (true);
    }
}
