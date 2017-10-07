package dependencies;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class SocketManager {

	private SocketIOServer server;

	private static final SocketManager instance = new SocketManager();

	private SocketManager() {
		Configuration config = new Configuration();
        config.setHostname("localhost"); // your external hostname
        config.setPort(7777);

        server = new SocketIOServer(config);
	}

	public static SocketManager getInstance() {
		return instance;
	}
	
	public SocketIOServer getServer(){
		return server;
	}

}
