package queue.simulator.Model;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public int addTask(List<Server> servers, Task t) {
        int minTasksInServer = servers.get(0).getNumberOfTasksInQueue();
        int minServerID = servers.get(0).getServerID();
        int waitingTime = 0;
        for(Server server : servers) {
            if(server.getNumberOfTasksInQueue() < minTasksInServer) {
                minTasksInServer = server.getNumberOfTasksInQueue();
                minServerID = server.getServerID();
            }
        }

        for(Server server : servers) {
            if(server.getServerID() == minServerID) {
                waitingTime = server.addTask(t);
            }
        }
        return waitingTime;
    }
}
