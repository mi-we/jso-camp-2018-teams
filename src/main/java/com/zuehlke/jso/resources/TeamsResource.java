package com.zuehlke.jso.resources;

import com.zuehlke.jso.api.Team;
import com.zuehlke.jso.api.TeamRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Supplier;

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

    @POST
    @Consumes("application/json")
    public Response postTeam(Team team) {
        long teamId = teamRepository.insertTeam(team);
        return Response
                .status(Response.Status.CREATED)
                .entity(new Team(teamId, team.getName(), team.getMembers()))
                .build();
    }

    @GET
    @Path("{teamId}")
    public Response getTeam(@PathParam("teamId") long teamId) {
        Team team = teamRepository.selectTeam(teamId)
                .orElseThrow(teamNotFoundException(teamId));

        return Response
                .ok(team)
                .build();
    }

    @PUT
    @Path("{teamId}")
    public Response putTeam(@PathParam("teamId") long teamId, Team teamToUpdate) {
        Team existingTeam = teamRepository.selectTeam(teamId)
                .orElseThrow(teamNotFoundException(teamId));

        teamRepository.updateTeam(
                new Team(existingTeam.getId(), teamToUpdate.getName(), teamToUpdate.getMembers())
        );

        Team updatedTeam = teamRepository.selectTeam(teamId).orElseThrow(teamNotFoundException(teamId));

        return Response.ok(updatedTeam).build();
    }

    private Supplier<NotFoundException> teamNotFoundException(long teamId) {
        return () -> new NotFoundException("Team with id " + teamId + " not found.");
    }
}
