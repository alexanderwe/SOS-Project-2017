import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Starts and setups UI
 */
public class Main extends Application {

    final static Logger logger = LogManager.getLogger(Main.class);

    //Static global variable for the controller (where MyController is the name of your controller class
    static MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set up instance instead of using static load() method
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Parent root = loader.load();

        //Now we have access to getController() through the instance... don't forget the type cast
        mainController = (MainController) loader.getController();

        primaryStage.setTitle("Sensor Socket Client");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }

    @Override
    public void stop() {
        logger.info("Closing stage");
        mainController.getSensorSocket().close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}