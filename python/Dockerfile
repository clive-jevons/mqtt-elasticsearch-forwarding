FROM python:2-alpine

RUN mkdir -p /consumer
ADD requirements.txt /consumer/
ADD src /consumer/src/
RUN cd /consumer && pip install -r requirements.txt

ENTRYPOINT ["python", "/consumer/src/run.py"]
