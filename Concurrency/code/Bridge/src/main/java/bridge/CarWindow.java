package ch.zhaw.prog2.bridge;

import javax.swing.*;
import java.awt.*;

public class CarWindow extends JFrame {

    public CarWindow(CarWorld world) {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add("Center",world);
        JButton addLeft = new JButton("Add Left");
        JButton addRight = new JButton("Add Right");
        addLeft.addActionListener((event) -> world.addCar(Car.REDCAR));
        addRight.addActionListener((event) -> world.addCar(Car.BLUECAR));

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(addLeft);
        pane.add(addRight);
        container.add("South",pane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
