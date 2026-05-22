package lt.vu.mif.persistence;

import lt.vu.mif.entities.Student;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.util.List;

@Decorator
public class CachingStudentDao implements StudentRepository {

    @Inject
    @Delegate
    private StudentRepository delegate;

    private List<Student> cache;

    @Override
    public List<Student> listAll() {
        if (cache == null || cache.isEmpty()) {
            System.out.println("[CACHE] miss, loading from DB");
            cache = delegate.listAll();
        } else {
            System.out.println("[CACHE] hit");
        }
        return cache;
    }

    @Override
    public void persist(Student student) {
        cache = null;
        delegate.persist(student);
    }

    @Override
    public void delete(Student student) {
        cache = null;
        delegate.delete(student);
    }

    @Override
    public Student findById(Integer id) {
        return delegate.findById(id);
    }

    @Override
    public List<Student> findByName(String name) {
        return delegate.findByName(name);
    }

    @Override
    public Student update(Student student) {
        return delegate.update(student);
    }
}
