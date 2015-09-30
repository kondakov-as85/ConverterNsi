package com.proitr.ConverterNsi;

import com.mycomp.ExtUtils.FileUtils;

import java.io.IOException;

/**
 * Created by kondakov on 27.08.2015.
 */
public class ExtUtils {

    public static void lgBad(String path, String message) {
        try {
            FileUtils.writeFile(path + "bad.log",message,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void wLog(String message) {
        System.out.println(message);
        FileUtils.printLog(message, "./");
    }

    public static void wLog(String path, String message) {
        System.out.println(message);
        try {
            FileUtils.writeFile(path,message,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String pathProperty, PropConverter propConverter) {
        String result;
        try {
            result = FileUtils.getProperty(pathProperty, propConverter.name());
            if (result==null) {
                result = propConverter.getVal();
                System.out.println("Not Param " + propConverter.name() + " getting default = " + result);
            }
        } catch (Exception e) {
            System.out.println("Not Param " + propConverter.name());
            result = propConverter.getVal();
        }
        return result;
    }

    public static String getFormula(String pathProperty, PropFormula propFormula) {
        String result;
        try {
            result = FileUtils.getProperty(pathProperty, propFormula.name());
            if (result==null) {
                result = propFormula.getFormula();
                System.out.println("Not Param " + propFormula.name() + " getting default = " + result);
            }
        } catch (Exception e) {
            result = propFormula.getFormula();
            System.out.println("Not Param " + propFormula.name() + " getting default = " + result);
        }
        return result;
    }

    public static double round(double number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return (double) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }


}
