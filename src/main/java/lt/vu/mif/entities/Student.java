package lt.vu.mif.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Student.listAll", query = "select s from Student as s"),
        @NamedQuery(name = "Student.findByName", query = "select s from Student as s where lower(s.firstName) like :name or lower(s.lastName) like :name"),
        @NamedQuery(name = "Student.listAllWithEnrollments", query = "select distinct s from Student s left join fetch s.enrollments e left join fetch e.course")
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}
