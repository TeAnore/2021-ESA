package com.example.lab1.controller;

import com.example.lab1.dao.RatingDao;
import com.example.lab1.models.Rating;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/ratings")
public class RatingController {
    @EJB
    private RatingDao ratingDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // -> localhost:8080/ratings
    public Response getRatings() throws JsonProcessingException {
        List<Rating> ratings = ratingDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(ratings))
                .build();
    }

    @GET
    @Path("/{ratingId}") // -> localhost:8080/ratings/{ratingId}
    public Response getRatingById(@PathParam("ratingId") String ratingId) throws JsonProcessingException {
        Rating rating = ratingDao.get(Integer.valueOf(ratingId));

        if (rating == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Rating with id %s not found", ratingId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(rating))
                .build();
    }

    @POST
    @Path("/") // -> localhost:8080/ratings/{formParams}
    public Response addNewRating(
            @FormParam("code") String code,
            @FormParam("description") String description) {
        Rating rating = new Rating();
        rating.setCode(code);
        rating.setDescription(description);

        ratingDao.save(rating);
        return Response.ok().build();
    }

    @PUT
    @Path("/{ratingId}") // -> localhost:8080/ratings/{ratingId} + {formParams}
    public Response updateRating(
            @PathParam("ratingId") String ratingId,
            @DefaultValue("") @FormParam("code") String code,
            @DefaultValue("") @FormParam("description") String description
    ) {
        Rating rating = ratingDao.get(Integer.valueOf(ratingId));
        if (rating == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Rating with id %s not found", ratingId)).build();

        if (!code.isEmpty())
            rating.setCode(code);

        if (!description.isEmpty())
            rating.setDescription(description);

        ratingDao.update(rating);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{ratingId}") // -> localhost:8080/ratings/{ratingId}
    public Response deleteRating(@PathParam("ratingId") String ratingId) {
        Rating rating = ratingDao.get(Integer.valueOf(ratingId));
        if (rating == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Rating with id %s not found", ratingId)).build();

        ratingDao.delete(rating);
        return Response.ok().build();
    }
}
