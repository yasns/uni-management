package lt.vu.mif.beans;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.beans.AlertBean.Type;
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
import javax.persistence.OptimisticLockException;
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

    @Inject
    private AlertBean alertBean;

    @Getter
    private Student student;

    @Getter
    private List<Enrollment> enrollments = new ArrayList<>();

    @Getter
    private List<Course> courses = new ArrayList<>();
    @Getter @Setter
    private Integer selectedCourseId = -1;
    @Getter @Setter
    private Enrollment enrollmentToCreate = new Enrollment();


    @PostConstruct
    private void init() {
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer studentId = Integer.parseInt(requestParameters.get("studentId"));
        student = studentDao.findById(studentId);
        enrollments = enrollmentDao.findByStudentId(studentId);
        courses = courseDao.listNotEnrolledByStudent(student);
    }

    @Transactional
    public void deleteEnrollment(Enrollment enrollment) {
        enrollmentDao.delete(enrollment);
    }

    public String updateGrade(Enrollment enrollment) {
        try {
            enrollmentDao.update(enrollment);
            alertBean.add(Type.SUCCESS, "Grade updated successfully.");
            return "/enrollment?faces-redirect=true&studentId=" + student.getId();
        } catch (OptimisticLockException e) {
            alertBean.add(Type.DANGER, "Record was modified concurrently. Current data is shown — please review and save again.");
            return "/enrollment?faces-redirect=true&studentId=" + student.getId();
        } catch (InterruptedException e) {
            alertBean.add(Type.DANGER, "Update was interrupted. Please try again.");
            return null;
        }
    }

    @Transactional
    public void createEnrollment() {
        Course course = courseDao.findById(selectedCourseId);

        enrollmentToCreate.setCourse(course);
        enrollmentToCreate.setStudent(student);

        enrollmentDao.persist(enrollmentToCreate);
        alertBean.add(Type.SUCCESS, "Student enrolled successfully.");
    }

}
