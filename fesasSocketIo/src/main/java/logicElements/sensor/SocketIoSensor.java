package logicElements.sensor;

import com.corundumstudio.socketio.SocketIOServer;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.AbstractLogic;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.LogicType;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.ISensorLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationCategory;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationType;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.IKnowledgeRecord;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;
import dependencies.SocketManager;
import logicElements.knowledge.ContextWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;

/**
 * Description from meta data:
 * <p>
 * This file has been generated by the FESAS development tool set.
 *
 * @author FESAS
 */
public class SocketIoSensor extends AbstractLogic implements ISensorLogic {

    final static Logger logger = LogManager.getLogger(SocketIoSensor.class);

    public SocketIoSensor() {
        super();
        supportedInformationTypes.add(InformationType.Probe_SIMPLE_MANAGED_RESOURCES);

        this.informationType = InformationType.Sensor_DEFAULT;
        type = LogicType.SENSOR;
        shortName = "SocketIoSensor";
    }

    // do not change anything above this line (except of adding import statements)

    //add variables here
    private SocketIOServer server;
    private ContextWrapper contextWrapper;

    @Override
    public void initializeLogic(HashMap<String, String> properties) {

        // Set up the socket io sever for communication with the client
        server = SocketManager.getInstance().getServer();
        setupSocket();
    }

    @Override
    public String callLogic(IKnowledgeRecord data) {
        if (data instanceof KnowledgeRecord) {
            if (data.getData() instanceof String) {

                // Just pipe the data to the Monitor
                this.sendData(data.getData());
            }
            return "Not the expected data type! It is: " + data.getData().getClass().getSimpleName();
        }
        return "Not a KnowledgeRecord! It is: " + data.getClass().getSimpleName();
    }

    // Set up the socket where clients can connect to
    private void setupSocket() {
        final SocketIoSensor sensorInstance = this;
        server.addEventListener("sensorData", String.class, (client, data, ackRequest) -> {
            KnowledgeRecord record = new KnowledgeRecord(data, InformationType.Probe_SIMPLE_MANAGED_RESOURCES.toString(), InformationCategory.SENSOR.toString(), "fesasID-000_1_007", new Date().getTime());
            sensorInstance.callLogic(record);
        });

        // Clients can shutdown the server
        // In a production environment you may drop this option
        server.addEventListener("shutdown", String.class, (client, data, ackRequest) -> {
            logger.info("Server will stop");
            System.exit(1);
            server.stop();
            logger.info("Server stopped");

        });


        server.start();
        logger.info("Server started");

    }

    // add further methods if needed

}
