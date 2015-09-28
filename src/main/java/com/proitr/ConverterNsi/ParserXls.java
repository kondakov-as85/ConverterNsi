package com.proitr.ConverterNsi;

import com.proitr.ConverterNsi.objects.ArraySubData;
import com.proitr.ConverterNsi.objects.SubData;
import com.proitr.ConverterNsi.objects.SubDataNsi;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.util.Map;

public class ParserXls {
    public ArraySubData arraySubData = null;
    private static final String codeСonstruction = "45";
    private final int beginRowPrognoz = 1;
    private final int beginRowMert = 6;
    private final int codeColumn = 0;
    private final int idPrognozColumn = 2;
    private final int idMertColumn = 4;
    private final int valueMertColumn = 7;
    public static final int tpPrognoz = 1;
    public static final int tpMERT = 2;

    public ParserXls(String pathFile, int type, String pathDataNSI) {
        this.arraySubData = new ArraySubData();
        try {

            ExtUtils.wLog("Reading NSI Data...");
            Map dataNsi = new ParseDataNsi(pathDataNSI).data;

            FileInputStream fileInputStream = new FileInputStream(pathFile);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet worksheet = workbook.getSheetAt(0);
            boolean eof;
            int indRow;
            switch (type) {
                case tpPrognoz:
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
                                SubDataNsi subDataNsi = (SubDataNsi) dataNsi.get(code);
                                Integer id = Integer.valueOf(Double.valueOf(row.getCell(idPrognozColumn).getNumericCellValue()).intValue());
                                double sum13 = row.getCell(3).getNumericCellValue();
                                double count13 = row.getCell(4).getNumericCellValue();
                                double sum14 = row.getCell(5).getNumericCellValue();
                                double count14 = row.getCell(6).getNumericCellValue();
                                double sum15 = row.getCell(7).getNumericCellValue();
                                double count15 = row.getCell(8).getNumericCellValue();
                                double sum16 = row.getCell(9).getNumericCellValue();
                                double count16 = row.getCell(10).getNumericCellValue();
                                double sum17 = row.getCell(11).getNumericCellValue();
                                double count17 = row.getCell(12).getNumericCellValue();
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
                                String parentProduct = code.replace(".", ";").split(";")[0];
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
                case tpMERT:
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
                                int id = Integer.parseInt(row.getCell(idMertColumn).getStringCellValue());
                                HSSFCell cell = row.getCell(valueMertColumn);
                                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String[] vals = cell.getStringCellValue().replaceAll("\n", ";").split(";");
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
                                        double sum13 = sum14 * 0.97D / 1.124D;
                                        double count13 = count14 * 0.97D;
                                        double sum15 = sum14 * 1.05D * 0.947D;
                                        double count15 = count14 * 1.05D;
                                        double sum16 = sum15 * 1.04D * 1.042D;
                                        double count16 = count15 * 1.04D;
                                        double sum17 = sum16 * 1.03D * 0.987D;
                                        double count17 = count16 * 1.03D;
                                        subData.setId(Integer.valueOf(id));
                                        String parentProduct = code.replace(".", ";").split(";")[0];
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
                                        if (code.equals("85.12.000")) {
                                            System.out.println("s13= " + sum13 + " | s14= " + sum14 + " | s15= " + sum15 + " | s16= " + sum16 + " | s17= " + sum17);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
