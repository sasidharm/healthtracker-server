package me.sasidhar;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import me.sasidhar.healthtracker.domain.HealthMetric;
import me.sasidhar.healthtracker.domain.HealthTrackerUser;
import me.sasidhar.healthtracker.domain.Unit;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({"spring.data.mongodb.uri:mongodb://localhost/healthtracker"})
public class HealthMetricsEndpointTest {
    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    public void isAlive() throws Exception {
        when().get("/").then()
                .body("status", is("HealthTracker is up and running"));
    }

    @Test
    public void getMetrics() throws Exception {
        ValidatableResponse response = given().get("/users/sasi/metrics")
                .then()
                .statusCode(200)
                .contentType(is("application/json;charset=UTF-8"));

        response.body("[0].name", is("Weight"));
        response.body("[0].user.id", is("57005a278d6a6892a68ba8f6"));
    }

    @Test
    public void addMetric() throws Exception {
        HealthMetric metric = new HealthMetric();
        metric.setName("Weight");
        metric.setValue("94.5");
        metric.setTimestamp(new Date().getTime());
        metric.setUnit(Unit.SI_WEIGHT);
        ValidatableResponse response = given().contentType(ContentType.JSON).body(metric)
                .post("users/sasi/metrics")
                .then()
                .statusCode(200)
                .contentType(is("application/json;charset=UTF-8"));

        response.body("value", is("94.5"));
    }

    @Test
    @Ignore
    public void addUser() throws Exception {
        HealthTrackerUser user = new HealthTrackerUser();
        user.setUsername("sasi");
        user.setFirstName("Sasidhar");
        user.setLastName("Mukkamala");
        user.setPassword("ap11l3426");
        user.setDateOfBirth(DateTime.parse("04/23/1984", DateTimeFormat.forPattern("MM/dd/yyyy")).toDate());

        given().contentType(ContentType.JSON)
                .body(user)
                .post("users")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void getUser() throws Exception {
        given().get("users/sasi")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("username", is("sasi"));
    }
}