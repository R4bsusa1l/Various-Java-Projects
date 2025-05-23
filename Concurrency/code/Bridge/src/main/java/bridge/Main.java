package ch.zhaw.prog2.bridge;

public class Main {

    private static void nap(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] a) {
        final TrafficController controller = new TrafficController();

        final CarWorld world = new CarWorld(controller);
        final CarWindow win = new CarWindow(world);

        win.pack();
        win.setVisible(true);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    nap(25);
                    win.repaint();
                }
            }
        }).start();
    }
}
