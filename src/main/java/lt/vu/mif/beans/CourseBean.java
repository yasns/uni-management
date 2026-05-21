package lt.vu.mif.beans;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Course;
import lt.vu.mif.entities.Student;
import lt.vu.mif.persistence.CourseDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Model
public class CourseBean {
    @Inject
    private CourseDao courseDao;

    @Getter
    private List<Course> courses = new ArrayList<>();
    @Getter
    @Setter
    private Course courseToCreate = new Course();

    @PostConstruct
    public void init() {
        this.courses = courseDao.listAll();
    }

    @Transactional
    public void createCourse() {
        this.courseDao.persist(courseToCreate);
    }

    @Transactional
    public void deleteCourse(Course course) {
        this.courseDao.delete(course);
    }

}
