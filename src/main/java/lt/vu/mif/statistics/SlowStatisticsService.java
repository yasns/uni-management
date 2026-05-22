package lt.vu.mif.statistics;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import java.util.concurrent.Future;

@Alternative
@Stateless
public class SlowStatisticsService extends StatisticsService {

    @Asynchronous
    @Override
    public Future<StudentStats> calculateStats() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return super.calculateStats();
    }
}
