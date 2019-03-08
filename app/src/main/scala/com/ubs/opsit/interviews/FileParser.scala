package com.ubs.opsit.interviews

import scalaz.stream._
import scalaz.concurrent._

import scala.collection.mutable.Map

object FileParser {
    var collectedData = scala.collection.mutable.Map[String,Option[Int]]()
    var globalMeasurment = GlobalMeasurement
    
    def parse(filepaths: List[String]): Unit = { 
        filepaths foreach parseFile
        println(globalMeasurment.allMeasurement)
        println(globalMeasurment.failedMeasurement)
    }

    private def parseFile(filepath: String): Unit = {
        io.linesR(filepath)
          .drop(1)
          .map(row => toTuple(row.split(",")))
          .map{ case (_, measurement) => globalMeasurment.add(measurement) }
          .run
          .run
    }

    private def toTuple(data: Array[String]): Tuple2[String, Option[Int]] = data match {
        case Array(sensor, measurement, _*) => (sensor, convertMeasurement(measurement))
    }

    private def convertMeasurement(measurement: String): Option[Int] = measurement match {
        case "NaN" => None
        case measurement => Some(measurement.toInt)
    }
}
