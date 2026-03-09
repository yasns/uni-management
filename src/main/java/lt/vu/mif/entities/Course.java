package lt.vu.mif.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Course.listAll", query = "select c from Course as c")})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"title", "credits"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @NotNull
    private String title;

    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer credits;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}