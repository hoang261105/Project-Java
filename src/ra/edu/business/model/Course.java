package ra.edu.business.model;

import ra.edu.business.IApp;
import ra.edu.validate.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Course implements IApp {
    private String courseId;
    private String courseName;
    private int duration;
    private String instructor;
    private LocalDate createdAt;

    public Course() {
    }

    public Course(String courseId, String courseName, int duration, String instructor, LocalDate createdAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.instructor = instructor;
        this.createdAt = createdAt;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void inputData(Scanner sc) {
        this.courseId = inputCourseId(sc);

        this.courseName = inputCourseName(sc);

        this.duration = Validator.validateInputInt(sc, "Nhập thời lượng:");

        this.instructor = Validator.validateInputString(sc, "Nhập tên giảng viên: ", new StringRule(0, 100, "Tên giảng viên không hợp lệ"));
    }

    public String inputCourseId(Scanner sc){
        String courseId = Validator.validateInputRegex(sc, "Nhập id khóa học:", new RegexRule("^(C).{4}$", "Id khóa học không hợp lệ"));
        return ExistValidator.validateExist(sc, courseId, new ExistRule("courseId", "Id khóa học đã tồn tại"));
    }

    public String inputCourseName(Scanner sc){
        String courseName = Validator.validateInputString(sc, "Nhập tên khóa học: ", new StringRule(0, 100, "Tên khóa học không hợp lệ"));
        return ExistValidator.validateExist(sc, courseName, new ExistRule("courseName", "Tên khóa học đã tồn tại"));
    }

    @Override
    public void displayData() {
        System.out.printf("| %-10s | %-20s | %-8d | %-15s | %-10s |\n",
                courseId, courseName, duration, instructor, createdAt);
    }
}
