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



//    public static String  getProp(String key) {
//        String result = "";
//        try {
//            result = FileUtils.getProperty(Constants.pathProperty, key);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


}
