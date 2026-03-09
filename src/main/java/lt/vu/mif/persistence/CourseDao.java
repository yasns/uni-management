package lt.vu.mif.persistence;

import lt.vu.mif.entities.Course;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
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
}