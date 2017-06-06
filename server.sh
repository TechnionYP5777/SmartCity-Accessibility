#!/bin/bash
heroku config:set MAVEN_CUSTOM_GOALS="clean package spring-boot:repackage"
git subtree push --prefix server-side heroku master
