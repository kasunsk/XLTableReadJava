package com.kasun.run;

import com.kasun.userapp.dto.ObjectID;
import com.kasun.userapp.logic.ReadingXLLogic;

import java.util.List;

/**
 * Created by kasun on 6/20/15.
 */
public class MainClass {

    public static void main(String args []){

        ReadingXLLogic readingXLLogic = new ReadingXLLogic();
        List<ObjectID> tables = readingXLLogic.getAllTheTablesAsList();
    }
}
