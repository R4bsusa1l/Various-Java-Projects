package ch.zhaw.prog2.bridge;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class CarWorld extends JPanel {

    final Image bridge;
    final Image redCar;
    final Image blueCar;

    final TrafficController controller;

    final ArrayList<Car> blueCars = new ArrayList<>();
    final ArrayList<Car> redCars = new ArrayList<>();

    public CarWorld(TrafficController controller) {
        this.controller = controller;
        MediaTracker mediaTracker = new MediaTracker(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        redCar = toolkit.getImage(getClass().getResource("redcar.gif"));
        mediaTracker.addImage(redCar, 0);
        blueCar = toolkit.getImage(getClass().getResource("bluecar.gif"));
        mediaTracker.addImage(blueCar, 1);
        bridge = toolkit.getImage(getClass().getResource("bridge1.gif"));
        mediaTracker.addImage(bridge, 2);

        try {
            mediaTracker.waitForID(0);
            mediaTracker.waitForID(1);
            mediaTracker.waitForID(2);
        } catch (java.lang.InterruptedException e) {
            System.out.println("Couldn't load one of the images");
        }

        redCars.add(new Car(Car.REDCAR, null, redCar, null));
        blueCars.add(new Car(Car.BLUECAR, null, blueCar, null));
        setPreferredSize(new Dimension(bridge.getWidth(null), bridge.getHeight(null)));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(bridge, 0, 0, this);
        for (Car car : redCars) car.draw(graphics);
        for (Car car : blueCars) car.draw(graphics);
    }

    public void addCar(final int cartype) {
        SwingUtilities.invokeLater(() -> {
            Car car;
            if (cartype == Car.REDCAR) {
                car = new Car(cartype, redCars.getLast(), redCar, controller);
                redCars.add(car);
            } else {
                car = new Car(cartype, blueCars.getLast(), blueCar, controller);
                blueCars.add(car);
            }
            new Thread(car).start();
        });
    }

}
