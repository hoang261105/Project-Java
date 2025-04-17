package ra.edu.business.service.student;

import ra.edu.business.model.Student;
import ra.edu.business.service.AppService;

public interface StudentService extends AppService<Student> {
    boolean checkLoginUser(String email, String password);
}
