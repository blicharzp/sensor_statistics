# Sensor Statistics Task

## Used open source libraries
`scalaz-stream` source - "https://github.com/scalaz/scalaz-stream"  
`ScalaTest` source -https://github.com/scalatest/scalatest

## Requirements 
`docker` optional (recommended)  
`docker-compose` optional (recommended)

## How to run

### Docker
To build docker image:
```
docker build . -t sensor_statistics --build-arg UID=$(id -u) --build-arg GID=$(id -g)
```
To run container (it will take a moment, sbt needs to download whole dependencies, compile source files and run test):
```
docker run -it -v $(pwd)/app:/home/interviewer/app -v `<PATH/FOR/DIRECTORY/WITH/CSV/FILES>:<CUSTOM/PATH/IN/CONTAINER/TO/MOUNT>` sensor_statistics sbt
```
The most recommended location (examples in this readme uses this location) is `/home/interviewer/<DIRNAME>` directory.  
Remember to mount directory with data and use `<CUSTOM/PATH/IN/CONTAINER/TO/MOUNT>` path as a parameter and pass it as a parameter.  
To simplify process of `sbt` dependency collection, you can add this 3 mounting points (already done if you are using `docker-compose`):
```
-v $(pwd)/sbt/.ivy:/home/interviewer/.ivy
```
```
-v $(pwd)/sbt/.ivy2:/home/interviewer/.ivy2
```
```
-v $(pwd)/sbt/.sbt:/home/interviewer/.sbt
```
Example usage:
```
docker run -it -v $(pwd)/app:/home/interviewer/app -v $(pwd)/data:/home/interviewer/data -v $(pwd)/sbt/.ivy:/home/interviewer/.ivy -v $(pwd)/sbt/.ivy2:/home/interviewer/.ivy2 -v $(pwd)/sbt/.sbt:/home/interviewer/.sbt sensor_statistics sbt
```
Run for selected in example mounting point for *.csv files in `sbt` prompt:
```
run /home/interviewer/data
```

### Docker Compose
Setting up whole enviroment is much easier for `docker-compose` users.  
To build image:
```
docker-compose build --build-arg UID=$(id -u) --build-arg GID=$(id -g)
```
To run built container:
```
docker-compose run -v `<PATH/FOR/DIRECTORY/WITH/CSV/FILES>:<CUSTOM/PATH/IN/CONTAINER/TO/MOUNT>` sensor_statistics sbt
```
Example usage:
```
docker-compose run -v $(pwd)/data:/home/interviewer/data sensor_statistics sbt
```
Run for selected in example mounting point:
```
run /home/interviewer/data
```

### Usage
To run application in `sbt` prompt:
```
run <PATH/TO/DIRECTORY>
```
To run tests in `sbt` prompt:
```
test
```
For usage in `bash` prompt:
```
sbt run <PATH/TO/DIRECTORY> / sbt test
```