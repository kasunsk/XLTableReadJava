package com.kasun.run;

import com.kasun.userapp.logic.ReadingXLLogic;

/**
 * Created by kasun on 6/20/15.
 */
public class MainClass {

    public static void main(String args []){

        ReadingXLLogic readingXLLogic = new ReadingXLLogic();
        readingXLLogic.readXLSheet();
    }
}
