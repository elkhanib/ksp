# KSP - Knowledge Sharing Platform!

---
KSP is a knowledge sharing platform where [iO](https://www.iodigital.com/en/home) colleagues (hopefully including me) can keep their curiosity going on. As MVP/pilot, I've included only TedTalks, but it's designed with _package by future_ pattern, which makes easy to add a new features.
</br></br>

### Requirements
___
You will need a JDK 11 version and docker installed on your machine.
</br></br>

### How to run it?
___
Please follow below steps to run project.
* open terminal
* change directory to go to project's folder
* run `gradlew clean dockerBuild`
* run `docker-compose up -d`

It will run in docker machine, and service will be available at: [localhost:8081](http://localhost:8081/ksp/swagger-ui/index.html])
</br></br>

### How to test it?
[Postman collection](postman/ksp-v1.postman_collection.json) included in project's root folder, import it to your postman to see request and response samples. </br>
Also included two ([docker](postman/docker.postman_environment.json) and [local](postman/local.postman_environment.json)) environment files. 
</br></br>

### Issues

---
* When we start the docker-compose, mysql container should be waited by all containers (like init-container in k8s deployment). Unfortunately I couldn't make a time to configure it correctly. So when you start docker-compose problably you will see some exceptions, but in a minute it will be worked :(
* H2 database used in integration testing. It can be replaced with [test-containers](https://www.testcontainers.org/)
* Some (e.g: domain, entity, config, etc.) classes need to be excluded from coverage report
* Improve open-api documentation
* Use JWT to implement security

