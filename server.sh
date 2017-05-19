#!/bin/bash
cd server-side
echo "Killing previous server"
killall screen
echo "Running server..."
screen -dmL mvn exec:java -Dexec.mainClass="smartcity.accessibility.services.Application" -Dserver.port=8090
sleep 5m
echo "Successs!"
