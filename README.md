# Sensor Statistics Task

## Used open source libraries
`scalaz-stream` source - "https://github.com/scalaz/scalaz-stream"  

## Requirements 
`docker` optional (recommended)  
`docker-compose` optional

## How to run 
To build docker image:
```
docker build . -t sensor_statistics --build-arg UID=$(id -u) --build-arg GID=$(id -g)
```
To run tests (it will take a moment, sbt needs to download whole dependencies, compile source files and run test):
```
docker run -it -v $(pwd)/app:/home/interviewer/app -v `<PATH/FOR/DIRECTORY/WITH/CSV/FILES>:<CUSTOM/PATH/IN/CONTAINER/TO/MOUNT>` sensor_statistics sbt
```
If you use `docker-compose`:
```
docker-compose build --build-arg UID=$(id -u) --build-arg GID=$(id -g)
```
To run service:
```
docker-compose run -v `<PATH/FOR/DIRECTORY/WITH/CSV/FILES>:<CUSTOM/PATH/IN/CONTAINER/TO/MOUNT>` sensor_statistics sbt
```
The most recommended location is to mount it in `/home/interviewer/<DIRNAME>` directory.  
For example to mount `data` catalog into `/home/interviewer/data` use:
```
docker-compose run -v $(pwd)/data:/home/interviewer/data sensor_statistics sbt
```
To run code:
```
run <PATH/TO/DIRECTORY>
```
If you are using docker, remember to mount directory with data

