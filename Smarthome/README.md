[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Ihxtlzlu)
# SmartHome

Die Basisanwendung ist ein Smart-Home-System, das Sensoren verwaltet.

## Hauptkomponenten

* `SmartHome`: Interface, welches definiert, was eine SmartHome-Klasse unterstützen muss. Derzeit verwalten SmartHome-Klassen Sensoren des Hauses.
* `MyHome`: Konkrete SmartHome-Klasse, welche drei Temperatursensoren instanziiert und bereitstellt.
* `HomeApp`: Diese Hauptanwendungsklasse demonstriert, wie das Smart-Home-System verwendet wird. Es liest die Sensoren von `MyHome` aus und gibt die Messwerte auf der Konsole aus.
* Die Sensor-bezogenen Klassen (Interface `Sensor`, `BaseSensor`, `SensorData`, `SensorUnit`, `DataReader`) liegen im Package `sensor`.  
  Einziger bisher implementierter Sensor ist `TemperatureSensor`, welcher periodisch den Temperaturwert aktualisiert.
* Für das Observer-Pattern ist das Interface `SensorObserver` bereits definiert, es wird aber noch nicht verwendet.

![classdiagram-handout.svg](doc/classdiagram-handout.svg)

Im Package `io` befinden sich einige Klassen, die in den Input/Output-Aufgaben benötigt werden.

## Build-Tooling

Gradle ist konfiguriert, um das Projekt in die IDE laden zu können.
Mit `run` kann die Demoapplikation `HomeApp` gestartet werden.
Für Tests stellt Gradle JUnit und Mockito bereit.


