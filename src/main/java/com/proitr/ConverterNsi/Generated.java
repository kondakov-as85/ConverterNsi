package com.proitr.ConverterNsi;

import com.mycomp.ExtUtils.FileUtils;
import com.proitr.ConverterNsi.objects.ArraySubData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

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
        ParserXls parserXls = new ParserXls(pathXls2, 2, pathXmlFromNsi2, pathProp, null);
        ArraySubData data = parserXls.arraySubData;
        genereteXml(pathOut, "outMERT.xml", data);

        ExtUtils.wLog("Open input XLS file 2...");
        ParserXls parserXls2 = new ParserXls(pathXls, 1, pathXmlFromNsi1, pathProp, parserXls.badCode);
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

    public static String toXML (Object rootObject) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(ArraySubData.class);
        Marshaller marshaller = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();//
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(rootObject, stringWriter);
        String xml = stringWriter.toString();
        stringWriter.flush();
        stringWriter.close();
        return xml;
    }
}
