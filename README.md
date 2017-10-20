# SOS Project 2017

Under heavy WIP

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
    SENSOR.TYPE_LIGHT,
    SENSOR.TYPE_PERSON,
}


```