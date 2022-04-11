ARG ARCH="amd64"
FROM ${ARCH}/adoptopenjdk:8-jre
COPY build/libs/simple-file-server-*-SNAPSHOT.jar /simple-file-server.jar
COPY file-server-start.sh /file-server-start.sh
COPY src/main/resources/application.yml /application.yml
RUN chmod +x /file-server-start.sh
ENV APP_CONFIG_PATH /application.yml
ENV JVM_OPTS "-Xms64m -Xmx256m"
ENTRYPOINT ["/file-server-start.sh"]