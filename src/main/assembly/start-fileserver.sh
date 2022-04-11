#!/bin/bash

java -Xmx16m -Xmx64m -jar simple-file-server-1.0.0-SNAPSHOT.jar --spring.config.location=file:/opt/file-server/application.yml
