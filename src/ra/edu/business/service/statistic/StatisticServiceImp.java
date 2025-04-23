package ra.edu.business.service.statistic;

import ra.edu.business.dao.statistic.StatisticDao;
import ra.edu.business.dao.statistic.StatisticDaoImp;

import java.util.Map;

public class StatisticServiceImp implements StatisticService {
    private StatisticDao statisticDao;

    public StatisticServiceImp() {
        statisticDao = new StatisticDaoImp();
    }

    @Override
    public Map<Integer, Integer> getTotalCourseAndStudent() {
        return statisticDao.getTotalCourseAndStudent();
    }

    @Override
    public Map<String, Integer> getTotalStudentOfCourse() {
        return statisticDao.getTotalStudentOfCourse();
    }

    @Override
    public Map<String, Integer> getTop5CourseMostStudent() {
        return statisticDao.getTop5CourseMostStudent();
    }

    @Override
    public Map<String, Integer> getCourseMoreThan10Students() {
        return statisticDao.getCourseMoreThan10Students();
    }
}
