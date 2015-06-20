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
        String cellType = null;

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

                        Dimensions dimensions = new Dimensions();
                        ObjectID objectID = new ObjectID();
                        Row row = (Row) rowIterator.next();
                        Iterator cellIterator = row.cellIterator();

                        while (cellIterator.hasNext()) {
                            Cell cell = (Cell) cellIterator.next();

                            if (isCellTypeString(cell)) {

                                if (cell.getStringCellValue().equals(MAIN_TABLE_NAME)) {
                                    System.out.println();

                                }else {

                                    if (cell.getStringCellValue().equals(ObjectID.WEIR)) {
                                        cellType = ObjectID.WEIR;
                                    }

                                    System.out.print(cell.getStringCellValue() + " , ");
                                }

                            } else if (isCellTypeNumeric(cell)) {

                                if (isNextCellNotAString(cellIterator)) {


//                                    dimensions.setApproximate(cell.getNumericCellValue());
                                    System.out.print(cell.getNumericCellValue() + " , ");
                                }else{
                                    System.out.print(" * ");
                                }

                                addValuesToDimension(dimensions, cell);
                            }


                        }

                        System.out.println();
                    }
            }


        }catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }

        return null;
    }

    private void addValuesToDimension(Dimensions dimensions, Cell cell) {

        if(!(cell == null) && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

            if (!isCellValueNull(cell) && cell.getColumnIndex() == 3) {
                dimensions.setShapeLength(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 4) {
                dimensions.setLength(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 5) {
                dimensions.setValue(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 6) {
                dimensions.setFinalResult(cell.getNumericCellValue());
            } else if (!isCellValueNull(cell) && cell.getColumnIndex() == 7) {
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
