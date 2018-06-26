package com.zuehlke.jso.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.util.LinkedList;
import java.util.List;

public class Team {

    private long id;
    private String name;
    private List<String> members;

    @JdbiConstructor
    public Team(
            @ColumnName("id") long id,
            @ColumnName("name") String name) {
        this.id = id;
        this.name = name;
        this.members = new LinkedList<>();
    }

    @JsonCreator
    public Team(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("members") List<String> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getMembers() {
        if (members == null) {
            members = new LinkedList<>();
        }
        return members;
    }

}
