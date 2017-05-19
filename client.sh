#!/bin/bash
echo "Killing previous client if such exists"
killall ionic
echo "Running client..."
cd client-side
echo "Installing with npm"
npm install --silent
echo "Serving ionic"
screen -d -m -L ionic serve 80 --nolivereload --nobrowser
sleep 5m
echo "Success!"
