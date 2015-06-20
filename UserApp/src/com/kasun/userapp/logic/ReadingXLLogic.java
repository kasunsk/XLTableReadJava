package com.kasun.userapp.logic;

import com.kasun.userapp.dto.Dimensions;
import com.kasun.userapp.dto.ObjectID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kasun on 6/20/15.
 */
public class ReadingXLLogic {

    private static final String FILE_PATH = "/home/kasun/Downloads/Final.xlsx";
    private static final String MAIN_TABLE_NAME = "OBJECTID";

    public String readXLSheet(){
        int cellToAdd = 0;
        String dimensionType = null;

        List studentList = new ArrayList();
        FileInputStream fis = null;

        try{
            fis = new FileInputStream(FILE_PATH);
            Workbook workbook = new XSSFWorkbook(fis);

            int numberOfSheets = workbook.getNumberOfSheets();
            System.out.println("Number Of Sheets = "+numberOfSheets);

            for (int i=0 ; i < numberOfSheets ; i++){
                Sheet sheet = workbook.getSheetAt(i);
                Iterator rowIterator = sheet.iterator();

                    while (rowIterator.hasNext()) {

                        ObjectID objectID = new ObjectID();

                        Dimensions dimensions = null;
                        Row row = (Row) rowIterator.next();
                        Iterator cellIterator = row.cellIterator();

                        while (cellIterator.hasNext()) {
                            Cell cell = (Cell) cellIterator.next();

                            if(isCellTypeNumeric(cell)) {
                                System.out.print(cell.getNumericCellValue()+" , ");
                                addValuesToDimension(dimensions,cell);

                            }else if(isCellTypeString(cell)){

                                dimensionType = setDimetionType(dimensionType, cell);
                                dimensions = new Dimensions();
                                System.out.print(" ");
                            }
//                            if (isCellTypeString(cell)) {
//
//                                if (cell.getStringCellValue().equals(MAIN_TABLE_NAME)) {
//                                    System.out.println();
//
//                                }else {
//
//                                    if (cell.getStringCellValue().equals(ObjectID.WEIR)) {
//                                        System.out.print("Came here");
//                                        dimensionType = ObjectID.WEIR;
//                                    }
//
//                                    System.out.print(cell.getStringCellValue() + " , ");
//                                }
//
//                            } else if (isCellTypeNumeric(cell)) {
//
//                                if (isNextCellNotAString(cellIterator)) {
//
////                                    dimensions.setApproximate(cell.getNumericCellValue());
//                                    System.out.print(cell.getNumericCellValue() + " , ");
//                                }else{
//                                    System.out.print(cell.getNumericCellValue() + " , ");
//                                }
//
//                                addValuesToDimension(dimensions, cell);
//                            }


                        }

                        setDimensionToObjectID(dimensionType, objectID, dimensions);
                        System.out.println();
                    }
            }


        }catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }

        return null;
    }

    private String setDimetionType(String dimensionType, Cell cell) {
        if(cell.getStringCellValue().equals(ObjectID.WEIR)){
            dimensionType = ObjectID.WEIR;
        }else if(cell.getStringCellValue().equals(ObjectID.CANAL)){
            dimensionType = ObjectID.CANAL;
        }else if(cell.getStringCellValue().equals(ObjectID.CATCHMENT_AREA)){
            dimensionType = ObjectID.CATCHMENT_AREA;
        }else if(cell.getStringCellValue().equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_HOUSE)){
            dimensionType = ObjectID.DISTANCE_FROM_ROAD_TO_POWER_HOUSE;
        }else if(cell.getStringCellValue().equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_WEIR)){
            dimensionType = ObjectID.DISTANCE_FROM_ROAD_TO_POWER_WEIR;
        }else if(cell.getStringCellValue().equals(ObjectID.TAILRACE)){
            dimensionType = ObjectID.TAILRACE;
        }else if(cell.getStringCellValue().equals(ObjectID.PENSTOCK)){
            dimensionType = ObjectID.PENSTOCK;
        }else if(cell.getStringCellValue().equals(ObjectID.HEAD)){
            dimensionType = ObjectID.HEAD;
        }
        return dimensionType;
    }

    private void setDimensionToObjectID(String dimensionType, ObjectID objectID, Dimensions dimensions) {

        if(dimensions != null && objectID != null && dimensionType!= null) {

            if (dimensionType.equals(ObjectID.WEIR)) {
                objectID.setWeir(dimensions);
            } else if (dimensionType.equals(ObjectID.CANAL)) {
                objectID.setCanal(dimensions);
            } else if (dimensionType.equals(ObjectID.CATCHMENT_AREA)) {
                objectID.setCatchmentArea(dimensions);
            } else if (dimensionType.equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_HOUSE)) {
                objectID.setDistanceFromRoadToPowerHouse(dimensions);
            } else if (dimensionType.equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_WEIR)) {
                objectID.setDistanceFromRoadToPowerWeir(dimensions);
            } else if (dimensionType.equals(ObjectID.HEAD)) {
                objectID.setHead(dimensions);
            } else if (dimensionType.equals(ObjectID.PENSTOCK)) {
                objectID.setPenStock(dimensions);
            } else if (dimensionType.equals(ObjectID.TAILRACE)) {
                objectID.setTailRace(dimensions);
            }
        }
    }

    private void addValuesToDimension(Dimensions dimensions, Cell cell) {


        if(!(cell == null) && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

            if (!isCellValueNull(cell) && cell.getColumnIndex() == 2) {
                dimensions.setShapeLength(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 3) {
                dimensions.setLength(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 4) {
                dimensions.setValue(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 5) {
                dimensions.setFinalResult(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 6) {
                dimensions.setApproximate(cell.getNumericCellValue());
            }
        }
    }


    private boolean isCellValueNull(Cell cell) {

        return cell.getNumericCellValue() == Double.NaN;
    }


    private boolean isCellTypeNumeric(Cell cell) {

        return Cell.CELL_TYPE_NUMERIC == cell.getCellType();
    }


    private boolean isCellTypeString(Cell cell) {

        return Cell.CELL_TYPE_STRING == cell.getCellType();
    }


    private  boolean isNextCellNotAString(Iterator cellIterator) {

        return cellIterator.hasNext() && !(((Cell) cellIterator.next()).getCellType()==Cell.CELL_TYPE_STRING);
    }
}
