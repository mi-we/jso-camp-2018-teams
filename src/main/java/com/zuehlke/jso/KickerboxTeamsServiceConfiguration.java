package com.zuehlke.jso;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class KickerboxTeamsServiceConfiguration extends Configuration {

    Logger logger = LoggerFactory.getLogger(KickerboxTeamsServiceConfiguration.class);

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

    @JsonProperty("environment")
    public String getEnvironment() {
        return environment;
    }

    @JsonProperty("databaseParameters")
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
        Response response = ClientBuilder.newClient()
                .target("http://metadata.google.internal/computeMetadata/v1/project/attributes/{parameterName}")
                .resolveTemplate("parameterName", parameterName)
                .request()
                .header("Metadata-Flavor", "Google")
                .get();

        String potentialParameterValue = response.readEntity(String.class);
        if (response.getStatus() == 200) {
            return potentialParameterValue;
        } else {
            logger.error("Google Metadata API return status code {} with response body {}",
                    response.getStatus(), potentialParameterValue);
            throw new RuntimeException("Unable to retrieve value for parameter " + parameterName + "!");
        }
    }
}
