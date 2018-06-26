package com.zuehlke.jso.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Team {

    private int id;
    private String name;
    private List<String> members;

    @JsonCreator
    public Team(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("members") List<String> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getMembers() {
        return members;
    }

}
