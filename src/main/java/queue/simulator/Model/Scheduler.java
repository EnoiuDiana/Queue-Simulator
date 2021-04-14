package queue.simulator.Model;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private final List<Server> servers;
    private int noOfServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int noOfServers, int maxTasksPerServer) {
        this.servers = new ArrayList<>();
        this.noOfServers = noOfServers;
        this.maxTasksPerServer = maxTasksPerServer;
        for(int i = 0; i < noOfServers; i++) {
            Server newServer = new Server(i);
            servers.add(newServer);
            Thread newServerThread = new Thread(newServer);
            newServerThread.start();
        }
    }

    public void implementStrategy(SelectionPolicy policy) {
        if(policy == SelectionPolicy.SHORTEST_QUEUE) {
            //TODO implement
        }
        if(policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public int dispatchTask(Task t) {
        return strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }
}
