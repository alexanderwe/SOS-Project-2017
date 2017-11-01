package controller;


import com.google.gson.JsonElement;
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
import knowledge.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.FileService;
import service.JsonService;
import socketIo.SensorSocket;
import socketIo.SocketEventListener;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;


public class MainController implements SocketEventListener{

    final static Logger logger = LogManager.getLogger(MainController.class);

    @FXML
    public TextArea jsonTextArea;
    @FXML
    public TextArea fesasResultTextArea;
    @FXML
    public TextArea fesasContextTextArea;
    @FXML
    public TextArea lastSentData;
    @FXML
    public Label errorLabelText;
    @FXML
    public Label infoLabelText;

    private SensorSocket sensorSocket;

    public MainController(){
        this.sensorSocket = new SensorSocket("127.0.0.1", "7777");
        this.sensorSocket.addListener(this);
    }

    @FXML
    public void initialize() {
    }

    public void sendJSON(ActionEvent actionEvent) {
        errorLabelText.setText("");
        this.fesasResultTextArea.setText("");
        errorLabelText.setTextFill(Color.web("#000000"));
        try {
            JsonObject jsonObject = new JsonParser().parse(jsonTextArea.getText()).getAsJsonObject(); // To check if JSON is valid
            sensorSocket.sendMessage("sensorData", jsonTextArea.getText());
        } catch (JsonSyntaxException ilse) {
            logger.error("Not a valid JSON string");
            errorLabelText.setText("Not a valid JSON string");
            errorLabelText.setTextFill(Color.web("#FF0000"));
        } catch (IllegalStateException ilse) {
            logger.error("Not a valid JSON string");
            errorLabelText.setText("Not a valid JSON string");
            errorLabelText.setTextFill(Color.web("#FF0000"));
        } catch (ConnectException conex) {
            logger.error("Socket is not connected");
            errorLabelText.setText("Socket is not connected to the FESAS system");
            errorLabelText.setTextFill(Color.web("#FF0000"));
        }
    }

    public void prettyPrint() {
        errorLabelText.setText("");
        errorLabelText.setTextFill(Color.web("#000000"));
        try {
            jsonTextArea.setText(JsonService.toPrettyFormat(jsonTextArea.getText()));
        } catch (JsonSyntaxException ilse) {
            logger.error("Not a valid JSON string");
            errorLabelText.setText("Not a valid JSON string");
            errorLabelText.setTextFill(Color.web("#FF0000"));
        } catch (IllegalStateException ilse) {
            logger.error("Not a valid JSON string");
            errorLabelText.setText("Not a valid JSON string");
            errorLabelText.setTextFill(Color.web("#FF0000"));
        }
    }

    public void shutDownServer() throws IOException{
        sensorSocket.sendMessage("shutdown", true);
    }

    public void connectToServer(){
        sensorSocket.connect();
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
            errorLabelText.setText("Error reading file");
            errorLabelText.setTextFill(Color.web("#FF0000"));
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

    @Override
    public void socketConnected() {
        Platform.runLater(() -> {
            infoLabelText.setText("Connected to FESAS");
            infoLabelText.setTextFill(Color.web("#000000"));
        });
    }

    @Override
    public void socketDisconnected() {
        Platform.runLater(() -> {
            infoLabelText.setText("Socket is not connected to the FESAS system");
            infoLabelText.setTextFill(Color.web("#FF0000"));
        });
    }


    @Override
    public void retrievedEffectorData(Object... data) {
        Platform.runLater(() -> {
            this.fesasResultTextArea.setText(JsonService.toPrettyFormat(String.valueOf(data[0])));

            JsonElement retrievedData = new JsonParser().parse(String.valueOf(data[0]));
            ActionType actionType = ActionType.byValue(retrievedData.getAsJsonObject().get("actionType").getAsString());
            switch (actionType) {
                case WINDOW_CLOSE:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_WINDOW\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"closed\": true\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    break;
                case WINDOW_OPEN:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_WINDOW\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"closed\": false\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    break;
                case LIGHT_TURN_ON:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_LIGHT_BULB\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"on\": true\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    break;
                case LIGHT_TURN_OFF:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_LIGHT_BULB\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"on\": false\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    break;
                case SECURITY_LEVEL_RISE:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_SECURITY\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"level\": 2\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    break;
                case SECURITY_LEVEL_DROP:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_SECURITY\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"level\": 1\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    break;
                case TURN_OFF_ALL:
                    try {
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_LIGHT_BULB\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"on\": false\n" +
                                "  }\n" +
                                "}");
                        this.sensorSocket.sendMessage("sensorData", "{\n" +
                                "  \"sensorType\": \"SENSOR_TYPE_WINDOW\",\n" +
                                "  \"resourceId\": \"1aadjadj\",\n" +
                                "  \"data\": {\n" +
                                "    \"closed\": true\n" +
                                "  }\n" +
                                "}");
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                default: break;
            }


        });
    }


    @Override
    public void retrievedContextData(Object... data) {
        Platform.runLater(() -> {
            this.fesasContextTextArea.setText(JsonService.toPrettyFormat(String.valueOf(data[0])));
        });
    }

    @Override
    public void socketHasSentData(Object data) {
        Platform.runLater(() -> {
            this.lastSentData.setText(JsonService.toPrettyFormat(String.valueOf(data)));
        });
    }
}

