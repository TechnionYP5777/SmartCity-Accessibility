#!/bin/bash
cd server-side
echo "Killing previous server"
killall screen
echo "Running server..."
screen -dmL mvn clean install exec:java -Dexec.mainClass="smartcity.accessibility.services.Application" -Dserver.port=8090 -DskipTests
sleep 5m
echo "Successs!"
