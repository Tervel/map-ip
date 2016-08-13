package com.map_ip.mapip.resources;

import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.codahale.metrics.annotation.Timed;
import com.map_ip.mapip.model.api.ApiEvent;
import com.map_ip.mapip.model.api.ApiStatus;
import com.map_ip.mapip.service.EventService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/v1/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
    private final EventService eventService;
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    // Adds new request, it's associated ip, and whether it was a success or not
    @POST
    @Timed
    public Response createEvent(ApiEvent event) {
        if (isValid(event)){
            eventService.addEvent(event);
            return Response.ok().build();
        }
        return Response.status(400).entity(new ApiStatus("The provided event was malformed or incomplete.")).build();
    }

    private boolean isValid(ApiEvent event) {
        return event != null && !StringUtils.isNullOrEmpty(event.getIp()) && !StringUtils.isNullOrEmpty(event.getType());
    }

    @POST
    @Path("/evaluate")
    @Timed
    public Response evaluateEvent(ApiEvent event) {
        // TODO: implement
        return null;
    }

    @GET
    @Path("/search")
    @Timed
    public Response addEvent(ApiEvent event) {
        // TODO: implement
        return null;
    }

    //CREATE GET END POINT

    /***
     *
     * Gets events with the specified IP, type, and/or, success
     * @param ip
     * @return
     */
//    @Path("{ip}")
    @GET //Using get for now
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventsByIP(@QueryParam("ip") String ip) {
        JSONArray events;
        try {
            events = eventService.getEventsByIP(ip);
            if (events != null) {
                System.out.println("Number of items: " + events.length());
                return Response.ok().entity(events.toString()).build();
            }
        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
        return Response.status(200).entity("IP: " + ip + "\nNOT FOUND").build(); //// TODO: 3/08/2016
    }

}
