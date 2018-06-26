package com.zuehlke.jso;

import com.zuehlke.jso.resources.TeamsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
    }

    @Override
    public void run(final KickerboxTeamsServiceConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new TeamsResource());
    }

}
