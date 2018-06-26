# Kickerbox Teams Service

How to start the Kickerbox Teams Service application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/kickerbox-teams-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


Deployment
---
Install and init gcloud. Afterwards it is as easy as:
`gcloud app deploy`
