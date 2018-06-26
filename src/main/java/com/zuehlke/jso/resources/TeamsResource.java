package com.zuehlke.jso.resources;

import com.zuehlke.jso.api.Team;
import com.zuehlke.jso.api.TeamRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("team")
@Produces(MediaType.APPLICATION_JSON)
public class TeamsResource {

    private TeamRepository teamRepository;

    public TeamsResource(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GET
    public Response getTeams() {
        List<Team> teams = teamRepository.selectAllTeams();
        return Response
                .ok(teams)
                .build();
    }

    @GET
    @Path("{teamId}")
    public Response getTeam(@PathParam("teamId") long teamId) {
        Optional<Team> team = teamRepository.selectTeam(teamId);

        if (team.isPresent()) {
            return Response.ok(team.get()).build();
        }

        throw new NotFoundException("Team with id " + teamId + " not found.");
    }

    @POST
    @Consumes("application/json")
    public Response postTeam(Team team) {
        long teamId = teamRepository.insertTeam(team);
        return Response
                .status(Response.Status.CREATED)
                .entity(new Team(teamId, team.getName(), team.getMembers()))
                .build();
    }
}
