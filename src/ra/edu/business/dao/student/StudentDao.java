package ra.edu.business.dao.student;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Student;

public interface StudentDao extends AppDao<Student> {
    boolean checkLoginUser(String email, String password);
}
