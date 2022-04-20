FROM amazoncorretto:8-alpine

ARG JAR

ENV REST_LISTEN_PORT=9966
ENV ELK_SVC_HOST=localhost
ENV ELK_SVC_HOST=9002
ENV ELK_SVC_USER_NAME=dmsadmin
ENV ELK_SVC_PASSWORD=COmmscope123!
ENV ELK_SVC_HTTP_MODE=http


WORKDIR /opt/elk-client/

COPY ${basedir}/version.txt .

ADD target/${JAR} /opt/elk-client/

ENTRYPOINT  ["/bin/sh", "-c", "set -e && java -Xmx512m -jar ./lib/elkclient-service.jar"]
