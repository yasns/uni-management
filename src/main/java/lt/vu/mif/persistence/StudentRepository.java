package lt.vu.mif.persistence;

import lt.vu.mif.entities.Student;

import java.util.List;

public interface StudentRepository {
    void persist(Student student);
    List<Student> listAll();
    Student findById(Integer id);
    List<Student> findByName(String name);
    Student update(Student student);
    void delete(Student student);
}
