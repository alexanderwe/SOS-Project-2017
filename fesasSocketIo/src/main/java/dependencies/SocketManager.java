package dependencies;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.google.gson.Gson;
import logicElements.knowledge.ContextWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for handling the SocketIO stuff
 */
public class SocketManager {

    private SocketIOServer server;
    final static Logger logger = LogManager.getLogger(SocketManager.class);

    private static final SocketManager instance = new SocketManager();

    private SocketManager() {
        Configuration config = new Configuration();

        config.setHostname("localhost"); // your external hostname
        config.setPort(7777);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        server = new SocketIOServer(config);

        // On client connect send the current state to the client to display
        server.addConnectListener(socketIOClient -> {
            logger.info("Client connected, send current context");
            socketIOClient.sendEvent("contextData", (new Gson()).toJson(ContextWrapper.getInstance().getContext()));
        });
    }

    public static SocketManager getInstance() {
        return instance;
    }

    public SocketIOServer getServer() {
        return server;
    }

}
