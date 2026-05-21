package lt.vu.mif.mybatis.beans;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.mybatis.dao.CourseMapper;
import lt.vu.mif.mybatis.dao.EnrollmentMapper;
import lt.vu.mif.mybatis.dao.StudentMapper;
import lt.vu.mif.mybatis.model.Enrollment;
import lt.vu.mif.mybatis.model.Student;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Model
public class StudentBeanMyBatis {
    @Inject
    private StudentMapper studentMapper;

    @Inject
    private EnrollmentMapper enrollmentMapper;

    @Getter
    private List<Student> allStudents;

    private List<Enrollment> enrollments;

    @Getter
    HashMap<Student, Float> studentAvgs = new HashMap<>();

    public String getAvg(Student student) {
        return studentAvgs.get(student).isNaN() ? "-" : String.valueOf(studentAvgs.get(student));
    }

    @PostConstruct
    public void init() {
        this.allStudents = studentMapper.selectAll();
        this.enrollments = enrollmentMapper.selectAll();

        for (Student student : allStudents) {
            Float gradeSum = 0f;
            int notNullGrades = 0;

            for (Enrollment enrollment : enrollments) {
                if (enrollment.getStudentId().equals(student.getId())) {
                    if (enrollment.getGrade() != null) {
                        gradeSum += enrollment.getGrade();
                        notNullGrades++;
                    }
                }
            }
            studentAvgs.put(student, gradeSum / notNullGrades);
        }
    }


    @Getter @Setter
    private Student studentToCreate = new Student();

    @Transactional
    public String createStudent() {
        studentMapper.insert(studentToCreate);
        return "/mybatis/students.xhtml?faces-redirect=true";
    }

    @Transactional
    public void deleteStudent(Student student) {
        studentMapper.deleteByPrimaryKey(student.getId());
    }

    public void searchStudents() {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            allStudents = studentMapper.selectAll();
            return;
        }
        allStudents = studentMapper.selectByFullName(searchTerm.trim().toLowerCase());
    }

    @Getter @Setter
    private String searchTerm;
}