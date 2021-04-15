package queue.simulator.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class SimulationController {
    @FXML
    public Button start;

    @FXML
    public Label time;
    @FXML
    public Label waitingQueue;
    @FXML
    public Label queues;

    @FXML
    public Label avgWaiting;
    @FXML
    public Label avgService;
    @FXML
    public Label peakHour;

    SimulationManager simulationManager;

    public void initData(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }

    public void startSimulation() {
        Thread simulationManagerThread = new Thread(simulationManager);
        simulationManagerThread.start();
    }



    public void printSimulationUpdates(String time, String waitingQueue, String queues, String waitingQueueForSim, String queuesForSim) {
        this.time.setText(time);
        this.waitingQueue.setText(waitingQueueForSim);
        this.queues.setText(queuesForSim);

        try(FileWriter fw = new FileWriter("test.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("Time " + time);
            out.println("Waiting queue: " + waitingQueue);
            out.println(queues);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void printResults(float averageWaitingTime, float averageServiceTime, int peakHour) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        this.avgWaiting.setText(df.format(averageWaitingTime));
        this.avgService.setText(df.format(averageServiceTime));
        this.peakHour.setText(Integer.toString(peakHour));

        try(FileWriter fw = new FileWriter("test.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("Average waiting time: " + df.format(averageWaitingTime) + "\n");
            out.println("Average service time: " + df.format(averageServiceTime) + "\n");
            out.println("Peak hour: " + peakHour + "\n");
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
