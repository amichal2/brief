# brief
Sample application using Ktor framework

### build
`gradlew build`

### run
`gradlew run`

### deploy to IBM Cloud
`cf push brief -m 256M -p build/libs/brief-all.jar`

### url
[https://brief.eu-gb.mybluemix.net/content?query=sport](https://brief.eu-gb.mybluemix.net/content?query=sport)

### docker
#### local
`docker build -t amichal2/brief-repo:0.0.1 .`  
`docker run -p 8080:8080 amichal2/brief-repo:0.0.1`

#### push
`docker push amichal2/brief-repo:0.0.1`

####from Docker registry
`docker swarm init`  
`docker stack deploy -c docker-compose.yml brief`  

####shutdown
`docker stack rm brief`  
`docker swarm leave --force`  
