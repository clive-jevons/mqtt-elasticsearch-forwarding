# JEE / Payara Micro

This project builds on [Payara Micro](http://blog.payara.fish/introducing-payara-micro)
as the JEE container, and constitues a very simple EJB based version of the
consumer which has one singleton to hold the MQTT client and one for the
ElasticSearch client, and lastly one startup singleton which marries the two.

## Build

Use the gradle wrapper and the docker build script:

`./gradlew clean build && ./docker_build`

## Running

Fire up the scenario with docker compose:

`docker-compose up -d ; docker-compose logs -f`

and end it with:

`docker-compose stop ; docker-compose rm -f`
