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

## Groovy

See [the Groovy subproject](./groovy/README.md).
