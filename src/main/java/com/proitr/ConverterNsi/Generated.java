package com.proitr.ConverterNsi;

import com.mycomp.ExtUtils.FileUtils;
import com.mycomp.ExtUtils.UParser;
import com.proitr.ConverterNsi.objects.ArraySubData;
import com.proitr.ConverterNsi.objects.SubData;

import java.io.IOException;
import java.util.List;

/**
 * Created by kondakov on 25.09.2015.
 */
public class Generated {

    public void work(String[] params) throws IOException {
        ExtUtils.wLog("Start Convert...");
//        String type = params[0];
//        int typeData = 0;
//        String fileOut = "";
//        if (type.equals("-p")) {
//            typeData = 1;
//            fileOut = "outPrognoz.xml";
//        } else if (type.equals("-m")) {
//            typeData = 2;
//            fileOut = "outMERT.xml";
//        }
        String pathXls = params[0];
        String pathXls2 = params[1];
        String pathXmlFromNsi1 = params[2];
        String pathXmlFromNsi2 = params[3];
        String pathProp = params[4];
        String paramPathOut = params[5];
        String pathOut = "./";
        if (paramPathOut != null) {
            pathOut = paramPathOut;
        }

        ExtUtils.wLog("Open input XLS file 2...");
        ParserXls parserXls = new ParserXls(pathXls2, 2, pathXmlFromNsi2, pathProp, null, pathOut);
        ArraySubData data = parserXls.arraySubData;
        genereteXml(pathOut, "outMERT.xml", data);

        ExtUtils.wLog("Open input XLS file 2...");
        ParserXls parserXls2 = new ParserXls(pathXls, 1, pathXmlFromNsi1, pathProp, parserXls.badCode, pathOut);
        ArraySubData data2 = parserXls2.arraySubData;

        genereteXml(pathOut, "outPrognoz.xml", data2);

    }

    private static void genereteXml(String pathXmlOut, String fileOut, ArraySubData data) {
        String resultOut = pathXmlOut + fileOut;
        try {
            ExtUtils.wLog("Create out file...");
            FileUtils.writeFile(resultOut, toXML(data), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtUtils.wLog("Out File: " + resultOut);
    }

    public static String toXML (ArraySubData rootObject) throws Exception {
//        JAXBContext jc = JAXBContext.newInstance(ArraySubData.class);
//        Marshaller marshaller = jc.createMarshaller();
//        StringWriter stringWriter = new StringWriter();//
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        marshaller.marshal(rootObject, stringWriter);
//        String xml = stringWriter.toString();
//        stringWriter.flush();
//        stringWriter.close();

        //это глупость какая-то. Почему-то важен порядок тегов!!!

        StringBuilder builder = new StringBuilder();
        builder.append("<forecast>");
        List<SubData> list = rootObject.getSubData();
        for (SubData subData:list){
            builder.append("<prognozdata>");
            builder.append("<id>" + subData.getId() + "</id>");
            builder.append("<prognoz>" + subData.getPrognoz() + "</prognoz>");
            builder.append("<product>" + subData.getProduct() + "</product>");
            builder.append("<sumlast>" + subData.getSumlast() + "</sumlast>");
            builder.append("<sumcurr>" + subData.getSumcurr() + "</sumcurr>");
            builder.append("<sumnext>" + subData.getSumnext() + "</sumnext>");
            builder.append("<sumNorm>" + subData.getSumNorm() + "</sumNorm>");
            builder.append("<sumIndex>" + subData.getSumIndex() + "</sumIndex>");
            builder.append("<sumPlan>" + subData.getSumPlan() + "</sumPlan>");
            builder.append("<sumFormula>" + subData.getSumFormula() + "</sumFormula>");
            builder.append("<sumOther>" + subData.getSumOther() + "</sumOther>");
            builder.append("<sumnext2>" + subData.getSumnext2() + "</sumnext2>");
            builder.append("<sumNorm2>" + subData.getSumNorm2() + "</sumNorm2>");
            builder.append("<sumIndex2>" + subData.getSumIndex2() + "</sumIndex2>");
            builder.append("<sumPlan2>" + subData.getSumPlan2() + "</sumPlan2>");
            builder.append("<sumFormula2>" + subData.getSumFormula2() + "</sumFormula2>");
            builder.append("<sumOther2>" + subData.getSumOther2() + "</sumOther2>");
            builder.append("<sumnext3>" + subData.getSumnext3() + "</sumnext3>");
            builder.append("<sumNorm3>" + subData.getSumNorm3() + "</sumNorm3>");
            builder.append("<sumIndex3>" + subData.getSumIndex3() + "</sumIndex3>");
            builder.append("<sumPlan3>" + subData.getSumPlan3() + "</sumPlan3>");
            builder.append("<sumFormula3>" + subData.getSumFormula3() + "</sumFormula3>");
            builder.append("<sumOther3>" + subData.getSumOther3() + "</sumOther3>");
            builder.append("<valuelast>" + subData.getValuelast() + "</valuelast>");
            builder.append("<valuecurr>" + subData.getValuecurr() + "</valuecurr>");
            builder.append("<valuenext>" + subData.getValuenext() + "</valuenext>");
            builder.append("<valueNorm>" + subData.getValueNorm() + "</valueNorm>");
            builder.append("<valueIndex>" + subData.getValueIndex() + "</valueIndex>");
            builder.append("<valuePlan>" + subData.getValuePlan() + "</valuePlan>");
            builder.append("<valueFormula>" + subData.getValueFormula() + "</valueFormula>");
            builder.append("<valueOther>" + subData.getValueOther() + "</valueOther>");
            builder.append("<valuenext2>" + subData.getValuenext2() + "</valuenext2>");
            builder.append("<valueNorm2>" + subData.getValueNorm2() + "</valueNorm2>");
            builder.append("<valueIndex2>" + subData.getValueIndex2() + "</valueIndex2>");
            builder.append("<valuePlan2>" + subData.getValuePlan2() + "</valuePlan2>");
            builder.append("<valueFormula2>" + subData.getValueFormula2() + "</valueFormula2>");
            builder.append("<valueOther2>" + subData.getValueOther2() + "</valueOther2>");
            builder.append("<valuenext3>" + subData.getValuenext3() + "</valuenext3>");
            builder.append("<valueNorm3>" + subData.getValueNorm3() + "</valueNorm3>");
            builder.append("<valueIndex3>" + subData.getValueIndex3() + "</valueIndex3>");
            builder.append("<valuePlan3>" + subData.getValuePlan3() + "</valuePlan3>");
            builder.append("<valueFormula3>" + subData.getValueFormula3() + "</valueFormula3>");
            builder.append("<valueOther3>" + subData.getValueOther3() + "</valueOther3>");
            builder.append("<base>" + subData.getBase() + "</base>");
            builder.append("<baseMethod>" + subData.getBaseMethod() + "</baseMethod>");
            builder.append("<comment>" + subData.getComment() + "</comment>");
            builder.append("</prognozdata>");
        }
        builder.append("</forecast>");
        return UParser.formatXml(builder.toString());
    }
}
