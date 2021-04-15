package queue.simulator.Controller;

import javafx.application.Platform;
import queue.simulator.Model.*;

import java.util.Iterator;
import java.util.List;

public class SimulationManager implements Runnable{
    int timeLimit;
    int noOfTasks;
    int minArrivalTime ;
    int maxArrivalTime;
    int minServiceTime;
    int maxServiceTime;
    int noOfServers;
    private Scheduler scheduler;
    private final List<Task> generatedTasks;
    public SelectionPolicy selectionPolicy;
    private SimulationController simulationController;

    public SimulationManager(int timeLimit, int noOfTasks, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int noOfServers, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.noOfTasks = noOfTasks;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.noOfServers = noOfServers;
        GenerateTasks.generateNTasks(noOfTasks, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime);
        this.generatedTasks = GenerateTasks.getTasks();
        this.selectionPolicy = selectionPolicy;
    }

    public void getSimulationController(SimulationController simulationController) {
        this.simulationController = simulationController;
    }
    private void startServers() {
        this.scheduler = new Scheduler(noOfServers);
        scheduler.implementStrategy(selectionPolicy);
    }

    @Override
    public void run() {
        int maxTasksPerHour = 0;
        int peakHour = 0;
        float avgWaitingTime = 0;
        float averageServiceTime = computeAverageServiceTime();
        startServers();
        int currentTime = 0;
        while(currentTime < timeLimit) {
            String time = (Integer.toString(currentTime));
            for (Iterator<Task> taskIterator = generatedTasks.iterator(); taskIterator.hasNext();) {
                Task task = taskIterator.next();
                if(task.getArrivalTime() == currentTime) {
                    int waitingTimeForEachTask = scheduler.dispatchTask(task);
                    avgWaitingTime = avgWaitingTime + (float) waitingTimeForEachTask;
                    taskIterator.remove();
                }
            }
            computeStrings(time);
            int tasksAtTheMoment = helpComputePeakHour();
            if(maxTasksPerHour < tasksAtTheMoment) {
                maxTasksPerHour = tasksAtTheMoment;
                peakHour = currentTime;
            }
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        avgWaitingTime = avgWaitingTime/noOfTasks;
        float finalAvgWaitingTime = avgWaitingTime;
        int finalPeakHour = peakHour;
        Platform.runLater(()->this.simulationController.printResults(finalAvgWaitingTime, averageServiceTime, finalPeakHour));
        for(Server server: scheduler.getServers()) {
            server.stopServer();
        }

    }

    private void computeStrings(String time) {
        StringBuilder waitingQueueForSim = new StringBuilder();
        StringBuilder waitingQueue = new StringBuilder();
        for(Task task : generatedTasks) {
            waitingQueueForSim.append("(").append(task.getTaskID()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append(");\n");
            waitingQueue.append("(").append(task.getTaskID()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("); ");
        }
        String waitingQueueForSimStr = waitingQueueForSim.toString();
        String waitingQueueStr = waitingQueue.toString();

        StringBuilder queuesForSim = new StringBuilder();
        StringBuilder queues = new StringBuilder();
        for(Server server: scheduler.getServers()) {
            queues.append("Queue ").append(server.getServerID()).append(": ");
            queuesForSim.append("Queue ").append(server.getServerID()).append(": ");
            for(Task task : server.getTasks()) {
                queues.append("(").append(task.getTaskID()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("); ");
                queuesForSim.append("(").append(task.getTaskID()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("); ");
            }
            queues.append("\n");
            queuesForSim.append("\n\n");
        }
        String queuesStr = queues.toString();
        String queuesForSimStr = queuesForSim.toString();
        Platform.runLater(()-> updateGUI(time, waitingQueueStr, queuesStr, waitingQueueForSimStr, queuesForSimStr));
    }

    private float computeAverageServiceTime () {
        float averageServiceTime = 0;
        for (Task task : generatedTasks) {
            averageServiceTime = averageServiceTime + task.getServiceTime();
        }
        averageServiceTime = averageServiceTime / noOfTasks;
        return averageServiceTime;
    }

    private int helpComputePeakHour() {
        int tasksAtTheMoment = 0;
        for(Server server : scheduler.getServers()) {
            tasksAtTheMoment = tasksAtTheMoment + server.getNumberOfTasksInQueue();
        }
        return tasksAtTheMoment;
    }

    private void updateGUI(String time, String waitingQueue, String queues, String waitingQueueForSim, String queuesForSim) {
        this.simulationController.printSimulationUpdates(time, waitingQueue, queues, waitingQueueForSim, queuesForSim);
    }
}
