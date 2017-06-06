#!/bin/bash
heroku auth:token
heroku open -a smartcity-accessibility
heroku config:set MAVEN_CUSTOM_GOALS="clean package spring-boot:repackage"
git subtree push --prefix server-side heroku master
