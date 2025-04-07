package ch.zhaw.prog2.trafficlight;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TrafficLightOperation {

    private static final int NUMBER_OF_LIGHTS = 7;
    private static final int NUMBER_OF_CARS = 20;
    private static final int GREEN_PHASE_TIME = 500;
    private static final int RED_PHASE_TIME = 1000;
    private static volatile boolean running = true;

    public static void terminate () {
        running = false;
    }

    public static void main(String[] args) {
        // initialize traffic lights
        TrafficLight[] trafficLights = new TrafficLight[NUMBER_OF_LIGHTS];
        for (int position = 0; position < NUMBER_OF_LIGHTS; position++) {
            trafficLights[position] = new TrafficLight();
        }
        // initialize cars
        Car[] cars = new Car[NUMBER_OF_CARS];
        for (int id = 0; id < NUMBER_OF_CARS; id++) {
            cars[id] = new Car("" + id, trafficLights);
        }

        // Simulation
        while (running) {
            for (int greenIndex = 0; greenIndex < trafficLights.length; greenIndex += 2) {
                displayState(trafficLights, greenIndex, cars);
                nap(2000);
                greenPhase(trafficLights, greenIndex);
                redPhase(trafficLights, greenIndex);
            }
        }
    }

    private static void redPhase(TrafficLight[] trafficLights, int greenIndex) {
        trafficLights[greenIndex].switchToRed();
        trafficLights[nextLight(greenIndex)].switchToRed();
        nap(RED_PHASE_TIME);
    }

    private static void greenPhase(TrafficLight[] trafficLights, int greenIndex) {
        trafficLights[greenIndex].switchToGreen();
        trafficLights[nextLight(greenIndex)].switchToGreen();
        nap(GREEN_PHASE_TIME);
    }

    private static void displayState(TrafficLight[] trafficLights, int greenIndex, Car[] cars) {
        System.out.println("=====================================================");
        for (int lightIndex = 0; lightIndex < trafficLights.length; lightIndex++) {
            String lightState = (lightIndex == greenIndex || lightIndex == nextLight(greenIndex)) ? "🟢" : "🔴";
            int currentLight = lightIndex;
            String carsAtLight = Arrays.stream(cars)
                .filter(car -> car.position() == currentLight)
                .map(Car::getName)
                .collect(Collectors.joining(" "));
            System.out.printf("Light%2d |%s| %s%n", lightIndex, lightState, carsAtLight);
        }
        System.out.println("=====================================================");
    }

    private static int nextLight(int lightIndex) {
        return (lightIndex + 1) % NUMBER_OF_LIGHTS;
    }

    private static void nap(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException logOrIgnore) {
            System.out.println(logOrIgnore.getMessage());
        }
    }
}
