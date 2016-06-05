# Python MQTT to ES forwarder

The Python implementation is based on Paho Python and the ElasticSearch Python DSL.

It's recommended to use a VirtualEnv to do the building. E.g. from within the
python subdirectory:

	> virtualenv venv
	> . venv/bin/activate
	> pip install -r requirements.txt
	> python src/run.py

(the `venv` directory is ignored by git).
