package com.ubs.opsit.interviews

object Presenter {
    def present(measurements: Measurements): Unit = {
        println("Num of processed files: %s".format(measurements.fileNumber))
        println("Num of processed measurements: %s".format(measurements.globalMeasurement.allMeasurements))
        println("Num of failed measurements: %s\n".format(measurements.globalMeasurement.failedMeasurements))
        println("Sensors with highest avg humidity:\n")
        println("sensor-id,min,avg,max")
        measurements.sensors foreach {
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