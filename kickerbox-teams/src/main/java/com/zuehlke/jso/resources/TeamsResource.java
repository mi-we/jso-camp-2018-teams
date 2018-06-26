package com.zuehlke.jso.resources;

import com.google.common.collect.Lists;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("team")
@Produces(MediaType.APPLICATION_JSON)
public class TeamsResource {

    @GET
    public List<Team> getTeams() {
        return Lists.newArrayList(
                new Team(42, "LeanAgileSafeTeam",
                        Lists.newArrayList("Theo", "Jörg", "Jonas", "Michi")));
    }
}
