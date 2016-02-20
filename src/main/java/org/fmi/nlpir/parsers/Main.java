package org.fmi.nlpir.parsers;

public class Main {
	public static void main(String args[]) {
		String xmlFile = "/home/mmiteva/Downloads/enwikivoyage-20151201-pages-meta-current.xml";
		XmlReader reader = new XmlReader();
		reader.readXmlFile(xmlFile);
//		String text = "This is ''italic'' and '''that''' is bold. \n"+
//                "=Header 1=\n"+
//                "a list: \n* item A \n* item B \n* item C";
//		PlainTextConverter converter = new PlainTextConverter();
//		System.out.println(converter.convert(text));
	}
}