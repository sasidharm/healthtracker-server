package me.sasidhar.healthtracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Sasidhar on 4/4/15.
*/
public class UserMetrics {
    @Id
    private String id;
    @DBRef
    private String userId;
    @DBRef
    private List<HealthMetric> metrics;

    public UserMetrics() {

    }

    public UserMetrics(String userId, List<HealthMetric> metrics) {
        this.userId = userId;
        this.metrics = metrics;
    }

    public String getUserId() {
        return userId;
    }

    public List<HealthMetric> getMetrics() {
        return metrics;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMetrics(List<HealthMetric> metrics) {
        this.metrics = metrics;
    }

    public void addMetric(HealthMetric metric) {
        if(metrics == null)
        {
            metrics = new ArrayList<HealthMetric>();
        }
        metrics.add(metric);
    }
}
