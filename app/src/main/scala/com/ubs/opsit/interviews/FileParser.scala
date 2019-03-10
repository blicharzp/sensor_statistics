package com.ubs.opsit.interviews

import scalaz.stream.io

import scala.collection.mutable.ListMap
import scala.language.implicitConversions

object FileParser {
    var sensorMeasurements = scala.collection.mutable.ListMap[String,SensorMeasurement]()
    var globalMeasurement = new GlobalMeasurement()

    def process(filepaths: List[String]): Measurements = {
        parse(filepaths)
        Measurements(globalMeasurement,
                     sensorMeasurements.toSeq.sortBy(_._2.avg)(Ordering[Option[Float]].reverse),
                     filepaths.length)
    }

    private def parse(filepaths: List[String]): Unit = { 
        filepaths foreach parseFile
    }

    private def parseFile(filepath: String): Unit = {
        io.linesR(filepath)
          .drop(1)
          .map(row => MeasurementConverter.convert(row.split(",")))
          .map{ case (sensor, measurement) => sensorMeasurements.getOrElseUpdate(sensor, new SensorMeasurement())
                                                                .add(measurement)
                                              globalMeasurement.add(measurement) }
          .run
          .run
    }
}
