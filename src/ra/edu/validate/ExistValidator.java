package ra.edu.validate;

import ra.edu.business.model.Course;
import ra.edu.business.service.student.StudentService;
import ra.edu.business.service.student.StudentServiceImp;
import ra.edu.presentation.CourseUI;

import java.util.Scanner;

public class ExistValidator {
    public static String validateExist(Scanner sc, String value, ExistRule existRule) {
        do {
            boolean isExist = false;
            String type = existRule.getType();
            String inputValue = value;

            switch (type) {
                case "courseId":
                    Course course1 = new Course();
                    course1.setCourseId(inputValue);
                    isExist = CourseUI.courseService.checkExistCourseId(course1.getCourseId());
                    break;
                case "courseName":
                    Course course2 = new Course();
                    course2.setCourseName(inputValue);
                    isExist = CourseUI.courseService.checkExistCourseName(course2.getCourseName());
                    break;
                case "studentId":
                    break;
            }

            if (!isExist) {
                return value;
            }

            System.err.println(existRule.getErrorMessage() + " Vui lòng nhập lại!");
            value = sc.nextLine();
        }while (true);
    }
}
