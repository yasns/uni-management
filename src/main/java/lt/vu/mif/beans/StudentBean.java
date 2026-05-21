package lt.vu.mif.beans;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Student;
import lt.vu.mif.persistence.StudentDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Model
public class StudentBean {
    @Inject
    private StudentDao studentDao;

    @Getter
    private List<Student> students = new ArrayList<>();
    @Getter
    @Setter
    private Student studentToCreate = new Student();
    @Getter
    @Setter
    private String searchTerm;

    @PostConstruct
    public void init() {
        this.students = studentDao.listAll();
    }

    public void createStudent() {
        this.studentDao.persist(studentToCreate);
    }

    public void deleteStudent(Student student) {
        this.studentDao.delete(student);
    }

    public void searchStudents() {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            students = studentDao.listAll();
            return;
        }
        students = this.studentDao.findByName(searchTerm.trim().toLowerCase());
    }
}
