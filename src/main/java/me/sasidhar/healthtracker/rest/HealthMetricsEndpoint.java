package me.sasidhar.healthtracker.rest;


import me.sasidhar.healthtracker.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@Path("/")
public class HealthMetricsEndpoint {

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
        HealthTrackerUser user = userRepository.findByUsername(username);
        if(user == null) {
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
        HealthTrackerUser user = userRepository.findByUsername(username);
        if(user == null)
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type("application/json")
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        }

        metric.setUser(user);
        metricsRepository.save(metric);

        return Response.ok(metric).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("users")
    public Response addUser(HealthTrackerUser user) {
        HealthTrackerUser existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null)
        {
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
        HealthTrackerUser user = userRepository.findByUsername(username);
        if(user == null)
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type("application/json")
                    .entity("{\"error\": \"User not found\"}")
                    .build();
        }
        return Response.ok(user).build();
    }
}
