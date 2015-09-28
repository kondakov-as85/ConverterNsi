package com.proitr.ConverterNsi.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kondakov on 28.09.2015.
 */
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(name = "ArraySubData", propOrder = {"subData"})
@javax.xml.bind.annotation.XmlRootElement(name = "forecast")
public class ArraySubData {
    @javax.xml.bind.annotation.XmlElement(name = "prognozdata", nillable = true)
    protected java.util.List<SubData> subData;

    public ArraySubData() {
        this.subData = new ArrayList<SubData>();
    }

    public List<SubData> getSubData() {
        return subData;
    }

    public void setSubData(List<SubData> subData) {
        this.subData = subData;
    }
}
