package lt.vu.mif.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AlertBean implements Serializable {

    private final List<Alert> pending = new ArrayList<>();

    public void add(Type type, String message) {
        pending.add(new Alert(type, message));
    }

    public List<Alert> getAlerts() {
        if (pending.isEmpty()) return new ArrayList<>();
        List<Alert> copy = new ArrayList<>(pending);
        pending.clear();
        return copy;
    }

    public enum Type {
        SUCCESS, DANGER, WARNING, INFO;

        public String cssClass() {
            return name().toLowerCase();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Alert implements Serializable {
        private final Type type;
        private final String message;
    }
}