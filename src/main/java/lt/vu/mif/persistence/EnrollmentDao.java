package lt.vu.mif.persistence;

import lt.vu.mif.entities.Enrollment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class EnrollmentDao {
    @Inject
    private EntityManager em;

    public void persist(Enrollment enrollment) {
        this.em.persist(enrollment);
    }

    public Enrollment findById(Integer id) {
        return em.find(Enrollment.class, id);
    }

    public List<Enrollment> findByStudentId(Integer studentId) {
        return em.createNamedQuery("Enrollment.findByStudentId", Enrollment.class).setParameter("studentId", studentId).getResultList();
    }

    public Enrollment update(Enrollment enrollment) {
        return em.merge(enrollment);
    }

    public void delete(Enrollment enrollment) {
        em.remove(enrollment);
    }

}