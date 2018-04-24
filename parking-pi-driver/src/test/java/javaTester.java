/*
package com.softwareag.parkingpi;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.model.measurement.MeasurementValue;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.cumulocity.sdk.client.Platform;
import c8y.*;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Date;
import com.pi4j.io.gpio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasurementPublisher {
    private static Logger logger = LoggerFactory.getLogger(MeasurementPublisher.class);
    public static boolean measurementSender=true;

    void publishMeasurement(Platform platform) {
        JSONParser parser=new JSONParser();
        Object obj;
        try {
            logger.info("reading the Json File Send measure.json");
            obj = parser.parse(new FileReader("/home/pi/Desktop/c8y/sendmeasure.json"));
            JSONArray sensors=(JSONArray)((JSONObject)obj).get("sensors");
            sensors.forEach(sensor -> {
                new Thread(new SendMeasurement((JSONObject)sensor, platform), "Thread-" + ((JSONObject) sensor).get("id")).start();
            });
        }catch (Exception e){
            logger.info("could not read sendmeasure.json" + e);
        }
    }

    private class SendMeasurement implements Runnable {

        private JSONObject sensor;
        private Platform platform;
        public SendMeasurement(JSONObject sensor, Platform platform) {
            this.sensor = sensor;
            this.platform = platform;
        }
        @Override
        public void run() {
            try {
                logger.info("going to the id triger and echo value for sensor"+Thread.currentThread().getName());
                String id = (String) sensor.get("id");
                String trig = (String) sensor.get("trig");
                String echo = (String) sensor.get("echo");
                logger.info(Thread.currentThread().getName() + " : " + "Trying to publish for sensor " + id);
                Integer trigPin = Integer.valueOf(trig);
                Integer echoPin = Integer.valueOf(echo);
                while (true) {
                    MeasurementRepresentation sendMeasure = new MeasurementRepresentation();
                    ManagedObjectRepresentation sourceMo = platform.getInventoryApi().get(GId.asGId(id));
                    sendMeasure.setSource(sourceMo);
                    sendMeasure.setTime(new Date());
                    sendMeasure.setType("c8y_linux");
                    DistanceMeasurement disMeasure = new DistanceMeasurement();
                    MeasurementValue disValue = new MeasurementValue();
                    BigDecimal sensorVal = calcSensor(trigPin, echoPin);
                    disValue.setValue(sensorVal);
                    logger.info(sourceMo.getName()+" "+Thread.currentThread().getName() + " : " + "Sensor value of " + id + " is " + sensorVal);
                    disValue.setUnit("cm");
                    disMeasure.setDistance(disValue);
                    sendMeasure.set(disMeasure);
                    platform.getMeasurementApi().create(sendMeasure);
                    logger.info(Thread.currentThread().getName() + " : " + "measurement Sent");
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        private BigDecimal calcSensor(Integer trig, Integer echo) {
            BigDecimal distance =BigDecimal.ZERO;
            final GpioController gpio = GpioFactory.getInstance();
            logger.info(Thread.currentThread().getName() + " : " + "Into calcSensor");
            Pin trigPin=RaspiPin.getPinByAddress(trig);
            Pin echoPin=RaspiPin.getPinByAddress(echo);
            final GpioPinDigitalOutput sensorTriggerPin = gpio.provisionDigitalOutputPin(trigPin);
            final GpioPinDigitalInput sensorEchoPin = gpio.provisionDigitalInputPin(echoPin, PinPullResistance.PULL_DOWN);
            try {

                sensorTriggerPin.high(); // Make trigger pin HIGH
                Thread.sleep((long) 0.01);// Delay for 10 microseconds
                sensorTriggerPin.low(); //Make trigger pin LOW

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH

                }
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                System.out.println(startTime);

                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW

                }
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
                distance = BigDecimal.valueOf(((((endTime-startTime)/1e3)/2) / 29.1)); //Printing out the distance in cm
                sensorTriggerPin.setShutdownOptions(true, PinState.LOW);
                sensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
                gpio.shutdown();
                gpio.unprovisionPin(sensorTriggerPin);
                gpio.unprovisionPin(sensorEchoPin);
            } catch (Exception e) {
                logger.info(Thread.currentThread().getName() + " : " + "could not read measurement from sensor", e);
            }
            return distance;
        }
    }

}




*/
