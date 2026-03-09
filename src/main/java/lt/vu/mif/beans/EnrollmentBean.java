package lt.vu.mif.beans;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Course;
import lt.vu.mif.entities.Enrollment;
import lt.vu.mif.entities.Student;
import lt.vu.mif.persistence.CourseDao;
import lt.vu.mif.persistence.EnrollmentDao;
import lt.vu.mif.persistence.StudentDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class EnrollmentBean {
    @Inject
    private EnrollmentDao enrollmentDao;

    @Inject
    private StudentDao studentDao;

    @Inject
    private CourseDao courseDao;

    @Getter
    private Student student;

    @Getter
    private List<Enrollment> enrollments = new ArrayList<>();

    @Getter
    private List<Course> courses = new ArrayList<>();
    @Getter
    @Setter
    private Integer selectedCourseId = -1;
    @Getter
    @Setter
    private Enrollment enrollmentToCreate = new Enrollment();

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer studentId = Integer.parseInt(requestParameters.get("studentId"));
        student = studentDao.findById(studentId);
        enrollments = enrollmentDao.findByStudentId(studentId);
        courses = courseDao.listAll();
    }

    @Transactional
    public void deleteEnrollment(Enrollment enrollment) {
        enrollmentDao.delete(enrollment);
    }

    @Transactional
    public void createEnrollment() {
        Course course = courseDao.findById(selectedCourseId);

        enrollmentToCreate.setCourse(course);
        enrollmentToCreate.setStudent(student);

        enrollmentDao.persist(enrollmentToCreate);
    }
}
