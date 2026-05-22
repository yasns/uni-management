package lt.vu.mif.persistence;

import lt.vu.mif.entities.Course;
import lt.vu.mif.entities.Student;
import lt.vu.mif.interceptor.TimedOperation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@TimedOperation
public class CourseDao {
    @Inject
    private EntityManager em;

    public void persist(Course course) {
        this.em.persist(course);
    }

    public List<Course> listAll() {
        return em.createNamedQuery("Course.listAll", Course.class).getResultList();
    }

    public Course findById(Integer id) {
        return em.find(Course.class, id);
    }

    public Course update(Course course) {
        return em.merge(course);
    }

    public void delete(Course course) {
        em.remove(course);
    }

    public List<Course> listNotEnrolledByStudent(Student student) {
        return em.createNamedQuery("Course.listNotEnrolledByStudent", Course.class).setParameter("student", student).getResultList();
    }
}