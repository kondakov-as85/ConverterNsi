package com.proitr.ConverterNsi;

/**
 * Created by kondakov on 30.09.2015.
 */
public enum PropFormula {
    SUM_SETTLEMENT_YEAR("arg1*0.97/1.124"),
    COUNT_SETTLEMENT_YEAR("arg1*0.97"),
    SUM_PROGNOZ1("arg1*1.05*0.947"),
    COUNT_PROGNOZ1("arg1*1.05"),
    SUM_PROGNOZ2("arg1*1.04*1.042"),
    COUNT_PROGNOZ2("arg1*1.04"),
    SUM_PROGNOZ3("arg1*1.03*0.987"),
    COUNT_PROGNOZ3("arg1*1.03");

    private final String formula;

    PropFormula(String s) {
        this.formula = s;
    }

    public String getFormula() {
        return formula;
    }
}
