# SOS Project 2017

This is a small concept of implementing a adaptive system using the [FESAS framework](http://fesas.bwl.uni-mannheim.de). We implemented a small use case of room with different sensors inside. Those sensors can detect different circumstances. With the help of those sensors our system adapts automatically according to some predefined rules.

## Pipeline of send events

Those send events are executing after sensor data was sent to the FESAS framework.

* logicElements.sensor.SocketIOSensor: _Send String_
* logicElements.monitor.Monitor: _Send JsonObject_
* logicElements.sensor.Analyzer: _Send JsonObject_
* logicElements.planner.Planner: _Send Action_
* logicElements.executor.Executor: _Send JsonObject_
* logicElements.effector.SocketIoEffector: _Send String_

## Assumptions

We built a client application which is able to send plain JSON strings to the FESAS framework. We built the system in away that it accepts the following base JSON scheme. (Notation of Typescript Interfaces and enums). To avoid confusion the `SENSOR_TYPE_LIGHT_LEVEL` refers to the light level on the outside of the building the room is in. THE `SENSOR_TYPE_LIGHT_BULB`` refers to the light bulbs inside the room.

```
interface SensorData{
    resourceId: string,
    sensorType: SensorType,
    data: object
}

enum SensorType {
    SENSOR_TYPE_LIGHT_LEVEL,
    SENSOR_TYPE_LIGHT_BULB,
    SENSOR_TYPE_WINDOW,
    SENSOR_TYPE_HUMIDITY,
    SENSOR_TYPE_SECURITY,
    SENSOR_TYPE_PERSON
}
```

## Rules File

In order to adapt to the current context the FESAS framework needs some rules. We wrote our rules to the `rules.json` file which has the following scheme:

```
enum ActionType {
    LIGHT_TURN_ON,
    LIGHT_TURN_OFF,
    WINDOW_OPEN,
    WINDOW_CLOSE,
    TURN_OFF_ALL,
    SECURITY_LEVEL_RISE,
    SECURITY_LEVEL_DROP,
    DO_NOTHING
}


interface Condition {
    name: string;
    eval: () => boolean;
    condition: () => boolean;
    action: ActionType
}

interface Rules {
    [key: SensorType]: {
        coniditons: [condition]    
    }
```
We used this file to make it convenient for end users to write rules without actually touching real source code.

Additionally we use a `application.properties` files, where users can specify simple properties
of the current context, like when does the day start/end or how many persons should be in a room.

```
day.end=18
day.start=6
person.maxCount=1
```

These file are used and evaluated in the `Analyzer` class to determine what action should be executed.
Therefore the `condition` function of a `condition` object is executed. This result determines if the action of this
object is allowed in the current context. If so, the `eval` function is executed. If this function returns `true` the `Analyzer` knows that 
the current system state is not in the `target space` and then the `Planner` is called to plan the action and the `Executor` then executes the action.

## Start the applications

0. Either use your IDE or the `.jars` located in `./out/artifacts` to start the two modules.
1. Start the `fesasSocketIo` application
2. Start the `SensorSocketClient` (connects to FESAS automatically)
3. Start sending fake JSON data from `SensorSocketClient.src.main.resources` to the FESAS and look what happens :)
3.1 inspect the `./fesasSocketIo/src/main/resources/rules.json` file, if you want to find out the different rules the system is evaluating when you send data to it.
