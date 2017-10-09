package controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.FileService;
import service.JsonService;
import socketIo.SensorSocket;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;


public class MainController {

    final static Logger logger = LogManager.getLogger(MainController.class);

    @FXML
    public TextArea jsonTextArea;
    @FXML
    public Label errorLabel;

    private SensorSocket sensorSocket;

    public MainController() {
        this.sensorSocket = new SensorSocket("127.0.0.1", "7777");
    }

    public void sendJSON(ActionEvent actionEvent) {
        errorLabel.setText("");
        errorLabel.setTextFill(Color.web("#000000"));
        try {
            JsonObject jsonObject = new JsonParser().parse(jsonTextArea.getText()).getAsJsonObject(); // To check if JSON is valid
            sensorSocket.sendSensorData(jsonTextArea.getText());
        } catch (JsonSyntaxException ilse) {
            logger.error("Not a valid JSON string");
            errorLabel.setText("Not a valid JSON string");
            errorLabel.setTextFill(Color.web("#FF0000"));
        } catch (IllegalStateException ilse) {
            logger.error("Not a valid JSON string");
            errorLabel.setText("Not a valid JSON string");
            errorLabel.setTextFill(Color.web("#FF0000"));
        } catch (ConnectException conex) {
            logger.error("Socket is not connected");
            errorLabel.setText("Socket is not connected to the FESAS system");
            errorLabel.setTextFill(Color.web("#FF0000"));
        }
    }

    public void prettyPrint() {
        errorLabel.setText("");
        errorLabel.setTextFill(Color.web("#000000"));
        try {
            jsonTextArea.setText(JsonService.toPrettyFormat(jsonTextArea.getText()));
        } catch (JsonSyntaxException ilse) {
            logger.error("Not a valid JSON string");
            errorLabel.setText("Not a valid JSON string");
            errorLabel.setTextFill(Color.web("#FF0000"));
        } catch (IllegalStateException ilse) {
            logger.error("Not a valid JSON string");
            errorLabel.setText("Not a valid JSON string");
            errorLabel.setTextFill(Color.web("#FF0000"));
        }
    }

    public void importJSON(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((MenuItem) actionEvent.getTarget()).getParentPopup().getScene().getWindow());
        try {
            String fileContent = FileService.readFile(file.getAbsolutePath());
            jsonTextArea.setText(JsonService.toPrettyFormat(fileContent));
        } catch (IOException ioe) {
            logger.error("Error reading imported file");
            errorLabel.setText("Error reading file");
            errorLabel.setTextFill(Color.web("#FF0000"));
        } catch (NullPointerException npe) {
            logger.warn("No file selected");
        }
    }

    public void closeApplication(ActionEvent actionEvent) {
        logger.info("Closing application");
        this.getSensorSocket().close();
        System.exit(0);
        Platform.exit();
    }

    public SensorSocket getSensorSocket() {
        return sensorSocket;
    }

    public void setSensorSocket(SensorSocket sensorSocket) {
        this.sensorSocket = sensorSocket;
    }
}

