package me.sasidhar.healthtracker.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Sasidhar on 4/4/15.
 */
public interface UserMetricsRepository extends MongoRepository<UserMetrics, String> {

    @Query("{ 'userId' : ?0 }")
    public UserMetrics findByUserId(String userId);

}
