package com.zuehlke.jso;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

public class KickerboxTeamsServiceConfiguration extends Configuration {

    @NotNull
    private String environment;

    @NotNull
    private DatabaseParameters databaseParameters;

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
        this.jerseyClient = jerseyClient;
    }

    @JsonProperty
    public String getEnvironment() {
        return environment;
    }

    @JsonProperty
    public DatabaseParameters getDatabaseParameters() {
        return databaseParameters;
    }

    public DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setUser(databaseParameters.getUsername());
        dataSourceFactory.setDriverClass(databaseParameters.getDriverClass());
        dataSourceFactory.setUrl(retrieveUrl());
        dataSourceFactory.setPassword(retrievePassword());
        return dataSourceFactory;
    }

    private String retrieveUrl() {
        if (environment.equals("local")) {
            return databaseParameters.getUrl();
        }
        return retrieveParameter("db-url");
    }

    private String retrievePassword() {
        if (environment.equals("local")) {
            return databaseParameters.getPassword();
        }

        return retrieveParameter("db-password");
    }

    private String retrieveParameter(String parameterName) {
        Response response = RestClientHolder.getClient()
                .target("http://metadata.google.internal/computeMetadata/v1/project/attributes/:parameterName")
                .resolveTemplate("parameterName", parameterName)
                .request()
                .header("Metadata-Flavor", "Google")
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(String.class);
        } else {
            throw new RuntimeException("Unable to retrieve password for databaseParameters!");
        }
    }
}
