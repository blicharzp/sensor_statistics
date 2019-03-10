package com.ubs.opsit.interviews

class SensorMeasurement {
    // [min, max, sum, numberOfElements], Tuple4 used instead of case class to avoid conversion
    type SensorData = Tuple4[Int,Int,Int,Int]
    var data: Option[SensorData] = None
    lazy val avg: Option[Float] = average

    def add(measurement: Option[Int]): Unit = measurement match {
        case Some(validMeasurement) => handle(validMeasurement)
        case None => Unit
    }

    private def handle(measurement: Int): Unit = data match {
        case Some(currentData) => compute(measurement, currentData)
        case None => data = Some(measurement, measurement, measurement, 1)
    }

    private def compute(measurement: Int, currentData: SensorData): Unit = {
        val (min, max, sum, numberOfElements) = currentData
        measurement match {
            case measurement if measurement >= max =>
                data = Some(min, measurement, sum + measurement, numberOfElements + 1)
            case measurement if measurement <= min =>
                data = Some(measurement, max, sum + measurement, numberOfElements + 1)
            case measurement =>
                data = Some(min, max, sum + measurement, numberOfElements + 1)
        }
    }

    private def average: Option[Float] = data match {
        case Some((_,_,sum, numberOfElements)) => Some(sum.toFloat / numberOfElements)
        case None => None
    }
}


object GlobalMeasurement {
    var allMeasurements: Int = 0
    var failedMeasurements: Int = 0
    def add(measurement: Option[Int]): Unit = measurement match {
        case Some(_) => allMeasurements += 1
        case None => allMeasurements += 1
                     failedMeasurements += 1
    }
}
