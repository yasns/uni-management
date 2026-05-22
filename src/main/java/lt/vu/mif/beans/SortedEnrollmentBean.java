package lt.vu.mif.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Specializes;
import java.util.Comparator;

@Model
@Specializes
public class SortedEnrollmentBean extends EnrollmentBean {

    @PostConstruct
    private void sortEnrollments() {
        getEnrollments().sort(Comparator.comparing(e -> e.getCourse().getTitle()));
    }
}
