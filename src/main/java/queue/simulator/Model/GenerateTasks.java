package queue.simulator.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GenerateTasks {
    private static final List<Task> TASKS = new ArrayList<>();

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void generateNTasks(int N, int minArrivalTime, int maxArrivalTime,
                                      int minServiceTime, int maxServiceTime) {
        TASKS.clear();
        for ( int i = 1; i <= N; i++) {
            int clientArrivalTime = getRandomNumber(minArrivalTime, maxArrivalTime);
            int clientServiceTime = getRandomNumber(minServiceTime, maxServiceTime);

            TASKS.add(new Task(i, clientArrivalTime, clientServiceTime));
        }
        TASKS.sort(Comparator.comparingInt(Task::getArrivalTime));
    }

    public static List<Task> getTasks() {
        return TASKS;
    }
}
