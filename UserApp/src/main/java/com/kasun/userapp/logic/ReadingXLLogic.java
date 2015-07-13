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
 * Created by Kasun Kariyawasam on 6/20/15.
 */
public class ReadingXLLogic {

    private static final String FILE_PATH = "Book1.xlsx";
    private static final String MAIN_TABLE_NAME = "OBJECTID";
    private static final String TABLE_END_HINT = "Aproximate power can generate";


    public List getAllTheTablesAsList() {

        List tableList = new ArrayList<ObjectID>();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(FILE_PATH);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator rowIterator = sheet.iterator();
            ObjectID objectID = null;
            String dimensionType = null;

            while (rowIterator.hasNext()) {
                Dimensions dimensions = null;
                Row row = (Row) rowIterator.next();
                Iterator cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();

                    if (isCellTypeNumeric(cell)) {
                        System.out.print(cell.getNumericCellValue() + " , ");
                        if (dimensions != null) {
                            addValuesToDimension(dimensions, cell);
                        }

                    } else if (isCellTypeString(cell)) {

                        if (cell.getStringCellValue().equals(TABLE_END_HINT)) {
                            if (objectID != null) {
                                tableList.add(objectID);
                            }
                            break;
                        } else if (cell.getStringCellValue().equals(MAIN_TABLE_NAME)) {
                            objectID = new ObjectID();
                        } else if (isDimensionType(cell)) {
                            dimensionType = setDimensionType(dimensionType, cell);
                            dimensions = new Dimensions();
                            System.out.print(" ");

                        }
                    }
                }
                setDimensionToObjectID(dimensionType, objectID, dimensions);
                System.out.println();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }

        return tableList;
    }


    private boolean isDimensionType(Cell cell) {

        String dimensionType = cell.getStringCellValue();
        return (dimensionType.equals(ObjectID.CANAL) || dimensionType.equals(ObjectID.WEIR) ||
                dimensionType.equals(ObjectID.CATCHMENT_AREA) || dimensionType.equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_HOUSE)
                ||
                dimensionType.equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_WEIR) || dimensionType.equals(ObjectID.PENSTOCK) ||
                dimensionType.equals(ObjectID.TAILRACE) || dimensionType.equals(ObjectID.HEAD));
    }


    private String setDimensionType(String dimensionType, Cell cell) {

        if (cell.getStringCellValue().equals(ObjectID.WEIR)) {
            dimensionType = ObjectID.WEIR;
        } else if (cell.getStringCellValue().equals(ObjectID.CANAL)) {
            dimensionType = ObjectID.CANAL;
        } else if (cell.getStringCellValue().equals(ObjectID.CATCHMENT_AREA)) {
            dimensionType = ObjectID.CATCHMENT_AREA;
        } else if (cell.getStringCellValue().equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_HOUSE)) {
            dimensionType = ObjectID.DISTANCE_FROM_ROAD_TO_POWER_HOUSE;
        } else if (cell.getStringCellValue().equals(ObjectID.DISTANCE_FROM_ROAD_TO_POWER_WEIR)) {
            dimensionType = ObjectID.DISTANCE_FROM_ROAD_TO_POWER_WEIR;
        } else if (cell.getStringCellValue().equals(ObjectID.TAILRACE)) {
            dimensionType = ObjectID.TAILRACE;
        } else if (cell.getStringCellValue().equals(ObjectID.PENSTOCK)) {
            dimensionType = ObjectID.PENSTOCK;
        } else if (cell.getStringCellValue().equals(ObjectID.HEAD)) {
            dimensionType = ObjectID.HEAD;
        }
        return dimensionType;
    }


    private void setDimensionToObjectID(String dimensionType, ObjectID objectID, Dimensions dimensions) {

        if (dimensions != null && objectID != null && dimensionType != null) {

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

        if (!(cell == null) && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

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
}
