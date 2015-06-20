package com.kasun.userapp.dto;

/**
 * Created by kasun on 6/20/15.
 */
public class ObjectID {

    public static final String WEIR = "weir";
    public static final String CANAL = "canal";
    public static final String PENSTOCK = "penstock";
    public static final String TAILRACE = "tailrace";
    public static final String DISTANCE_FROM_ROAD_TO_POWER_HOUSE = "distance from road to power house";
    public static final String DISTANCE_FROM_ROAD_TO_POWER_WEIR = "distance from road to power weir";
    public static final String CATCHMENT_AREA = "catchment area";
    public static final String HEAD = "head";

    private Dimensions weir;
    private Dimensions canal;
    private Dimensions penStock;
    private Dimensions tailRace;
    private Dimensions distanceFromRoadToPowerHouse;
    private Dimensions distanceFromRoadToPowerWeir;
    private Dimensions catchmentArea;
    private Dimensions head;

    public Dimensions getWeir() {
        return weir;
    }

    public void setWeir(Dimensions weir) {
        this.weir = weir;
    }

    public Dimensions getPenStock() {
        return penStock;
    }

    public void setPenStock(Dimensions penStock) {
        this.penStock = penStock;
    }

    public Dimensions getCanal() {
        return canal;
    }

    public void setCanal(Dimensions canal) {
        this.canal = canal;
    }

    public Dimensions getTailRace() {
        return tailRace;
    }

    public void setTailRace(Dimensions tailRace) {
        this.tailRace = tailRace;
    }

    public Dimensions getDistanceFromRoadToPowerHouse() {
        return distanceFromRoadToPowerHouse;
    }

    public void setDistanceFromRoadToPowerHouse(Dimensions distanceFromRoadToPowerHouse) {
        this.distanceFromRoadToPowerHouse = distanceFromRoadToPowerHouse;
    }

    public Dimensions getCatchmentArea() {
        return catchmentArea;
    }

    public void setCatchmentArea(Dimensions catchmentArea) {
        this.catchmentArea = catchmentArea;
    }

    public Dimensions getHead() {
        return head;
    }

    public void setHead(Dimensions head) {
        this.head = head;
    }

    public Dimensions getDistanceFromRoadToPowerWeir() {
        return distanceFromRoadToPowerWeir;
    }

    public void setDistanceFromRoadToPowerWeir(Dimensions distanceFromRoadToPowerWeir) {
        this.distanceFromRoadToPowerWeir = distanceFromRoadToPowerWeir;
    }
}
