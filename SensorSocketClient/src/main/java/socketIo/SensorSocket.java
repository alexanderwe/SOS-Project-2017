package socketIo;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SensorSocket {

    final static Logger logger = LogManager.getLogger(SensorSocket.class);

    private Socket socket;
    private String socketUrl;

    public SensorSocket(String ipAddress, String port){
        this.socketUrl = "http://"+ ipAddress +":"+port;
        try{
            logger.info("Connect to " + this.socketUrl);
            socket = IO.socket(this.socketUrl);
            socket.connect();
            logger.info(socket.connected() ? "Connected to " + this.socketUrl : "Could not connect to " + this.socketUrl);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendSensorData(String data){
        logger.info("Send sensor data to " + this.socketUrl);
        socket.emit("sensorData", data);
    }

    public void close() {
        logger.info("Disconnecting socket");
        socket.disconnect();
        logger.info("Socket disconnected");
    }

    public Socket getSocket(){
        return socket;
    }

}
