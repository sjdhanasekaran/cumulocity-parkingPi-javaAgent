package com.softwareag.parkingpi;

import c8y.DistanceSensor;
import c8y.IsDevice;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.identity.ExternalIDRepresentation;
import com.cumulocity.rest.representation.inventory.ManagedObjectReferenceRepresentation;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Creates the child devices and its identity as per the requirement.
 */
public class ManageChildDevice {
    private static Logger logger = LoggerFactory.getLogger(ManageChildDevice.class);
    public void createChildDevices(List<Sensor> sensors, Platform platform, GId parentId) {
        for(Sensor sensor: sensors) {
            logger.info("Into Creating the sensors");
            ManagedObjectRepresentation managedObjectRepresentation = new ManagedObjectRepresentation();
            managedObjectRepresentation.setName(String.valueOf(sensor.getName()));
            managedObjectRepresentation.set(new IsDevice());
            managedObjectRepresentation.setType("c8y_DistanceSensor");
            managedObjectRepresentation.set(new DistanceSensor());
            managedObjectRepresentation = platform.getInventoryApi().create(managedObjectRepresentation);

            ManagedObjectReferenceRepresentation sensorMref = new ManagedObjectReferenceRepresentation();
            sensorMref.setManagedObject(managedObjectRepresentation);
            platform.getInventoryApi().getManagedObjectApi(parentId).addChildDevice(sensorMref);

            ExternalIDRepresentation senEir = new ExternalIDRepresentation();
            senEir.setExternalId(managedObjectRepresentation.getName());
            senEir.setType("c8y_DistanceSensor");
            senEir.setManagedObject(managedObjectRepresentation);
            platform.getIdentityApi().create(senEir);
            sensor.setsID(managedObjectRepresentation.getId().getValue());
        }
        logger.info("child Devices created");
    }
}
