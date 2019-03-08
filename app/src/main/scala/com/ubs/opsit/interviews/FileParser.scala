package com.ubs.opsit.interviews

import scalaz.stream._
import scalaz.concurrent._

import scala.collection.mutable.ListMap
import scala.language.implicitConversions

object FileParser {
    type Sensor = Tuple2[String,SensorMeasurement]
    var collectedData = scala.collection.mutable.ListMap[String,SensorMeasurement]()

    def process(filepaths: List[String]): Unit = {
        parse(filepaths)
        present(filepaths.length)
    }

    private def parse(filepaths: List[String]): Unit = { 
        filepaths foreach parseFile
    }

    private def parseFile(filepath: String): Unit = {
        io.linesR(filepath)
          .drop(1)
          .map(row => toTuple(row.split(",")))
          .map{ case (sensor, measurement) => collectedData.getOrElseUpdate(sensor, new SensorMeasurement())
                                                           .add(measurement)
                                              GlobalMeasurement.add(measurement) }
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

    private def present(numberOfFiles: Int): Unit = {
        val sortedData = collectedData.toSeq.sortBy(_._2.avg).reverse
        println("Num of processed files: %s".format(numberOfFiles))
        println("Num of processed measurements: %s".format(GlobalMeasurement.allMeasurement))
        println("Num of failed measurements: %s\n".format(GlobalMeasurement.failedMeasurement))
        println("Sensors with highest avg humidity:\n")
        println("sensor-id,min,avg,max")
        sortedData foreach {
            case ((sensor, dataRow)) => 
                val (min, avg, max) = split(dataRow)
                println("%s,%s,%s,%s".format(sensor, min, avg, max))
        }
    }

    private def split(dataRow: SensorMeasurement): Tuple3[String, String, String] = dataRow.data match {
        case None => ("NaN","NaN","NaN")
        case Some((min,max,_,_)) => (min.toString, dataRow.avg.get.toString, max.toString)
    }
}
