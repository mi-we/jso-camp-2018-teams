FROM gcr.io/google_appengine/openjdk8

ADD target/kickerbox-teams-1.0-SNAPSHOT.jar app.jar
ADD config.yaml config.yaml

CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "server", "config.yaml"]