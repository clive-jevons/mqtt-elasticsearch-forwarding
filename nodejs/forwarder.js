var mqtt = require('mqtt')
var elasticsearch = require('elasticsearch');
var uuid = require('node-uuid');

var esClient = new elasticsearch.Client({
  host: 'elasticsearch:9200',
  log: 'error'
});

var mqttClient = mqtt.connect('tcp://mqttbroker:1883')
mqttClient.on('connect', function () {
  mqttClient.subscribe('test/topic');
});
mqttClient.on('message', function (topic, message) {
  esClient.index({
    "index": "clientevents",
    "type": "clientevent",
    "id": uuid.v4(),
    "body": {
      "the_message": message,
      "the_topic": topic
    }
  });
});


