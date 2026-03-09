package lt.vu.mif.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NamedQueries({@NamedQuery(name = "Enrollment.findByStudentId", query = "select e from Enrollment as e where e.student.id = :studentId")})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"student", "course"})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1)
    @Max(value = 10)
    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}