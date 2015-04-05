package me.sasidhar;

import me.sasidhar.healthtracker.domain.UserMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    @Autowired
    private UserMetricsRepository userMetricsRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}