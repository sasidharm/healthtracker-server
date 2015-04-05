package me.sasidhar.healthtracker.rest;


import me.sasidhar.healthtracker.domain.HealthMetric;
import me.sasidhar.healthtracker.domain.HealthMetricsRepository;
import me.sasidhar.healthtracker.domain.HealthTrackerUser;
import me.sasidhar.healthtracker.domain.HealthTrackerUserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@Path("/")
public class HealthMetricsEndpoint {

    private static final Log logger = LogFactory.getLog(HealthMetricsEndpoint.class);
    @Autowired
    private HealthTrackerUserRepository userRepository;

    @Autowired
    private HealthMetricsRepository metricsRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAlive() {
        return Response.ok("{\"status\" : \"HealthTracker is up and running\"}").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users/{username}/metrics")
    public Response getUserMetrics(@PathParam("username") String username) {
        logger.info("Received get metrics request with username [" + username + "]");
        HealthTrackerUser user = userRepository.findByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type("application/json")
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        }
        List<HealthMetric> metrics = metricsRepository.findByUserId(user.getId());
        return Response.ok(metrics).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("users/{username}/metrics")
    public Response addMetric(@PathParam("username") String username, HealthMetric metric) {
        logger.info("Received add metric request with username [" + username + "] and metric [" + metric.getName() + "]");
        HealthTrackerUser user = userRepository.findByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type("application/json")
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        }

        metric.setUser(user);
        metricsRepository.save(metric);

        return Response.ok(metric).build();
    }

    @DELETE
    @Path("users/{username}/metrics/{metricId}")
    public Response deleteMetric(@PathParam("username") String username, @PathParam("metricId") String metricId) {
        logger.info("Received delete metric request with username [" + username + "] and metricId [" + metricId + "]");
        HealthMetric metric = metricsRepository.findOne(metricId);
        if (metric == null || !metric.getUser().getUsername().equals(username)) {
            throw new NotFoundException();
        }
        metricsRepository.delete(metric);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("users")
    public Response addUser(HealthTrackerUser user) {
        logger.info("Received add user request with username [" + user.getUsername() + "]");
        HealthTrackerUser existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type("application/json")
                    .entity("{\"error\": \"User already exists\"}")
                    .build();
        }
        userRepository.save(user);
        return Response.ok(user).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users/{username}")
    public Response getUser(@PathParam("username") String username) {
        logger.info("Received get user request with username [" + username + "]");
        HealthTrackerUser user = userRepository.findByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type("application/json")
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        }
        return Response.ok(user).build();
    }
}
