from elasticsearch import Elasticsearch
import uuid, json, sys, os

class ElasticSearchClient(object):

    def __init__(self):
        self.elasticsearch = Elasticsearch([{'host': 'elasticsearch', 'port': 9200}])
        self.elasticsearch.indices.create(index = 'clientevents', ignore=400)
        print "ElasticSearch client initialised"

    def publish(self, message):
        doc_id = str(uuid.UUID(bytes=os.urandom(16), version=4))
        print "Publishing: %s with ID %s" % (message, doc_id)
        try:
            self.elasticsearch.index(index = 'clientevents',
                doc_type = 'clientevent',
                id = doc_id,
                body = {
                    'message': message
                })
        except:
            print "Error creating client event: %s" % str(sys.exc_info()[0])
        finally:
            print "Exiting publish for %s" % message

    def close(self):
        self.elasticsearch = None
