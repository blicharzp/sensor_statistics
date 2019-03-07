FROM openjdk:11.0.1

LABEL Name=time_converter

ARG UID
ARG GID

RUN addgroup --gid $GID interviewer_group &&\
    adduser --uid $UID --disabled-password --gecos "" --force-badname --ingroup interviewer_group interviewer &&\
    mkdir -p "/home/interviewer/app"

ENV SCALA_VERSION 2.12.8 
ENV SBT_VERSION 1.2.7

RUN \
  curl -fsL https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo "export PATH=~/scala-$SCALA_VERSION/bin:$PATH" >> /root/.bashrc

RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion && \
  mkdir project && \
  echo "scalaVersion := \"${SCALA_VERSION}\"" > build.sbt && \
  echo "sbt.version=${SBT_VERSION}" > project/build.properties && \
  echo "case object Temp" > Temp.scala && \
  sbt compile && \
  rm -r project && rm build.sbt && rm Temp.scala && rm -r target

WORKDIR /home/interviewer/app
USER interviewer
