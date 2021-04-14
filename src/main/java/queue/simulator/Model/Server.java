package queue.simulator.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;
    private final int serverID;
    private boolean isRunning;

    public Server(int ID) {
        tasks = new LinkedBlockingQueue<Task>();
        waitingPeriod = new AtomicInteger(0);
        serverID = ID;
        isRunning = true;
    }

    public int addTask(Task newTask) {
        newTask.setWaitingTime(this.waitingPeriod.get() + newTask.getServiceTime() + newTask.getArrivalTime());
        tasks.add(newTask);
        waitingPeriod.getAndAdd(newTask.getServiceTime());
        return waitingPeriod.get();
    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                Task currentTask = tasks.peek();
                if(currentTask != null) {
                    if (currentTask.getServiceTime() == 1) {
                        tasks.remove(currentTask);
                    } else {
                        currentTask.setServiceTime(currentTask.getServiceTime() - 1);
                        waitingPeriod.getAndDecrement();
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getServerID() {
        return serverID;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public int getNumberOfTasksInQueue() {
        return tasks.size();
    }

    public void stopServer() {
        isRunning = false;
    }
}
