package com.softwareag.parkingpi.helper;

import com.softwareag.parkingpi.Sensor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Get name,the number of parking place we have to create,trig and echo pin for reading the measurement
 */
    public class PiProperties {
        private static Logger logger = LoggerFactory.getLogger(PiProperties.class);
        public static PiProperties INSTANCE = new PiProperties();
        private String piName;
        private Double lat;
        private Double lng;
        private List<Sensor> sensors = new ArrayList<>();
        /*  private JSONArray sensors*/

        private void initialize() {
            JSONObject fObj = null;

            try {
                JSONParser parser = new JSONParser();
                FileReader r = new FileReader("/home/pi/Desktop/c8y/parkingpi.json");
                Object obj = parser.parse(r);
                fObj = (JSONObject) obj;

                //get piname and set it to the var
                piName = (String) fObj.get("name");
                JSONObject posObj=new JSONObject();
                posObj=(JSONObject)fObj.get("location");
                lat=(Double)posObj.get("lat");
                lng=(Double)posObj.get("lng");
                JSONArray sens = (JSONArray) fObj.get("sensors");
                for (int i = 0; i < sens.size(); i++) {
                    JSONObject sensorObj = (JSONObject) sens.get(i);
                    sensors.add(new Sensor((String) sensorObj.get("name"), (String) sensorObj.get("trig"), (String) sensorObj.get("echo")));
                }

            } catch (Exception e) {
               logger.info("problem at Piproperties class initialize");
            }
        }
    private PiProperties() {
        initialize();
    }
        public String getPiName() {
            return piName;
        }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public List<Sensor> getSenors() {
            return sensors;
        }
    }
