package queue.simulator.Model;

import java.util.List;

public interface Strategy {
    int addTask(List<Server> servers, Task t);
}
