package ch.zhaw.it.prog2.smarthome.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests that the {@link TemperatureSensor} is supplying some value
 */
class TemperatureSensorTest {

    @Test
    void readValueConstructorWithoutDataReader() {
        TemperatureSensor sensor = new TemperatureSensor("a sensor");
        SensorData sensorData = sensor.readValue();
        Assertions.assertTrue(sensorData.value() >= TemperatureSensor.MIN_VALUE, "value should be within the range of the sensor");
        Assertions.assertTrue(sensorData.value() <= TemperatureSensor.MAX_VALUE, "value should be within the range of the sensor");
    }

    @Test
    void readValueConstructorWithDataReader() {
        DataReader dataReader = mock(DataReader.class);
        double testvalue = 36.7;
        when(dataReader.readSensor()).thenReturn(testvalue);
        TemperatureSensor sensor = new TemperatureSensor("a sensor", dataReader);
        SensorData sensorData = sensor.readValue();
        assertEquals(testvalue, sensorData.value(), 0.001,
                "value should be the same as the data from the datarReader");

        dataReader = mock(DataReader.class);
        when(dataReader.readSensor())
                .thenReturn(TemperatureSensor.MIN_VALUE)
                .thenReturn(TemperatureSensor.MAX_VALUE);
        sensor = new TemperatureSensor("second sensor", dataReader);
        sensorData = sensor.readValue();
        assertEquals(TemperatureSensor.MIN_VALUE, sensorData.value(), 0.001,
                "minimum value should be acceptable");
        sensorData = sensor.readValue();
        assertEquals(TemperatureSensor.MAX_VALUE, sensorData.value(), 0.001,
                "maximum value should be acceptable");
    }
}