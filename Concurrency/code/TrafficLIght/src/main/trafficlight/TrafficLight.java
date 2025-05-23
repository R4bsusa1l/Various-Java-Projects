package ch.zhaw.prog2.trafficlight;

class TrafficLight {
    private boolean red;

    public TrafficLight() {
        red = true;
    }

    public synchronized void passby() {
        while (red) {
            try {
                wait();
            } catch (InterruptedException logOrIgnore) {
                System.out.println(logOrIgnore.getMessage());
            }
        }
    }

    public synchronized void switchToRed() {
        this.red = true;

    }

    public synchronized void switchToGreen() {
        this.red = false;
        notifyAll();
    }
}
