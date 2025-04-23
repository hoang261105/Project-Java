package ra.edu.validate;

import ra.edu.business.model.Course;
import ra.edu.business.model.ExcludeId;
import ra.edu.business.model.Student;
import ra.edu.presentation.CourseUI;
import ra.edu.presentation.StudentUI;

import java.util.List;
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
                    isExist = CourseUI.courseService.checkExistCourseName(course2.getCourseName(), course2.getCourseId());
                    break;
                case "studentId":
                    Student student1 = new Student();
                    student1.setStudentId(inputValue);
                    isExist = StudentUI.studentService.isExistStudentId(student1.getStudentId());
                    break;
                case "email":
                    Student student2 = new Student();
                    student2.setEmail(inputValue);
                    isExist = StudentUI.studentService.isExistEmail(student2.getEmail());
                    break;
                case "phone":
                    Student student3 = new Student();
                    student3.setPhoneNumber(inputValue);
                    isExist = StudentUI.studentService.isExistPhone(student3.getPhoneNumber());
                    break;
            }

            if (!isExist) {
                return value;
            }

            System.err.println(existRule.getErrorMessage() + " Vui lòng nhập lại!");
            value = sc.nextLine();
        }while (true);
    }

    // version mới: truyền thêm courseId để loại trừ
    public boolean checkExistCourseName(String name, String excludeCourseId) {
        List<Course> courseList = CourseUI.courseService.findAllOfRole("admin");
        for (Course course : courseList) {
            if (course.getCourseName().equalsIgnoreCase(name)
                    && !course.getCourseId().equalsIgnoreCase(excludeCourseId)) {
                return true;
            }
        }
        return false;
    }

}
