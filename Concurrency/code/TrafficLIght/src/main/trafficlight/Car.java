package ch.zhaw.prog2.trafficlight;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Car extends Thread {
    private static final int MIN_PASSBY_TIME = 200;
    private static final int MAX_PASSBY_TIME = 800;
    private final TrafficLight[] trafficLights;
    private int position;

    public Car(String name, TrafficLight[] trafficLights) {
        super(name);
        this.trafficLights = trafficLights;
        position = 0; // start at first light
        start();
    }

    public synchronized int position() {
        return position;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            trafficLights[position].passby();
            driveThrough(random.nextInt(MIN_PASSBY_TIME, MAX_PASSBY_TIME));
            gotoNextLight();
        }
    }

    /**
     * Move position to the next traffic light
     */
    private void gotoNextLight() {
        position = ++position % trafficLights.length;
    }

    /**
     * Simulate the time it takes to drive through the intersection of the traffic light
     * @param passbyTime time in milliseconds to drive through
     */
    private void driveThrough(int passbyTime) {
        try {
            TimeUnit.MILLISECONDS.sleep(passbyTime);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
