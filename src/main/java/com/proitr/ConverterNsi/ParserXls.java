package com.proitr.ConverterNsi;

import com.proitr.ConverterNsi.objects.ArraySubData;
import com.proitr.ConverterNsi.objects.SubData;
import com.proitr.ConverterNsi.objects.SubDataNsi;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParserXls {
    public ArraySubData arraySubData = null;
    public static final int tpPrognoz = 1;
    public static final int tpMERT = 2;
    public List<String> badCode;

    public ParserXls(String pathFile, int type, String pathDataNSI, String pathProp, List<String> badCode) {
        this.badCode = badCode;
        this.arraySubData = new ArraySubData();
        try {
            String codeСonstruction = ExtUtils.getProp(pathProp, PropConverter.CODE_CONSTRUCTION);


            ExtUtils.wLog("Reading NSI Data...");
            Map dataNsi = new ParseDataNsi(pathDataNSI).data;

            FileInputStream fileInputStream = new FileInputStream(pathFile);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet worksheet = workbook.getSheetAt(0);
            boolean eof;
            int indRow;
            switch (type) {
                case tpPrognoz: {
                    int codeColumnPrognoz = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.CODE_PROGNOZ_COLUMN));
                    int beginRowPrognoz = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.BEGIN_ROW_PROGNOZ));
                    int idPrognozColumn = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.ID_PROGNOZ_COLUMN));
                    int colSum13 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_SUM_13)); //3
                    int colCount13 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_COUNT_13)); //4
                    int colSum14 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_SUM_14)); //5
                    int colCount14 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_COUNT_14)); //6
                    int colSum15 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_SUM_15)); //7
                    int colCount15 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_COUNT_15)); //8
                    int colSum16 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_SUM_16)); //9
                    int colCount16 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_COUNT_16)); //10
                    int colSum17 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_SUM_17)); //11
                    int colCount17 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.COLUMN_COUNT_17)); //12
                    eof = false;
                    indRow = beginRowPrognoz;
                    ExtUtils.wLog("Reading Prognoz Data...");
                    while (!eof) {
                        HSSFRow row = worksheet.getRow(indRow);
                        if (row != null) {
                            //Читаем данные из файла
                            HSSFCell codeCell = row.getCell(codeColumnPrognoz);
                            if (codeCell != null) {
                                String code = codeCell.getStringCellValue();
                                if (this.badCode.contains(code)) {
                                    String parentProduct = code.replace(".", ";").split(";")[0];
                                    SubDataNsi subDataNsi = (SubDataNsi) dataNsi.get(code);
                                    Integer id = Integer.valueOf(Double.valueOf(row.getCell(idPrognozColumn).getNumericCellValue()).intValue());

                                    double sum13 = row.getCell(colSum13).getNumericCellValue();
                                    double count13 = row.getCell(colCount13).getNumericCellValue();
                                    double sum14 = row.getCell(colSum14).getNumericCellValue();
                                    double count14 = row.getCell(colCount14).getNumericCellValue();
                                    double sum15 = row.getCell(colSum15).getNumericCellValue();
                                    double count15 = row.getCell(colCount15).getNumericCellValue();
                                    double sum16 = row.getCell(colSum16).getNumericCellValue();
                                    double count16 = row.getCell(colCount16).getNumericCellValue();
                                    double sum17 = row.getCell(colSum17).getNumericCellValue();
                                    double count17 = row.getCell(colCount17).getNumericCellValue();
                                    //end-read
                                    //расчет данных и получение объекта-результата
                                    SubData subData = new SubData();
                                    subData.setId(id);
                                    if (subDataNsi != null) {
                                        subData.setProduct(subDataNsi.getProduct());
                                        subData.setPrognoz(subDataNsi.getPrognoz());
                                    }
                                    subData.setSumlast(sum13);
                                    subData.setSumcurr(sum14);
                                    subData.setSumnext(sum15);
                                    subData.setSumnext2(sum16);
                                    subData.setSumnext3(sum17);
                                    if (parentProduct.equals(codeСonstruction)) {
                                        subData.setSumNorm(sum15);
                                        subData.setSumNorm2(sum16);
                                        subData.setSumNorm3(sum17);
                                    } else {
                                        subData.setSumIndex(sum15);
                                        subData.setSumIndex2(sum16);
                                        subData.setSumIndex3(sum17);
                                    }
                                    subData.setValuelast(count13);
                                    subData.setValuecurr(count14);
                                    subData.setValuenext(count15);
                                    subData.setValuenext2(count16);
                                    subData.setValuenext3(count17);
                                    if (parentProduct.equals(codeСonstruction)) {
                                        subData.setValueNorm(count15);
                                        subData.setValueNorm2(count16);
                                        subData.setValueNorm3(count17);
                                    } else {
                                        subData.setValueIndex(count15);
                                        subData.setValueIndex2(count16);
                                        subData.setValueIndex3(count17);
                                    }
                                    this.arraySubData.getSubData().add(subData);
                                }
                            }
                        } else {
                            eof = true;
                        }
                        indRow++;
                    }
                    break;
                }
                case tpMERT: {
                    int codeColumnMert = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.CODE_MERT_COLUMN));
                    int beginRowMert = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.BEGIN_ROW_MERT));
                    int idMertColumn = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.ID_MERT_COLUMN));
                    int valueMertColumn = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.VALUE_MERT_COLUMN));
                    int mertColumnIndexCountSettlementYear = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_COUNT_SETTLEMENT_YEAR));
                    int mertColumnIndexSumSettlementYear = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_SUM_SETTLEMENT_YEAR));
                    int mertColumnIndexCountPrognoz1 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_COUNT_PROGNOZ1));
                    int mertColumnIndexSumPrognoz1 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_SUM_PROGNOZ1));
                    int mertColumnIndexCountPrognoz2 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_COUNT_PROGNOZ2));
                    int mertColumnIndexSumPrognoz2 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_SUM_PROGNOZ2));
                    int mertColumnIndexCountPrognoz3 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_COUNT_PROGNOZ3));
                    int mertColumnIndexSumPrognoz3 = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.MERT_COLUMN_INDEX_SUM_PROGNOZ3));

                    this.badCode = new ArrayList();
                    eof = false;
                    indRow = beginRowMert;
                    ExtUtils.wLog("Reading MERT Data...");
                    while (!eof) {
                        HSSFRow row = worksheet.getRow(indRow);
                        if (row != null) {
                            HSSFCell codeCell = row.getCell(codeColumnMert);
                            if (codeCell != null) {

                                double indexCountSettlementYear = getValue(row.getCell(mertColumnIndexCountSettlementYear));
                                double indexSumSettlementYear = getValue(row.getCell(mertColumnIndexSumSettlementYear));
                                double indexCountPrognoz1 = getValue(row.getCell(mertColumnIndexCountPrognoz1));
                                double indexSumPrognoz1 = getValue(row.getCell(mertColumnIndexSumPrognoz1));
                                double indexCountPrognoz2 = getValue(row.getCell(mertColumnIndexCountPrognoz2));
                                double indexSumPrognoz2 = getValue(row.getCell(mertColumnIndexSumPrognoz2));
                                double indexCountPrognoz3 = getValue(row.getCell(mertColumnIndexCountPrognoz3));
                                double indexSumPrognoz4 = getValue(row.getCell(mertColumnIndexSumPrognoz3));


                                SubData subData = new SubData();
                                //Читаем данные из файла
                                String code = codeCell.getStringCellValue();
//                                System.out.println(code);
                                SubDataNsi subDataNsi = (SubDataNsi) dataNsi.get(code);
                                String parentProduct = code.replace(".", ";").split(";")[0];
                                int id = Integer.parseInt(row.getCell(idMertColumn).getStringCellValue());
                                HSSFCell cell = row.getCell(valueMertColumn);
                                double baseSum = 0.0D;
                                double baseCount = 0.0D;
                                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String[] vals = cell.getStringCellValue().split("\n");
                                    String val1 = vals[0].replace(",", ".").replaceAll(" ", "").replaceAll(" ", "");
                                    String val2 = vals[1].replace(",", ".").replaceAll(" ", "").replaceAll(" ", "");
                                    if (vals.length < 1) {
                                        continue;
                                    }
                                    try {
                                        baseSum = Double.parseDouble(val1);
                                        baseCount = Double.parseDouble(val2);
                                    } catch (Exception ex) {
                                        System.out.println("Error: Parse bad symbol: " + val2);
                                        continue;
                                    }
                                } else {
                                    this.badCode.add(code);
                                    baseSum = cell.getNumericCellValue();
                                }

                                //end-read
                                //расчет данных и получение объекта-результата
                                double sumSettlementYear = baseSum * indexSumSettlementYear;
                                double countSettlementYear = baseCount * indexCountSettlementYear;
                                double sumPrognoz1 = sumSettlementYear * indexSumPrognoz1;
                                double countPrognoz1 = countSettlementYear * indexCountPrognoz1;
                                double sumPrognoz2 = sumPrognoz1 * indexSumPrognoz2;
                                double countPrognoz2 = countPrognoz1 * indexCountPrognoz2;
                                double sumPrognoz3 = sumPrognoz2 * indexSumPrognoz4;
                                double countPrognoz3 = countPrognoz2 * indexCountPrognoz3;

                                subData.setId(Integer.valueOf(id));
                                if (subDataNsi != null) {
                                    subData.setProduct(subDataNsi.getProduct());
                                    subData.setPrognoz(subDataNsi.getPrognoz());
                                }
                                subData.setSumlast(baseSum);
                                subData.setSumcurr(sumSettlementYear);
                                subData.setSumnext(sumPrognoz1);
                                subData.setSumnext2(sumPrognoz2);
                                subData.setSumnext3(sumPrognoz3);
                                subData.setSumNorm(0.0D);
                                subData.setSumNorm2(0.0D);
                                subData.setSumNorm3(0.0D);
//                                        if (parentProduct.equals(codeСonstruction)) {
//                                            subData.setSumNorm(sum15);
//                                            subData.setSumNorm2(sum16);
//                                            subData.setSumNorm3(sum17);
//                                        } else {
//                                            subData.setSumIndex(sum15);
//                                            subData.setSumIndex2(sum16);
//                                            subData.setSumIndex3(sum17);
//                                        }
                                subData.setValuelast(baseCount);
                                subData.setValuecurr(countSettlementYear);
                                subData.setValuenext(countPrognoz1);
                                subData.setValuenext2(countPrognoz2);
                                subData.setValuenext3(countPrognoz3);
                                subData.setValueNorm(0.0D);
                                subData.setValueNorm2(0.0D);
                                subData.setValueNorm3(0.0D);
//                                        if (parentProduct.equals(codeСonstruction)) {
//                                            subData.setValueNorm(count15);
//                                            subData.setValueNorm2(count16);
//                                            subData.setValueNorm3(count17);
//                                        } else {
//                                            subData.setValueIndex(count15);
//                                            subData.setValueIndex2(count16);
//                                            subData.setValueIndex3(count17);
//                                        }
                                this.arraySubData.getSubData().add(subData);


                            } else {
                                eof = true;
                            }
                        } else {
                            eof = true;
                        }
                        indRow++;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Double calc(double argValue, String formula) {
        Double result = 0.0D;
        try {
            if (argValue != 0.0) {
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                String infix = formula;
                engine.put("arg1", argValue);
                result = (Double) engine.eval(infix);
                result = ExtUtils.round(result, 2);
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }

    private double getValue(HSSFCell cell) {
        double result = 0.0D;

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String tmp = cell.getStringCellValue().replace(",", ".").replaceAll(" ", "").replaceAll(" ", "");
            result = Double.parseDouble(tmp);
        } else
            result = cell.getNumericCellValue();

        return result;
    }
}
