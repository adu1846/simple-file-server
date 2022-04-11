#!/bin/bash

FILE_SERVER_PID=`ps -ef | grep java | grep simple-file-server- | awk '{print $2}'`
kill ${FILE_SERVER_PID}
