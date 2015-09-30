package com.proitr.ConverterNsi;

/**
 * Created by kondakov on 30.09.2015.
 */
public enum PropFormula {
    SUM13("arg1*0.97/1.124"),
    COUNT13("arg1*0.97"),
    SUM15("arg1*1.05*0.947"),
    COUNT15("arg1*1.05"),
    SUM16("arg1*1.04*1.042"),
    COUNT16("arg1*1.04"),
    SUM17("arg1*1.03*0.987"),
    COUNT17("arg1*1.03");

    private final String formula;

    PropFormula(String s) {
        this.formula = s;
    }

    public String getFormula() {
        return formula;
    }
}
