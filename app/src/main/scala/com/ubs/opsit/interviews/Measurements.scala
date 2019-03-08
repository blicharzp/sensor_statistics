package com.ubs.opsit.interviews

class SensorMeasurement {
    var min: Int = 0
    var max: Int = 0
    var sum: Int = 0
    var elements: Int = 0

    def avg: Double = sum.toDouble / elements
}


object GlobalMeasurement {
    var allMeasurement: Int = 0
    var failedMeasurement: Int = 0
    def add(measurement: Option[Int]): Unit = measurement match {
        case Some(_) => allMeasurement+= 1
        case None => allMeasurement+= 1
                     failedMeasurement+= 1
    }
}