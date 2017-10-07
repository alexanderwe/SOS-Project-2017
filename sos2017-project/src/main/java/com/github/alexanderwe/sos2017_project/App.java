package com.github.alexanderwe.sos2017_project;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SensorSocket sensorSocket = new SensorSocket();
        sensorSocket.sendSensorData("This is a test");
    }
}
