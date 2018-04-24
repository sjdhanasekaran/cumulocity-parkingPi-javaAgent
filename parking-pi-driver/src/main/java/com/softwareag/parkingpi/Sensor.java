package com.softwareag.parkingpi;

public class Sensor {
    private String name;
    private String trig;
    private String echo;
    private String sID;
    public Sensor(String name,String trig,String echo){
        this.name=name;
        this.trig=trig;
        this.echo=echo;
    }

    public Sensor(String name, String trig, String echo, String sID){
        this.name=name;
        this.echo=echo;
        this.trig=trig;
        this.sID=sID;
    }
    public String getName() {
        return name;
    }

    public String getEcho() {
        return echo;
    }

    public String getTrig() {
        return trig;
    }

    public String getsID() {
        return sID;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public void setTrig(String trig) {
        this.trig = trig;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

}
