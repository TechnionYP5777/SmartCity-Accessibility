#!/bin/bash
echo "Killing previous client if such exists"
killall ionic
echo "Running client..."
cd client-side
echo "Installing with npm"
ionic plugin add cordova-plugin-geolocation
npm install --save @ionic-native/geolocation
echo "Serving ionic"
screen -d -m -L ionic serve -p 80 --nolivereload --nobrowser
sleep 5m
echo "Success!"
