package queue.simulator.Model;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    @Override
    public int addTask(List<Server> servers, Task t) {
        int minWaitingTime = servers.get(0).getWaitingPeriod().get();
        int minServerID = servers.get(0).getServerID();
        int waitingTime = 0;
        for(Server server : servers) {
            if(server.getWaitingPeriod().get() < minWaitingTime) {
                minWaitingTime = server.getWaitingPeriod().get();
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
