package ra.edu.business.service.enrollment;

import ra.edu.business.model.Enrollment;

import java.util.List;

public class EnrollmentServiceImp implements EnrollmentService {
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
