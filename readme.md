# Springboot hello world project with jenkins pipeline
This project represents an example of a Jenkins pipeline with springboot and docker. 
It uses git as scm. Every time a git push will be placed, jenkins will detect it and run the jenkinspipeline.
Only on the master branch, it will build a docker image and deploy it to the local docker host
on port 8080.<br> 
The example has version and health check functionality to have a better hello world project. ;)

## DevOp-System 
* host has 192.168.20.14 as ip address
* docker ce installation
* dockerized nexus 
    * private docker registry host on 8082
    * private docker registry proxy on 8083
* a native jenkins installation
    * stored credentials 'devopvm-docker-registry' for private docker registry
* maven settings.xml in /opt/maven
