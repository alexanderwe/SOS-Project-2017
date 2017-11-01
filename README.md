# SOS Project 2017


## Noteworthy stuff

**Pipeline:**

* logicElements.sensor.SocketIOSensor: _Send String_
* logicElements.monitor.Monitor: _Send JsonObject_
* logicElements.analyzer.Analyzer: _Send JsonObject_
* logicElements.sensor.Analyzer: _Send JsonObject_
* logicElements.planner.Planner: _Send JsonObject_
* logicElements.executor.Executor: _Send JsonObject_
* logicElements.effector.SocketIoEffector: _Send String_

Please keep in mind this is currently evaluating the framework and what is possible with it. The class names are very likely to be renamed and mock data to be added.


## Assumptions

We built a client application which is able to send plain JSON strings to the FESAS framework. We built the system in away that it accepts the following base JSON scheme.
Notation of Typescript Interfaces and enums

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

## Startup

1. Start the `fesasSocketIo` application
2. Start the `SensorSocketClient` (connects to FESAS automatically)
3. Start sending fake JSON data from `SensorSocketClient.src.main.resources` to the FESAS and look what happens :)
