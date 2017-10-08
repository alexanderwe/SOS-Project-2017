package controller;

import com.cedarsoftware.util.io.JsonWriter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import socketIo.SensorSocket;

// TODO: check if clsing
public class MainController {

    final static Logger logger = LogManager.getLogger(MainController.class);

    public TextArea jsonTextArea;
    public Label errorLabel;
    private SensorSocket sensorSocket;

    public MainController() {
        this.sensorSocket = new SensorSocket("127.0.0.1", "7777");
    }

    public void sendJSON(ActionEvent actionEvent){
        errorLabel.setText("");
        errorLabel.setTextFill(Color.web("#000000"));
        try {
            JSONObject test = new JSONObject(jsonTextArea.getText());
           sensorSocket.sendSensorData(jsonTextArea.getText());
        } catch (JSONException jsone) {
            errorLabel.setText("Not a valid JSON string");
            errorLabel.setTextFill(Color.web("#FF0000"));
        }
    }

    public void prettyPrint() {
        errorLabel.setText("");
        errorLabel.setTextFill(Color.web("#000000"));
        try {
            JSONObject pretty = new JSONObject(jsonTextArea.getText());
            jsonTextArea.setText(JsonWriter.formatJson(pretty.toString()));
        } catch (JSONException jsone) {
            errorLabel.setText("Not a valid JSON string");
            errorLabel.setTextFill(Color.web("#FF0000"));
        }
    }

    public SensorSocket getSensorSocket() {
        return sensorSocket;
    }

    public void setSensorSocket(SensorSocket sensorSocket) {
        this.sensorSocket = sensorSocket;
    }
}

