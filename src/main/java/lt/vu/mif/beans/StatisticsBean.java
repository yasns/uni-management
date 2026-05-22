package lt.vu.mif.beans;

import lombok.Getter;
import lt.vu.mif.statistics.StatisticsService;
import lt.vu.mif.statistics.StudentStats;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.Future;

@Named
@SessionScoped
public class StatisticsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private StatisticsService statisticsService;

    // transient: Future is not Serializable; if session is passivated the result is lost
    private transient Future<StudentStats> statsFuture;

    @Getter
    private StudentStats stats;
    @Getter
    private CalculationState status = CalculationState.IDLE;

    public void startCalculation() {
        if (status == CalculationState.RUNNING) return;
        stats = null;
        status = CalculationState.RUNNING;
        statsFuture = statisticsService.calculateStats();
    }

    public void pollStatus() {
        if (status != CalculationState.RUNNING || statsFuture == null) return;
        if (statsFuture.isDone()) {
            try {
                stats = statsFuture.get();
                status = CalculationState.DONE;
            } catch (Exception e) {
                status = CalculationState.ERROR;
            }
        }
    }

    public void reset() {
        if (statsFuture != null && !statsFuture.isDone()) {
            statsFuture.cancel(true);
        }
        statsFuture = null;
        stats = null;
        status = CalculationState.IDLE;
    }

    private enum CalculationState {
        IDLE, RUNNING, DONE, ERROR
    }
}
