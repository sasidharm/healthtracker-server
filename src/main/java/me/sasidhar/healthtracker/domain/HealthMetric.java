package me.sasidhar.healthtracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Sasidhar on 4/4/15.
 */
@Document(collection = "healthmetrics")
public class HealthMetric {

    @Id
    private String id;

    @DBRef
    private HealthTrackerUser user;

    private String name;
    private String value;
    private Unit unit;
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public HealthTrackerUser getUser() {
        return user;
    }

    public void setUser(HealthTrackerUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("UserMetric[name='%s', value='%s', unit='%s', timestamp='%s']",
                name, value, unit.getSymbol(), timestamp);
    }
}
