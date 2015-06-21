package com.kasun.run;

import com.kasun.userapp.dto.ObjectID;
import com.kasun.userapp.logic.Calculation;
import com.kasun.userapp.logic.ReadingXLLogic;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kasun on 6/20/15.
 */
public class MainClass {

    public static void main(String args []){

        BigDecimal upperValueSecondPart = BigDecimal.TEN.scaleByPowerOfTen(6);
        ReadingXLLogic readingXLLogic = new ReadingXLLogic();
        List<ObjectID> tables = readingXLLogic.getAllTheTablesAsList();
        Calculation calculation = new Calculation();
        calculation.calculateBestPerformanceTable(tables);
    }
}
