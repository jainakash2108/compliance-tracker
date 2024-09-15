# Clarity Compliance System
Application that allows Compliance Officers to manage and track company compliance information in accordance with the Transparency Act. The system supports adding, retrieving, updating, and deleting company records, with a focus on supply chain ethics, sustainability, corporate governance, and labor rights.

# Development

## Technology:

* Java 21
* Spring boot 3
* Gradle as build tool

## Startup instructions

```shell
# go to application directory
cd compliance-tracker
# build project using maven
./gradlew clean build
# build docker image
docker buildx build . -t compliance-tracker:latest --platform linux/amd64 --load
```

```
Run ComplianceTrackerApplication
```

## Swagger documentation

[Swagger](http://localhost:8080/compliance-tracker/swagger-ui/index.html)


### Sample request and response
[compliance-tracker.http](https://github.com/jainakash2108/compliance-tracker/blob/main/src/main/http/compliance-tracker.http)
