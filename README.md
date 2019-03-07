# Sensor Statistics Task

## Requirements 
`docker` installed
`docker-compose` optional

## How to run 
To build docker image:
```
docker build . -t sensor_statistics --build-arg UID=$(id -u) --build-arg GID=$(id -g)
```
To run tests (it will take a moment, sbt needs to download whole dependencies, compile source files and run test):
```
docker run -it -v $(pwd)/app:/home/interviewer/app sensor_statistics sbt
```
If you use `docker-compose`:
```
docker-compose build --build-arg UID=$(id -u) --build-arg GID=$(id -g)
```
To run service:
```
docker-compose run sensor_statistics sbt
```
To run code:
```
run <PATH/TO/DIRECTORY>
```

