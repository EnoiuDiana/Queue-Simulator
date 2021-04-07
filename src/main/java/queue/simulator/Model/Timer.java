package queue.simulator.Model;

public class Timer implements Runnable {

    private final Thread timeThread;
    private int second;
    private final int endTime;

    public Timer(int endTime) {
        this.timeThread = new Thread(this);
        this.second = 0;
        this.endTime = endTime;
    }

    public void start() {
        second = 0;
        timeThread.start();
    }

    @Override
    public void run() {
        while (second != endTime + 1) {
            System.out.println(second);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second++;
        }
    }
}
