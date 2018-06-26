package com.zuehlke.jso;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseParameters {

    private String url;

    private String username;

    private String password;

    private String driverClass;

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public String getDriverClass() {
        return driverClass;
    }
}
