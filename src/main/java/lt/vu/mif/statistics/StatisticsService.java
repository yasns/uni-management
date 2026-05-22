package lt.vu.mif.statistics;

import lt.vu.mif.entities.Enrollment;
import lt.vu.mif.entities.Student;
import lt.vu.mif.interceptor.TimedOperation;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@Stateless
public class StatisticsService {

    // @PersistenceContext bypasses the CDI-produced @RequestScoped EM which is
    // unavailable in the async thread (no CDI request context there).
    @PersistenceContext(unitName = "university-pu")
    private EntityManager em;

    private double median(List<Integer> grades) {
        if (grades.isEmpty()) return 0.0;
        Collections.sort(grades);
        int mid = grades.size() / 2;
        return grades.size() % 2 == 1
                ? grades.get(mid)
                : (grades.get(mid - 1) + grades.get(mid)) / 2.0;
    }

    @Asynchronous
    public Future<StudentStats> calculateStats() {
        List<Student> students = em.createNamedQuery("Student.listAllWithEnrollments", Student.class).getResultList();

        int totalEnrollments = 0;
        int gradeSum = 0;
        int studentsNoGrade = 0;
        List<Integer> grades = new ArrayList<>();

        for (Student s : students) {
            boolean hasUngraded = false;
            for (Enrollment e : s.getEnrollments()) {
                totalEnrollments++;
                if (e.getGrade() != null) {
                    gradeSum += e.getGrade();
                    grades.add(e.getGrade());
                } else {
                    hasUngraded = true;
                }
            }
            if (hasUngraded) studentsNoGrade++;
        }

        double avgGrade = grades.isEmpty() ? 0.0 : (double) gradeSum / grades.size();
        double medianGrade = median(grades);

        return new AsyncResult<>(new StudentStats(
                students.size(), totalEnrollments, avgGrade, medianGrade, studentsNoGrade
        ));
    }
}
