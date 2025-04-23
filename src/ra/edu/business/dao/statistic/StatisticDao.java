package ra.edu.business.dao.statistic;

import java.util.Map;

public interface StatisticDao {
    Map<Integer, Integer> getTotalCourseAndStudent();

    Map<String, Integer> getTotalStudentOfCourse();

    Map<String, Integer> getTop5CourseMostStudent();

    Map<String, Integer> getCourseMoreThan10Students();
}
