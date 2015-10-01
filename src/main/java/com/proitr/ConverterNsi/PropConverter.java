package com.proitr.ConverterNsi;

/**
 * Created by kondakov on 28.09.2015.
 */
public enum PropConverter {
    CODE_CONSTRUCTION("45"),
    BEGIN_ROW_PROGNOZ("1"),
    BEGIN_ROW_MERT("6"),
    CODE_PROGNOZ_COLUMN("0"),
    CODE_MERT_COLUMN("0"),
    ID_PROGNOZ_COLUMN("2"),
    ID_MERT_COLUMN("4"),
    VALUE_MERT_COLUMN("7"),
    COLUMN_SUM_13("3"),
    COLUMN_COUNT_13("4"),
    COLUMN_SUM_14("5"),
    COLUMN_COUNT_14("6"),
    COLUMN_SUM_15("7"),
    COLUMN_COUNT_15("8"),
    COLUMN_SUM_16("9"),
    COLUMN_COUNT_16("10"),
    COLUMN_SUM_17("11"),
    COLUMN_COUNT_17("12"),
    MERT_COLUMN_INDEX_COUNT_SETTLEMENT_YEAR("6"),
    MERT_COLUMN_INDEX_SUM_SETTLEMENT_YEAR("7"),
    MERT_COLUMN_INDEX_COUNT_PROGNOZ1("8"),
    MERT_COLUMN_INDEX_SUM_PROGNOZ1("9"),
    MERT_COLUMN_INDEX_COUNT_PROGNOZ2("10"),
    MERT_COLUMN_INDEX_SUM_PROGNOZ2("11"),
    MERT_COLUMN_INDEX_COUNT_PROGNOZ3("12"),
    MERT_COLUMN_INDEX_SUM_PROGNOZ3("13");

    private final String val;

    PropConverter(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
