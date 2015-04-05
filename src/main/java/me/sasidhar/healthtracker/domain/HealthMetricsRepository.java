package me.sasidhar.healthtracker.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Sasidhar on 4/5/15.
 */
public interface HealthMetricsRepository extends MongoRepository<HealthMetric, String> {

    @Query("{ 'userId' : ?0 }")
    public List<HealthMetric> findByUserId(String userId);

    @Query("{ 'user.username' : ?0 }")
    public List<HealthMetric> findByUsername(String username);
}
