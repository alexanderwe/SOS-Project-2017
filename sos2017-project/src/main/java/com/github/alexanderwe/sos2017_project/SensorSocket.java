package com.github.alexanderwe.sos2017_project;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SensorSocket {

    private final static String IP_ADDRESS = "127.0.0.1"; // Set to the IP of your machine

    private Socket socket;

    public SensorSocket(){
        try{
            socket = IO.socket("http://"+ IP_ADDRESS +":7777");
            socket.connect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendSensorData(String data){
        System.out.println("send data");
        socket.emit("sensorData", data);
    }

    public Socket getSocket(){
        return socket;
    }

}
