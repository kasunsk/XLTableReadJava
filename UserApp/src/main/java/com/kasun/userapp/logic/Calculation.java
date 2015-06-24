package com.kasun.userapp.logic;

import com.kasun.userapp.dto.ObjectID;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by kasun on 6/21/15.
 */
public class Calculation {

    private static final Long LONG_ZERO = 0L;

    private Double MINIMUM_WEIR_LENGTH;
    private Double MINIMUM_CATCHMENT_AREA;
    private Double MINIMUM_CANAL_LENGTH;
    private Double MINIMUM_HEAD;
    private Double MINIMUM_PENSTOCK;
    private Double MINIMUM_ROAD_DISTANCE_TO_WEIR;
    private Double MINIMUM_ROAD_DISTANCE_TO_HOUSE;
    private Double MINIMUM_TAILRACE_LANGTH;

    public static void main(String args[]){

        Calculation calculation = new Calculation();
        System.out.print(calculation.valueOfPowerPlant(new BigDecimal("10000")));
    }

    public Map<String , List<BigDecimal>> calculatePValuesAndValueOfPowerPlant(List<ObjectID> tables){

        setMinimumValues(tables);
        Map<String , List<BigDecimal>> requiredValues = new HashMap<String, List<BigDecimal>>();
        List<BigDecimal> values = null;
        int tableNumber = 1;

        for (ObjectID objectID : tables){
            values = new ArrayList<BigDecimal>();
            BigDecimal pValue = calculateP(objectID.getCatchmentArea().getValue(), objectID.getHead().getValue());
            values.add(pValue);
            BigDecimal valueOfPowerPlant = valueOfPowerPlant(calculateEquationXValue(objectID));
            values.add(valueOfPowerPlant);
            String key = tableNumber + "";
            requiredValues.put(key, values);
            tableNumber++;
        }

        return requiredValues;
    }


    public BigDecimal calculateP(Double catchmentArea, Double head){

        BigDecimal catchmentValue = new BigDecimal(Double.toString(catchmentArea));
        BigDecimal headValue = new BigDecimal(Double.toString(head));

        BigDecimal upperValueFirstPart = catchmentValue.multiply(new BigDecimal("3590")).multiply(new BigDecimal("80")).scaleByPowerOfTen(6) ;
        BigDecimal upperValueSecondPart = (new BigDecimal("0.7")).multiply(new BigDecimal("9.8")).multiply(headValue);
        BigDecimal upperValue = upperValueFirstPart.multiply(upperValueSecondPart);

        BigDecimal lowerValue = (new BigDecimal("365")).multiply(new BigDecimal("24")).multiply(new BigDecimal("60")).scaleByPowerOfTen(5);

        BigDecimal finalAnswerForP= upperValue.divide(lowerValue, 4, RoundingMode.HALF_UP);

        return finalAnswerForP;
    }

    public BigDecimal valueOfPowerPlant(BigDecimal equationXValue){

        return new BigDecimal("1000").divide(equationXValue, 4, RoundingMode.HALF_UP);
    }

    private void setMinimumValues(List<ObjectID> tables) {

        if(tables != null) {
            List<Double> weirLengths = new ArrayList<Double>();
            List<Double> catchmentAreas = new ArrayList<Double>();
            List<Double> canalLengths = new ArrayList<Double>();
            List<Double> heads = new ArrayList<Double>();
            List<Double> penStocks = new ArrayList<Double>();
            List<Double> roadDistanceToWeirs = new ArrayList<Double>();
            List<Double> roadDistanceToHouses = new ArrayList<Double>();
            List<Double> tailRaces = new ArrayList<Double>();

            for (ObjectID objectID : tables) {
                collectEachValues(weirLengths, catchmentAreas, canalLengths, heads, penStocks, roadDistanceToWeirs, roadDistanceToHouses, tailRaces, objectID);
            }

            setMINIMUMS(weirLengths, catchmentAreas, canalLengths, heads, penStocks, roadDistanceToWeirs, roadDistanceToHouses, tailRaces);
        }
    }

    private void setMINIMUMS(List<Double> weirLengths, List<Double> catchmentAreas, List<Double> canalLengths, List<Double> heads, List<Double> penStocks, List<Double> roadDistanceToWeirs, List<Double> roadDistanceToHouses, List<Double> tailRaces) {

        MINIMUM_WEIR_LENGTH = Collections.min(weirLengths);
        MINIMUM_CATCHMENT_AREA = Collections.min(catchmentAreas);
        MINIMUM_CANAL_LENGTH = Collections.min(canalLengths);
        MINIMUM_HEAD = Collections.min(heads);
        MINIMUM_PENSTOCK = Collections.min(penStocks);
        MINIMUM_ROAD_DISTANCE_TO_WEIR = Collections.min(roadDistanceToWeirs);
        MINIMUM_ROAD_DISTANCE_TO_HOUSE = Collections.min(roadDistanceToHouses);
        MINIMUM_TAILRACE_LANGTH = Collections.min(tailRaces);
    }

    private void collectEachValues(List<Double> weirLengths, List<Double> catchmentAreas, List<Double> canalLengths, List<Double> heads, List<Double> penStocks, List<Double> roadDistanceToWeirs, List<Double> roadDistanceToHouses, List<Double> tailRaces, ObjectID objectID) {

        weirLengths.add((objectID.getWeir().getValue()));
        catchmentAreas.add((objectID.getCatchmentArea().getValue()));
        canalLengths.add((objectID.getCanal().getValue()));
        heads.add((objectID.getHead().getValue()));
        penStocks.add((objectID.getPenStock().getValue()));
        roadDistanceToWeirs.add((objectID.getDistanceFromRoadToPowerWeir().getValue()));
        roadDistanceToHouses.add((objectID.getDistanceFromRoadToPowerHouse().getValue()));
        tailRaces.add((objectID.getTailRace().getValue()));
    }


    public BigDecimal calculateEquationXValue(ObjectID objectID){

        BigDecimal partOne = (new BigDecimal(objectID.getWeir().getValue()).subtract(new BigDecimal(MINIMUM_WEIR_LENGTH))).multiply(new BigDecimal("6")).divide(new BigDecimal("6"), 4, RoundingMode.HALF_UP);
        BigDecimal partTwo = (new BigDecimal(objectID.getCatchmentArea().getValue()).subtract(new BigDecimal(MINIMUM_CATCHMENT_AREA))).multiply(new BigDecimal("6")).divide(new BigDecimal("2.5"), 4, RoundingMode.HALF_UP);
        BigDecimal partThree = (new BigDecimal(objectID.getCanal().getValue()).subtract(new BigDecimal(MINIMUM_CANAL_LENGTH))).multiply(new BigDecimal("3.5")).divide(new BigDecimal("150"), 4, RoundingMode.HALF_UP);
        BigDecimal partFour = (new BigDecimal(objectID.getHead().getValue()).subtract(new BigDecimal(MINIMUM_HEAD))).multiply(new BigDecimal("5")).divide(new BigDecimal("15"), 4, RoundingMode.HALF_UP);
        BigDecimal partFive = (new BigDecimal(objectID.getPenStock().getValue()).subtract(new BigDecimal(MINIMUM_PENSTOCK))).multiply(new BigDecimal("4")).divide(new BigDecimal("55"), 4, RoundingMode.HALF_UP);
        BigDecimal partSix = (new BigDecimal(objectID.getDistanceFromRoadToPowerWeir().getValue()).subtract(new BigDecimal(MINIMUM_ROAD_DISTANCE_TO_WEIR))).multiply(new BigDecimal("1.5")).divide(new BigDecimal("15"), 4, RoundingMode.HALF_UP);
        BigDecimal partSeven = (new BigDecimal(objectID.getDistanceFromRoadToPowerHouse().getValue()).subtract(new BigDecimal(MINIMUM_ROAD_DISTANCE_TO_HOUSE))).multiply(new BigDecimal("2.5")).divide(new BigDecimal("60"), 4, RoundingMode.HALF_UP);
        BigDecimal partEight = (new BigDecimal(objectID.getTailRace().getValue()).subtract(new BigDecimal(MINIMUM_TAILRACE_LANGTH))).multiply(new BigDecimal("3")).divide(new BigDecimal("16"), 4, RoundingMode.HALF_UP);

        BigDecimal finalAnswer = partOne.subtract(partTwo).add(partThree).subtract(partFour).add(partFive).add(partSix).add(partSeven).add(partEight);
        return finalAnswer;
    }
}
