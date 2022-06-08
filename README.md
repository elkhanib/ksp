# KSP - Knowledge Sharing Platform!

KSP is a knowledge sharing platform where [iO](https://www.iodigital.com/en/home) colleagues (hopefully including me) can keep their curiosity going. As a MVP, we included only TedTalks, but its designed with _package by future_ pattern, which makes easy to add a new features.

### Requirements
If you're a developer you will need a JDK 11 version and docker installed on your machine.
If you're a business guy, or QA, or someone that only interested in BlackBox, you will need just docker installed machine to run application.

### How to run
Please follow below steps to run project.
* open terminal
* change directory to go to project's folder
* run `gradlew clean dockerBuild`
* run `docker-compose up -d`
  It will run in docker machine, and service will be available at: http://localhost:8081/ksp/swagger-ui/index.html


### Issues
* `PUT` and `DELETE` methods are not implemented
* When we start the docker-compose, mysql container should be waited by all containers (like init-container in k8s deployment). Unfortunately I couldn't make a time to configure it correctly. So when you start docker-compose problably you will see some exceptions, but in a minute it will be worked :(
* Integration tests can be implemented using [test-containers](https://www.testcontainers.org/)
* Test coverage for can be increased. At the moment its 66% lines and 81% classes are covered. Some classes needs to be excluded from coverage report

