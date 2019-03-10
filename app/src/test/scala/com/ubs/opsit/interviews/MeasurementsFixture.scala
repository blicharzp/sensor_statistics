import org.scalatest.FunSpec
import org.scalactic.TolerantNumerics
import com.ubs.opsit.interviews.{GlobalMeasurement, SensorMeasurement, MeasurementConverter}

class MeasurementsFixture extends FunSpec {
    val epsilon = 1e-4f
    describe("A GlobalMeasurement") {
        it("should have reset counters at the beginning") {
            val globalMeasurement = new GlobalMeasurement()
            assert(globalMeasurement.allMeasurements == 0)
            assert(globalMeasurement.failedMeasurements == 0)
        }

        it("should increment both failedMeasurements for None") {
            val globalMeasurement = new GlobalMeasurement()
            globalMeasurement.add(None)
            assert(globalMeasurement.allMeasurements == 1)
            assert(globalMeasurement.failedMeasurements == 1)
        }

        it("should increment only allMeasurements for valid imput") {
            val globalMeasurement = new GlobalMeasurement()
            globalMeasurement.add(Some(42))
            assert(globalMeasurement.allMeasurements == 1)
            assert(globalMeasurement.failedMeasurements == 0)
        }
    }

    describe("A SensorMeasurement") {
        it("should have None data at the beginning") {
            val sensorMeasurement = new SensorMeasurement()
            assert(sensorMeasurement.data == None)
        }

        it("should have None avg for None data") {
            val sensorMeasurement = new SensorMeasurement()
            assert(sensorMeasurement.data == None)
            assert(sensorMeasurement.avg == None)
        }

        it("should ignore not valid imput") {
            val sensorMeasurement = new SensorMeasurement()
            sensorMeasurement.add(None)
            assert(sensorMeasurement.data == None)
            assert(sensorMeasurement.avg == None)
        }

        it("should have same min, max, sum and numberOfElements == 1 for one valid imput") {
            val sensorMeasurement = new SensorMeasurement()
            val input = 42
            sensorMeasurement.add(Some(input))
            val (min, max, sum, numberOfElements) = sensorMeasurement.data.get
            assert(min == input)
            assert(max == input)
            assert(sum == input)
            assert(numberOfElements == 1)
        }

        it("should select correct bounds for multiple valid input") {
            val sensorMeasurement = new SensorMeasurement()
            val input1 = 1
            val input2 = 2
            sensorMeasurement.add(Some(input1))
            sensorMeasurement.add(Some(input2))
            val (min, max, sum, numberOfElements) = sensorMeasurement.data.get
            assert(min == input1)
            assert(max == input2)
            assert(sum == input1 + input2)
            assert(numberOfElements == 2)
        }

        it("should select correct bounds for same multiple valid input") {
            val sensorMeasurement = new SensorMeasurement()
            val input1 = 1
            val input2 = 1
            sensorMeasurement.add(Some(input1))
            sensorMeasurement.add(Some(input2))
            val (min, max, sum, numberOfElements) = sensorMeasurement.data.get
            assert(min == input1)
            assert(max == input1)
            assert(sum == input1 + input2)
            assert(numberOfElements == 2)
        }

        it("should have avg === imput for one valid imput") {
            val sensorMeasurement = new SensorMeasurement()
            val input = 42
            sensorMeasurement.add(Some(input))
            assert(sensorMeasurement.avg.get === input.toFloat)
        }

        it("should compute correct avg for multiple valid input") {
            val sensorMeasurement = new SensorMeasurement()
            val input1 = 1
            val input2 = 2
            sensorMeasurement.add(Some(input1))
            sensorMeasurement.add(Some(input2))
            assert(sensorMeasurement.avg.get === (input1.toFloat + input2.toFloat) / 2.0)
        }
    }

    describe("A MeasurementConverter") {
        it("should convert sensor with NaN to None humidity") {
            val inputSensor = "s42"
            val inputHumidity = "NaN"
            val nanValueSensor = Array(inputSensor, inputHumidity)
            val (sensor, humidity) = MeasurementConverter.convert(nanValueSensor)
            assert(sensor == inputSensor)
            assert(humidity == None)
        }

        it("should convert sensor with non NaN to valid humidity") {
            val inputSensor = "s42"
            val inputHumidity = "2"
            val nanValueSensor = Array(inputSensor, inputHumidity)
            val (sensor, humidity) = MeasurementConverter.convert(nanValueSensor)
            assert(sensor == inputSensor)
            assert(humidity == Some(inputHumidity.toInt))
        }
    }
}