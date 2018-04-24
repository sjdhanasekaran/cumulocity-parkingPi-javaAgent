package com.softwareag.parkingpi;

import c8y.lx.driver.Driver;
import c8y.lx.driver.OperationExecutor;
import c8y.lx.driver.OpsUtil;
import com.cumulocity.model.operation.OperationStatus;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.devicecontrol.DeviceControlApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParkingPiMaintenance implements Driver,OperationExecutor {
    private static Logger logger = LoggerFactory.getLogger(ParkingPiMaintenance.class);
    private Platform platform;
    private int count=0;

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void initialize(Platform platform) throws Exception {
this.platform=platform;
    }

    @Override
    public OperationExecutor[] getSupportedOperations() {
        logger.info("operation (maintenance) Executor");
            return new OperationExecutor[] { this };

    }

    @Override
    public void initializeInventory(ManagedObjectRepresentation mo) {
        OpsUtil.addSupportedOperation(mo,supportedOperationType());
        logger.info("added operation c8y_maintenance");
    }

    @Override
    public void discoverChildren(ManagedObjectRepresentation mo) {

    }

    @Override
    public void start() {

    }

    @Override
    public String supportedOperationType() {
        return "c8y_ParkingPiMaintenance";
    }

    @Override
    public void execute(OperationRepresentation operation, boolean cleanup) throws Exception {
        if(count==0){
            platform.getDeviceControlApi().create(operation);
            logger.info("created operation c8y_maintenance");

            count=count++;
        }
              if (MeasurementPublisher.measurementSender) {
                MeasurementPublisher.measurementSender = false;
                ManagedObjectRepresentation mo = platform.getInventoryApi().get(operation.getDeviceId());
                mo.set(new ParkingPiStatus(Status.MAINTENANCE));
                mo.setLastUpdated(null);
                platform.getInventoryApi().update(mo);
                operation.setStatus(OperationStatus.SUCCESSFUL.toString());
               platform.getDeviceControlApi().update(operation);
                  logger.info("executed operation and status active to maintenance");

              } else {
                MeasurementPublisher.measurementSender = true;
                ManagedObjectRepresentation mo = platform.getInventoryApi().get(operation.getDeviceId());
                mo.set(new ParkingPiStatus(Status.ACTIVE));
                mo.setLastUpdated(null);
                platform.getInventoryApi().update(mo);
                operation.setStatus(OperationStatus.SUCCESSFUL.toString());
                platform.getDeviceControlApi().update(operation);
                  logger.info("executed operation and status maintenance to active state");
            }
        }

    }
