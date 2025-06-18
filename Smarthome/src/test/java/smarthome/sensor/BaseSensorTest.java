package ch.zhaw.it.prog2.smarthome.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test the {@link BaseSensor} class.
 * Are ids generated in strictly ascending order and are parameter tested on {@code null} values
 */
class BaseSensorTest {
    private Sensor sensor;

    /**
     * Create a subclass of the abstract {@link BaseSensor} class
     */
    @BeforeEach
    void setUp() {
        sensor = new BaseSensor("first sensor") {
            @Override
            public SensorData readValue() {
                return null;
            }
        };
    }

    @Test
    void constructorNoNullValue() {
        assertThrows(NullPointerException.class, () -> new BaseSensor(null) {
            @Override
            public SensorData readValue() {
                return null;
            }
        }, "sensor name cannot be null");
    }

    @Test
    void getId() {
        long firstId = sensor.getId();
        Assertions.assertTrue(firstId > 0, "sensor id must be greater than zero");
        sensor = new BaseSensor("second sensor") {
            @Override
            public SensorData readValue() {
                return null;
            }
        };
        long secondId = sensor.getId();
        long idDiff = secondId - firstId;
        Assertions.assertEquals(1, idDiff, "sensor id must be incremented by one.");
    }

}