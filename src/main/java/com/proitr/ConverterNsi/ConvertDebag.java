package com.proitr.ConverterNsi;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * Created by kondakov on 25.09.2015.
 */
public class ConvertDebag {
    public static void main(String[] args) throws IOException {
        Generated gen = new Generated();
        String[] params = new String[5];
        params[0] = "-p";
        params[1] = "d:\\Temp\\Converter\\f1.xls";
        params[2] = "d:\\Temp\\Converter\\dataNsi.xml";
        params[3] = "./params.property";
        params[4] = "d:\\Temp\\Out_Converter\\";

        gen.work(params);

        params[0] = "-m";
        params[1] = "d:\\Temp\\Converter\\f2.xls";

        gen.work(params);

        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");

            int i = 10;

            String infix = "3+2*(i+5)";
            engine.put("i",i);
            System.out.println(engine.eval(infix));
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }
}
