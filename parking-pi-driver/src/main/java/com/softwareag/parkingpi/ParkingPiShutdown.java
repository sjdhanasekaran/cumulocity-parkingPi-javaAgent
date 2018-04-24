package com.softwareag.parkingpi;

import c8y.lx.driver.Driver;
import c8y.lx.driver.OperationExecutor;
import c8y.lx.driver.OpsUtil;
import com.cumulocity.model.operation.OperationStatus;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParkingPiShutdown implements Driver,OperationExecutor{
    private static Logger logger = LoggerFactory.getLogger(ParkingPiShutdown.class);
private Platform platform;
private int count=0;
    @Override
    public void initialize() {

    }

    @Override
    public void initialize(Platform platform) {
this.platform=platform;
    }

    @Override
    public OperationExecutor[] getSupportedOperations() {
            return new OperationExecutor[] { this };
    }

    @Override
    public void initializeInventory(ManagedObjectRepresentation mo) {
        OpsUtil.addSupportedOperation(mo, supportedOperationType());
        logger.info("added operation c8y_Shutdown");

    }

    @Override
    public void discoverChildren(ManagedObjectRepresentation mo) {

    }

    @Override
    public void start() {

    }

    @Override
    public String supportedOperationType() {
        return "c8y_ParkingPiShutdown";
    }

    @Override
    public void execute(OperationRepresentation operation, boolean cleanup) {
        if(count==0){
            platform.getDeviceControlApi().create(operation);
            logger.info("created operation c8y_shutdown");
            count=count++;
        }
        if (MeasurementPublisher.measurementSender) {
            MeasurementPublisher.measurementSender = false;
            ManagedObjectRepresentation mo = platform.getInventoryApi().get(operation.getDeviceId());
            mo.set(new ParkingPiStatus(Status.INACTIVE));
            mo.setLastUpdated(null);
            platform.getInventoryApi().update(mo);
            operation.setStatus(OperationStatus.SUCCESSFUL.toString());
            platform.getDeviceControlApi().update(operation);
            logger.info("executed operation and status active to inactive");
        } else {
            MeasurementPublisher.measurementSender = true;
            ManagedObjectRepresentation mo = platform.getInventoryApi().get(operation.getDeviceId());
            mo.set(new ParkingPiStatus(Status.ACTIVE));
            mo.setLastUpdated(null);
            platform.getInventoryApi().update(mo);
            platform.getDeviceControlApi().update(operation);
            operation.setStatus(OperationStatus.SUCCESSFUL.toString());
            platform.getDeviceControlApi().update(operation);
            logger.info("executed operation and status inactive to active state");

        }
    }
}
