package queue.simulator.Model;

public class Client {

    private final int arrivalTime;
    private final int serviceTime;
    private final int clientID;

    public Client(int arrivalTime, int serviceTime, int clientID) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.clientID = clientID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getClientID() {
        return clientID;
    }
}
