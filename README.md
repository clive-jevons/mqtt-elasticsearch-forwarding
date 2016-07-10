# MQTT to ElasticSearch Forwarder

This represents a collection of applications in various languages which do the same thing:
Forward incoming messages from an MQTT broker to an ElasticSearch instance.

The point is trying to determine:

* How easy is it to implement in a given language
* How well does the solution perform under load

## Docker

The infrastructure components (MQTT broker, ES instance, application) are always deployed
and run in docker containers. To this end, each project provides a `docker-compose.yml`
which can be used to start the scenario with `docker-compose up -d` (for example)
and then you can track the progress with `docker-compose logs -f`.

### Scaling the producer to increase load

You can start multiple producer containers in order to increase the number of messages
being sent to the MQTT broker by using:

`docker-compose scale producer=5`

It's best to chain the commands when starting the scenario, e.g.:

`docker-compose up -d ; docker-compose scale producer=5 ; docker-compose logs -f watchdog`

## Groovy

See [the Groovy subproject](./groovy/README.md).

## Python

See [the Python subproject](./python/README.md).

## JEE / Payara Micro

See [the Payara Micro subproject](./payara-micro/README.md).

## Spring Boot
See [the Spring Boot subproject](./spring-boot/README.md).

## Node.js
See [the Node.js subproject](./nodejs/README.md).

## Producer

See [the producer subproject](./producer/README.md).

## Watchdog

See [the watchdog subproject](./watchdog/README.md).
