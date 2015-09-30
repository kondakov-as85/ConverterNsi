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
import java.util.Map;

public class ParserXls {
    public ArraySubData arraySubData = null;
    public static final int tpPrognoz = 1;
    public static final int tpMERT = 2;

    public ParserXls(String pathFile, int type, String pathDataNSI, String pathProp) {
        this.arraySubData = new ArraySubData();
        try {
            String codeСonstruction = ExtUtils.getProp(pathProp, PropConverter.CODE_CONSTRUCTION);

            int codeColumn = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.CODE_COLUMN));


            ExtUtils.wLog("Reading NSI Data...");
            Map dataNsi = new ParseDataNsi(pathDataNSI).data;

            FileInputStream fileInputStream = new FileInputStream(pathFile);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet worksheet = workbook.getSheetAt(0);
            boolean eof;
            int indRow;
            switch (type) {
                case tpPrognoz: {
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
                            HSSFCell codeCell = row.getCell(codeColumn);
                            if (codeCell != null) {
                                String code = codeCell.getStringCellValue();
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
                        } else {
                            eof = true;
                        }
                        indRow++;
                    }
                    break;
                }
                case tpMERT: {
                    int beginRowMert = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.BEGIN_ROW_MERT));
                    int idMertColumn = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.ID_MERT_COLUMN));
                    int valueMertColumn = Integer.parseInt(ExtUtils.getProp(pathProp, PropConverter.VALUE_MERT_COLUMN));

                    String fSum13 = ExtUtils.getFormula(pathProp, PropFormula.SUM13);
                    String fCount13 = ExtUtils.getFormula(pathProp, PropFormula.COUNT13);
                    String fSum15 = ExtUtils.getFormula(pathProp, PropFormula.SUM15);
                    String fCount15 = ExtUtils.getFormula(pathProp, PropFormula.SUM15);
                    String fSum16 = ExtUtils.getFormula(pathProp, PropFormula.SUM16);
                    String fCount16 = ExtUtils.getFormula(pathProp, PropFormula.SUM16);
                    String fSum17 = ExtUtils.getFormula(pathProp, PropFormula.SUM17);
                    String fCount17 = ExtUtils.getFormula(pathProp, PropFormula.SUM17);

                    eof = false;
                    indRow = beginRowMert;
                    ExtUtils.wLog("Reading MERT Data...");
                    while (!eof) {
                        HSSFRow row = worksheet.getRow(indRow);
                        if (row != null) {
                            HSSFCell codeCell = row.getCell(codeColumn);
                            if (codeCell != null) {
                                SubData subData = new SubData();
                                //Читаем данные из файла
                                String code = codeCell.getStringCellValue();
                                String parentProduct = code.replace(".", ";").split(";")[0];
                                int id = Integer.parseInt(row.getCell(idMertColumn).getStringCellValue());
                                HSSFCell cell = row.getCell(valueMertColumn);
                                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String[] vals = cell.getStringCellValue().split("\n");
                                    String val1 = vals[0].replace(",", ".").replaceAll(" ", "").replaceAll(" ", "");
                                    String val2 = vals[1].replace(",", ".").replaceAll(" ", "").replaceAll(" ", "");
                                    //end-read
                                    //расчет данных и получение объекта-результата
                                    if (vals.length > 1) {
                                        double sum14 = 0.0D;
                                        double count14 = 0.0D;
                                        try {
                                            sum14 = Double.parseDouble(val1);
                                            count14 = Double.parseDouble(val2);
                                        } catch (Exception ex) {
                                            System.out.println("Error: Parse bad symbol: " + val2);
                                            continue;
                                        }
                                        double sum13 = calc(sum14, fSum13);         //sum14 * 0.97D / 1.124D;
                                        double count13 = calc(count14, fCount13);   //count14 * 0.97D;
                                        double sum15 = calc(sum14, fSum15);         //sum14 * 1.05D * 0.947D;
                                        double count15 = calc(count14, fCount15);   //count14 * 1.05D;
                                        double sum16 = calc(sum15, fSum16);         //sum15 * 1.04D * 1.042D;
                                        double count16 = calc(count15, fCount16);   //count15 * 1.04D;
                                        double sum17 = calc(sum16, fSum17);         //sum16 * 1.03D * 0.987D;
                                        double count17 = calc(count16,fCount17);    //count16 * 1.03D;

                                        subData.setId(Integer.valueOf(id));
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
        Double result = null;
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            String infix = formula;
            engine.put("arg1", argValue);
            result = (Double) engine.eval(infix);
            result = ExtUtils.round(result, 2);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }
}
