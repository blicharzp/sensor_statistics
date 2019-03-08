package com.ubs.opsit.interviews

class SensorMeasurement {
    var min: Option[Int] = None
    var max: Option[Int] = None
    var sum: Int = 0
    var elements: Option[Int] = None

    def avg: Option[Double] = {
        elements match {
            case Some(count) => Some(sum.toDouble / count)
            case None => None
        }
    }

    def add(measurement: Option[Int]): Unit = measurement match {
        case Some(value) => handle(value)
        case None => Unit
    }

    private def handle(value: Int): Unit = {
        sum += value
        val count = elements getOrElse(0) + 1
        if (count == 1) {
            computeForFirstElement(value)
        } else {
            compute(value)
        }
        elements = Some(count)
    }
    
    private def computeForFirstElement(value: Int): Unit = {
        min = Some(value)
        max = Some(value)
    }

    private def compute(value: Int): Unit = value match {
        case value if value >= max.get => max = Some(value)
        case value if value <= min.get => min = Some(value)
    }
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