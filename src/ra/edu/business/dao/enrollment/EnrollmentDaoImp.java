package ra.edu.business.dao.enrollment;

import ra.edu.business.model.Enrollment;

import java.util.List;

public class EnrollmentDaoImp implements EnrollmentDao {

    @Override
    public List<Enrollment> findAll() {
        return List.of();
    }

    @Override
    public boolean add(Enrollment enrollment) {
        return false;
    }

    @Override
    public boolean update(Enrollment enrollment, int option) {
        return false;
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        return false;
    }
}
