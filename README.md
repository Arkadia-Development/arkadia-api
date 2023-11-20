# arkadia-api
The backend API for Arkadia Retrocade's website, used to update the status of arcade machines.

## Requirements

Create a file at `src/main/resources/application.properties` in the following format:

```
spring.data.mongodb.uri=your.mongodb.connection.string
spring.data.mongodb.database=databasename
```

## Hosting Instructions

1. Build the Docker image: `docker build -t yourDockerIoUsername/packageName .`
2. Push the Docker image: `docker push yourDockerIoUsername/packageName`
3. Ensure you have the [Fly CLI](fly.io) installed, then use `flyctl launch --image yourDockerIoUsername/packageName` to get the service up and running
