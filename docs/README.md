# brief
Sample application using Ktor framework

### build
gradlew clean \
gradlew build

### run
gradlew run

### deploy to IBM Cloud
cf push brief -m 256M -p build/libs/brief-all.jar
