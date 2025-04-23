package ra.edu.business.service.student;

import ra.edu.business.model.Student;
import ra.edu.business.service.AppService;

import java.util.List;

public interface StudentService extends AppService<Student> {
    List<Student> paginationStudents(int currentPage, int itemPerPage);

    boolean isExistStudentId(String studentId);

    boolean isExistEmail(String studentName);

    boolean isExistPhone(String studentPhone);

    Student findStudentById(String studentId);

    boolean createAccount(Student student);

    List<Student> searchStudent(String attribute, int option);

    List<Student> sortStudent(int option);

    boolean changePassword(String studentId, String newPassword);
}
