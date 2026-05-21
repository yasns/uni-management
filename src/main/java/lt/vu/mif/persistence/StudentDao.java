package lt.vu.mif.persistence;

import lt.vu.mif.entities.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StudentDao {
    @Inject
    private EntityManager em;

    @Transactional
    public void persist(Student student) {
        this.em.persist(student);
    }

    public List<Student> listAll() {
        return em.createNamedQuery("Student.listAll", Student.class).getResultList();
    }

    public Student findById(Integer id) {
        return em.find(Student.class, id);
    }

    public List<Student> findByName(String name) {
        return em.createNamedQuery("Student.findByName", Student.class).setParameter("name", "%" + name.toLowerCase() + "%").getResultList();
    }

    @Transactional
    public Student update(Student student) {
        return em.merge(student);
    }

    @Transactional
    public void delete(Student student) {
        em.remove(student);
    }
}