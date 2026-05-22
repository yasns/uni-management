package lt.vu.mif.persistence;

import lt.vu.mif.entities.Enrollment;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.transaction.Transactional;

@Alternative
@ApplicationScoped
public class SlowEnrollmentDao extends EnrollmentDao {

    @Override
    @Transactional
    public void update(Enrollment enrollment) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        super.update(enrollment);
    }
}
