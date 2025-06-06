package ra.edu.business.service.statistic;

import ra.edu.business.service.AppService;

import java.util.Map;

public interface StatisticService {
    Map<Integer, Integer> getTotalCourseAndStudent();

    Map<String, Integer> getTotalStudentOfCourse();

    Map<String, Integer> getTop5CourseMostStudent();

    Map<String, Integer> getCourseMoreThan10Students();
}
