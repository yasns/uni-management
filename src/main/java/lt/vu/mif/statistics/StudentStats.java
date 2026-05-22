package lt.vu.mif.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class StudentStats implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int totalStudents;
    private final int totalEnrollments;
    private final double averageGrade;
    private final double medianGrade;
    private final int studentsNoGrade;
}
