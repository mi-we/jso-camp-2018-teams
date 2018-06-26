package com.zuehlke.jso;

import com.zuehlke.jso.resources.TeamsResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class KickerboxTeamsServiceApplication extends Application<KickerboxTeamsServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new KickerboxTeamsServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "Kickerbox Teams Service";
    }

    @Override
    public void initialize(final Bootstrap<KickerboxTeamsServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<KickerboxTeamsServiceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(KickerboxTeamsServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(new JdbiExceptionsBundle());
    }

    @Override
    public void run(final KickerboxTeamsServiceConfiguration configuration,
                    final Environment environment) {
        initializeRestClient(configuration, environment);
        environment.jersey().register(new TeamsResource());
    }

    private void initializeRestClient(KickerboxTeamsServiceConfiguration configuration, Environment environment) {
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        RestClientHolder.setClient(client);
    }

}
