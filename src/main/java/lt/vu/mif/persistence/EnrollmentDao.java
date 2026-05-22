package lt.vu.mif.persistence;

import lt.vu.mif.entities.Enrollment;
import lt.vu.mif.interceptor.TimedOperation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@TimedOperation
public class EnrollmentDao {
    @Inject
    private EntityManager em;

    public void persist(Enrollment enrollment) {
        this.em.persist(enrollment);
    }

    public List<Enrollment> findByStudentId(Integer studentId) {
        return em.createNamedQuery("Enrollment.findByStudentId", Enrollment.class).setParameter("studentId", studentId).getResultList();
    }

    public void delete(Enrollment enrollment) {
        em.remove(enrollment);
    }

    @Transactional
    public void update(Enrollment enrollment) {
        em.merge(enrollment);
        em.flush();
    }

}