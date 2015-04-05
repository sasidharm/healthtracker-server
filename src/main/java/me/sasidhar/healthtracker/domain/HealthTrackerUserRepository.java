package me.sasidhar.healthtracker.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by Sasidhar on 4/5/15.
 */
public interface HealthTrackerUserRepository extends MongoRepository<HealthTrackerUser, String> {

    @Query("{ 'username' : ?0 }")
    HealthTrackerUser findByUsername(String username);
}
