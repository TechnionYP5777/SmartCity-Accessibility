#!/bin/bash
cd server-side
echo "Killing previous server"
killall screen
echo "Running server..."
mvn exec:java -Dexec.mainClass="smartcity.accessibility.services.Application" -Dserver.port=8090
echo "Successs!"
