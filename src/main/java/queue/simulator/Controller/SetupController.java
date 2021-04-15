package queue.simulator.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

import static queue.simulator.Model.SelectionPolicy.SHORTEST_TIME;

public class SetupController {

    @FXML
    private TextField noOfTasksTextField;
    @FXML
    private TextField minArrivalTimeTextField;
    @FXML
    private TextField maxArrivalTimeTextField;
    @FXML
    private TextField minServiceTimeTextField;
    @FXML
    private TextField maxServiceTimeTextField;

    @FXML
    private TextField noOfServersTextField;

    @FXML
    private TextField timeLimitTextField;

    SimulationManager simulationManager;

    public void initializeSimulation(ActionEvent event) {
        String noOfTasksStr = noOfTasksTextField.getText();
        String minArrivalTimeStr = minArrivalTimeTextField.getText();
        String maxArrivalTimeStr = maxArrivalTimeTextField.getText();
        String minServiceTimeStr = minServiceTimeTextField.getText();
        String maxServiceTimeStr = maxServiceTimeTextField.getText();
        String noOfServersStr = noOfServersTextField.getText();
        String timeLimitStr = timeLimitTextField.getText();

        try {
            int noOfTasks = Integer.parseInt(noOfTasksStr);
            int minArrivalTime = Integer.parseInt(minArrivalTimeStr);
            int maxArrivalTime = Integer.parseInt(maxArrivalTimeStr);
            int minServiceTime = Integer.parseInt(minServiceTimeStr);
            int maxServiceTime = Integer.parseInt(maxServiceTimeStr);
            int noOfServers= Integer.parseInt(noOfServersStr);
            int timeLimit= Integer.parseInt(timeLimitStr);
            try {
                simulationManager = new SimulationManager(timeLimit, noOfTasks, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, noOfServers, SHORTEST_TIME);
                loadSimulationScene(event);

            } catch (Exception ex) {
                System.out.println("Execution failed!");
                ex.printStackTrace();
            }

        } catch(Exception e) {
            System.out.println("Invalid data!");
        }

    }

    private void loadSimulationScene(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/queue/simulator/View/simulation.fxml"));
        Parent simulationSceneParent = fxmlLoader.load();
        Scene simulationScene = new Scene(simulationSceneParent);
        SimulationController simulationController = fxmlLoader.getController();
        simulationController.initData(simulationManager);
        simulationManager.getSimulationController(simulationController);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(simulationScene);
        window.show();

    }
}
