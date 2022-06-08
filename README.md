# KSP - Knowledge Sharing Platform!

KSP is a knowledge sharing platform where [iO](https://www.iodigital.com/en/home) colleagues (hopefully including me) can keep their curiosity going. As a MVP, we included only TedTalks, but its designed with _package by future_ pattern, which makes easy to add a new features.

### Requirements
If you're a developer you will need a JDK 11 version and docker installed on your machine.
If you're a business guy, or QA, or someone that only interested in BlackBox, you will need just docker installed machine to run application.

### How to run it?
Please follow below steps to run project.
* open terminal
* change directory to go to project's folder
* run `gradlew clean dockerBuild`
* run `docker-compose up -d`

It will run in docker machine, and service will be available at: http://localhost:8081/ksp/swagger-ui/index.html

### How to test it?
Postman collection included in postman folder, import it to your postman to see request and response samples.

### Issues
* When we start the docker-compose, mysql container should be waited by all containers (like init-container in k8s deployment). Unfortunately I couldn't make a time to configure it correctly. So when you start docker-compose problably you will see some exceptions, but in a minute it will be worked :(
* H2 database used in integration testing. It can be replaced with [test-containers](https://www.testcontainers.org/)
* Some (e.g: domain, entity, config, etc.) classes need to be excluded from coverage report
* Improve open-api documentation
* Use JWT to implement security

