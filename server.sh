#!/bin/bash
cd server-side
echo "Killing previous server"
kill -9 `cat save_pid.log`
echo "Running server..."
screen -dmL mvn exec:java -Dexec.mainClass="smartcity.accessibility.services.Application" -Dserver.port=8090
echo $! > save_pid.log
echo "Successs!"
