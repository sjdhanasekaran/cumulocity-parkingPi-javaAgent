package com.softwareag.parkingpi.helper;

import com.softwareag.parkingpi.ManageChildDevice;
import com.softwareag.parkingpi.Sensor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonHelper {
    private static Logger logger = LoggerFactory.getLogger(JsonHelper.class);
    public static void copyJSONToFile(List<Sensor> sensors) {
        JSONObject jsonObject = new JSONObject();
        JSONArray sensorJsonArray = new JSONArray();
        for (Sensor sensor : sensors) {
            JSONObject simple = new JSONObject();
            simple.put("name", sensor.getName());
            simple.put("trig", sensor.getTrig());
            simple.put("echo", sensor.getEcho());
            simple.put("id", sensor.getsID());
            sensorJsonArray.add(simple);
        }

        try(FileWriter file = new FileWriter("/home/pi/Desktop/c8y/sendmeasure.json");) {
            jsonObject.put("sensors", sensorJsonArray);
            file.write(jsonObject.toJSONString());
            file.flush();
            logger.info("writing sendmeasure.json finished");
        } catch (IOException e) {
            logger.info("could not write to sendmeasure.json");
        }

    }
}
