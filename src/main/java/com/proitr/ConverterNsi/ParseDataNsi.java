package com.proitr.ConverterNsi;

import com.mycomp.ExtUtils.UParser;
import com.proitr.ConverterNsi.objects.SubDataNsi;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class ParseDataNsi
{
	public Map<String, SubDataNsi> data = null;

	public ParseDataNsi(String pathFile) {
		try { this.data = new HashMap();
			UParser parser = new UParser(pathFile, true);
			Element rootElement = parser.getRootel();
			Node products = UParser.searchNode(rootElement, "products");
			Node product = products.getFirstChild();
			do {
				if (product.getNodeName().equals("product")) {
					String code = UParser.searchNode(product, "code").getTextContent();
					if (!code.equals("")) {
						String id = UParser.searchNode(product, "id").getTextContent();
						String prognoz = UParser.searchNode(product, "prognoz").getTextContent();
						String parent = UParser.searchNode(product, "parent").getTextContent();
						SubDataNsi sData = new SubDataNsi(Integer.parseInt(id), Integer.parseInt(prognoz), Integer.parseInt(parent));
						this.data.put(code, sData);
					}
				}
				product = product.getNextSibling();
			}while (product != null);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
