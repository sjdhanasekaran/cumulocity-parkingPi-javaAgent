package com.softwareag.parkingpi;

enum Status{
    ACTIVE,MAINTENANCE,INACTIVE
}

public class ParkingPiStatus {
private Status status;
public ParkingPiStatus(Status status){
      this.status=status;
  }
  public ParkingPiStatus() {

  }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void setStatusActive(){
    this.status=Status.ACTIVE;
    }
    public void setStatusInActive(){
        this.status=Status.INACTIVE;
    }
    public void setStatusMaintenance(){
        this.status=Status.MAINTENANCE;
    }


}


