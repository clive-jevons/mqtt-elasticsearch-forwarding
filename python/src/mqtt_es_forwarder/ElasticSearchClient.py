from elasticsearch_dsl.connections import connections
from elasticsearch_dsl import DocType, String
import uuid

class ClientEvent(DocType):

    message = String()

    class Meta:
        index = 'clientevents'
        doc_type = 'clientevent'

class ElasticSearchClient(object):

    def __init__(self):
        #connections.create_connection(hosts=['elasticsearch'], timeout=20)
        connections.create_connection(hosts=['localhost'], timeout=20)
        ClientEvent.init()
        print "ElasticSearch client initialised"

    def publish(self, message):
        print "Publishing: %s" % message
        try:
            client_event = ClientEvent(message = message)
            client_event.meta.id = uuid.uuid4().int()
            print "About to save: %s" % str(client_event)
            client_event.save()
            print "Finished calling save."
        except:
            print "Error creating client event: %s" % str(e)

    def close(self):
        try: connections.remove_connection('default')
        except: print "Unable to close ES connection"
