# Repository Contents
This repository contains an example Java Agent with support for Raspberry Pi, Linux, Windows and Mac systems. 
It consists of the following modules: 

 * jv-driver: Interface classes for writing hardware drivers and implementing new functionality.
 * jv-agent: The main executable agent including basic device management, should work on all Java platforms.
 * rpi-driver: Hardware driver for the Raspberry Pi.
 * **parking-pi-driver : driver for c8y parkingpi.**
 * kontron-driver: Hardware driver for 
 * mac-driver: Hardware driver for Mac OS X systems.
 * generic-linux-driver: Hardware driver for linux systems lacking /proc/cpuinfo. It uses the MAC address to register the device.
 * win-driver: Hardware driver for Windows systems.
 * piface-driver: A simple Piface integration.
 * tinkerforge-driver: Support for Tinkerforge bricks. 
 * assembly: Base packaging for all environments.
 * packages: Environment specific packaging.
 
 # Pre Process
  At First you have to remove the Piface Driver and copy the ParkingPiDriver to RaspberryPi after Installing The Cumulocity Latest Rpi Agent to do this Run the following commands as sudo in the Terminal window 
  
  ```
  rm /usr/share/cumulocity-rpi-agent/lib/<<pifacedriver.jar Exact filename as it is>>
  cp <<ParkingPiDriver.jar Location>> /usr/share/cumulocity-rpi-agent/lib/
  cp <<json-simple-1.1.1.jar Location>> /usr/share/cumulocity-rpi-agent/lib/
  ```
 And Restart the agent.
 
# Parking-pi-driver
  ***ParkingPiDriver.java***
  * To Run this Driver Apart from the above files You have add Google Simple Json dependency jar to the POM.XML.
  * The main Java class is parking-pi-driver\src\main\java\com\softwareag\parkingpi\ParkingPiDriver.java which updates the parking pi's name, position and parking pi's Status which is not set by the c8y raspberry-pi agent(sets only for the first time of running from the
  * ParkingPi.json File which has been at the location /home/pi/Desktop/c8y/).
  * It also creates the ChildDevices(Basically the sensors) based on the Array size of sensors key in same Json file and copy the sensors array and updated with the SystemID(from c8y) also generate sendmeasure.Json file in same directory Using *ManageChildDevices.java* class.
  * The location of parkingPi.json be anywhere in pi but you have to update the file location in The class com\softwareag\parkingpi\PiProperties.java
  * Start to send Measurements to sensors using *MeasurementPublisher.java* class.
   
  ***ManageChildDevices.java***
  * This Class Creates the Sensors as ManagedObject and add it as Child device to The Parent parking Pi.
  * ManagedObjecet is Set type as c8y_DistanceSensor and Measurement Type as Distance Measurement And the name of this Device also get from ParkingPiJson
      
  ***MeasurementPublisher.java***
  * Reads Echo trig and sourceId From the sendmeasure.json file
  * set source to that Id, read the value from the sensor using trig and echo pin
  * set the readed value to MeasurementValue and unit as centimeter and send it to cumulocity.
   
  ***ParkingPiStatus.java***
  * Set ParkingPi as ACTIVE, INACTIVE or MAINTENANCE state.
