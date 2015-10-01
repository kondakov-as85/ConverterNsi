package com.proitr.ConverterNsi;

import java.io.IOException;

/**
 * Created by kondakov on 25.09.2015.
 */
public class ConvertDebag {
    public static void main(String[] args) throws IOException {
        Generated gen = new Generated();

//        String pathXls = params[0];
//        String pathXls2 = params[1];
//        String pathXmlFromNsi = params[2];
//        String pathProp = params[3];
//        String paramPathOut = params[4];

        String[] params = new String[6];
        params[0] = "d:\\Temp\\Converter_new\\f1.xls";
        params[1] = "d:\\Temp\\Converter_new\\f2_new2.xls";
        params[2] = "d:\\Temp\\Converter_new\\dataNsi_municip.xml";
        params[3] = "d:\\Temp\\Converter_new\\dataNsi_region.xml";
        params[4] = "./params.property";
        params[5] = "d:\\Temp\\Out_Converter\\";
        gen.work(params);
    }
}
