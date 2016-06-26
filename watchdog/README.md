# Watchdog

The watchdog application is a very simple app which checks the
ElasticSearch server every second for how many documents are
currently in `clientevents/clientevent` and outputs that number
along with how many seconds it has been successfully retrieving
this data.

This is meant as a starting point for a monitoring application
with which we can see how quickly the messages are being
processed by the various forwarding applications.

## Build

Use the gradle wrapper:

`./gradlew clean build`

Next create the Docker Image:

`./docker_build`

The `docker-compose.yml` configurations of the various forwarding
projects use this image. So when you have started one of those
scenarios with `docker-compose up -d`, then you can track progress
of the message processing using:

`docker-compose logs -f watchdog`
