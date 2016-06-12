# Producer Application

A small Groovy / Java application which simply pumps out MQTT messages in
an infinite loop.

Lives inside an Alpine-based docker image.

## Build

`./gradlew clean build && ./docker_build`

## Run

Generally run as part of one of the `docker-compose.yml` setups.
