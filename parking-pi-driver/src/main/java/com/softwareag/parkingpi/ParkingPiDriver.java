package com.softwareag.parkingpi;

/**
 * This is the main driver class
 * Initialize platform and deletes all unwanted child devices and External identity created by the Agent for the first time.
 * Initiates the new child devices Creation that are need for the parkingpi(i.e Distance Sensors as per the requirements)
 * Initiates the measurements
 */
import c8y.Position;
import c8y.lx.driver.Driver;
import c8y.lx.driver.OperationExecutor;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.softwareag.parkingpi.helper.JsonHelper;
import com.softwareag.parkingpi.helper.PiProperties;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;


public class ParkingPiDriver implements Driver {
    private static Logger logger = LoggerFactory.getLogger(ParkingPiDriver.class);
    private Platform platform;

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void initialize(Platform platform) throws Exception {
        this.platform = platform;
        logger.info("platform Initializing");
    }

    @Override
    public OperationExecutor[] getSupportedOperations() {
        return new OperationExecutor[0];
    }

    @Override
    public void initializeInventory(ManagedObjectRepresentation mo) {

    }

    @Override
    public void discoverChildren(ManagedObjectRepresentation mo) {
        logger.info("Discovering the Child");
        String piName = PiProperties.INSTANCE.getPiName();
        GId parentID = mo.getId();
        List<Sensor> sensors = PiProperties.INSTANCE.getSenors();
        logger.info("Values are Assigned");
        String getMoName = mo.getName();
        if (!(getMoName.equals(piName))) {
            logger.info("going to Create child device");
            mo.setName(piName);
            Position position = new Position();
            position.setLng(BigDecimal.valueOf(PiProperties.INSTANCE.getLng()));
            position.setLat(BigDecimal.valueOf(PiProperties.INSTANCE.getLat()));
            mo.set(position);
            mo.set(new ParkingPiStatus(Status.ACTIVE));
            mo.setLastUpdated(null);
            platform.getInventoryApi().update(mo);
            ManageChildDevice manageChildDevice = new ManageChildDevice();
            manageChildDevice.createChildDevices(sensors, platform, parentID);////sensor creation and registration
            JsonHelper.copyJSONToFile(sensors);
            logger.info("new file Created in the folder desktop/c8y");
            logger.info("Child created");
        }
    }

    @Override
    public void start() {
        logger.info("Parking Pi Started");
        MeasurementPublisher publisher = new MeasurementPublisher();
        publisher.publishMeasurement(platform);
    }

}