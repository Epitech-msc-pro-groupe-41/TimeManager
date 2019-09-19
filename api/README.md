# Launch the Api Rest

### Start mongoDB in docker

For running mongoDB in Docker, type the following command in a shell : 
- docker run -d -p 27017:27017 -v ~/data:/data/db_mongo mongo

To see if mongoDB is started, type this command :
- docker ps
You must see an image with the name : mongo and it's container ID.

### Start the Api Rest of the Time Manager

To launch the server open a shell and follow these commands:
- Go to api directory 
- ./mvnw install
- java -jar target/api-0.0.1-SNAPSHOT.jar

### Reference Documentation

You have documentation about api routes in this link : http://localhost:4000/rest/swagger-ui.html#/