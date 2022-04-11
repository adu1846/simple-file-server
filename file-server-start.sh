#!/bin/sh

echo "starting simple-file-server ..."
echo "APP_CONFIG_PATH=${APP_CONFIG_PATH}"

if [ "${APP_CONFIG_PATH}" = "false" ]; then
  echo "using default configuration"
  echo "JAVA_OPTS=${JAVA_OPTS}"
  java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /simple-file-server.jar
else
  echo "using custom configuration"
  echo "APP_CONFIG_PATH=${APP_CONFIG_PATH}"
  echo "JAVA_OPTS=${JAVA_OPTS}"
  java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /simple-file-server.jar \
     --spring.config.location=file:${APP_CONFIG_PATH}
fi
