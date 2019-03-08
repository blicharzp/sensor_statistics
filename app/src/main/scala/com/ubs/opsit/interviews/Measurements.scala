package com.ubs.opsit.interviews

class SensorMeasurement {
    // [min, max, sum, numberOfElements], Tuple4 used instead of case class to avoid conversion
    type SensorData = Tuple4[Int,Int,Int,Int]
    var data: Option[SensorData] = None
    lazy val avg: Option[Double] = average

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
        }
    }

    private def average: Option[Double] = data match {
        case Some((_,_,sum, numberOfElements)) => Some(sum.toDouble / numberOfElements)
        case None => None
    }
}


object SensorMeasurement {
    implicit def ordering: Ordering[SensorMeasurement] = new Ordering[SensorMeasurement] {
        override def compare(first: SensorMeasurement, second: SensorMeasurement): Int = {
            compareAverage(first.avg, second.avg)
        }

        private def compareAverage(first: Option[Double], second: Option[Double]): Int = first match {
            case _ if first == None && second == None => 0
            case _ if first == None => -1
            case _ if second == None => 1
            case _ => cmp(first.get, second.get)
        }
        
        private def cmp(first: Double, second: Double): Int = {
            if(first >= second) {
                return 1
            }
            return -1
        }
    }
}


object GlobalMeasurement {
    var allMeasurement: Int = 0
    var failedMeasurement: Int = 0
    def add(measurement: Option[Int]): Unit = measurement match {
        case Some(_) => allMeasurement += 1
        case None => allMeasurement += 1
                     failedMeasurement += 1
    }
}
