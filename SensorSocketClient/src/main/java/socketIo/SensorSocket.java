package socketIo;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ConnectException;

public class SensorSocket {

    final static Logger logger = LogManager.getLogger(SensorSocket.class);

    private Socket socket;
    private String socketUrl;
    private boolean connected;

    public SensorSocket(String ipAddress, String port) {
        this.socketUrl = "http://" + ipAddress + ":" + port;
        this.connected = false;
        try {
            logger.info("Connect to " + this.socketUrl);
            socket = IO.socket(this.socketUrl);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, args -> {
                logger.info("Connected to " + this.socketUrl);
                this.connected = true;
            }).on(Socket.EVENT_DISCONNECT, args -> {
                logger.info("Disconnected from " + this.socketUrl);
                this.connected = false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String key, Object data) throws ConnectException {
        if (this.connected) {
            logger.info("Send " + key +" with " + data + " to " + this.socketUrl);
            socket.emit(key, data);
        } else {
            throw new ConnectException();
        }
    }

    public void connect(){
        if (!this.connected) {
            logger.info("Try to connect to " + this.socketUrl);
            socket.connect();
        }
    }

    public void close() {
        logger.info("Disconnecting socket");
        socket.disconnect();
        logger.info("Socket disconnected");
    }

    public Socket getSocket() {
        return socket;
    }

}
