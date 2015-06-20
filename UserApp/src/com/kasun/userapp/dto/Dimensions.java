package com.kasun.userapp.dto;

/**
 * Created by kasun on 6/20/15.
 */
public class Dimensions {

    public static final String SHAPE_LENGTH = "SHAPE_Leng";
    public static final String LENGTH = "length";
    public static final String VALUES = "values_";
    public static final String FINAL_RESULT = "Final_Resu";
    public static final String APPROXIMATE = "Aproximate";

    private double shapeLength;
    private double length;
    private double value;
    private double finalResult;
    private double approximate;

    public double getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(double shapeLength) {
        this.shapeLength = shapeLength;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getApproximate() {
        return approximate;
    }

    public void setApproximate(double approximate) {
        this.approximate = approximate;
    }

    public double getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(double finalResult) {
        this.finalResult = finalResult;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
