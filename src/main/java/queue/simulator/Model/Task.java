package queue.simulator.Model;

public class Task {

    private final int arrivalTime;
    private int serviceTime;
    private final int taskID;
    private int waitingTime;

    public Task(int clientID, int arrivalTime, int serviceTime ) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.taskID = clientID;
        this.waitingTime = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
