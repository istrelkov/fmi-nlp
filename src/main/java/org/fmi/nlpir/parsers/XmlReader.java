package org.fmi.nlpir.parsers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlReader {
	
	private PlainTextConverter plainTextConverter = new PlainTextConverter();

	public void readXmlFile(String fileName) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				private boolean isContent = false;
				private boolean isTitle =  false;
				private String title = null;
				private StringBuilder stringBuilder;

				@Override
				public void startElement(String uri, String localName,
						String tagName, Attributes attributes)
						throws SAXException {					
					if (tagName.equalsIgnoreCase("TEXT")) {
						//System.out.println("Finding text for " + title);
						isContent = true;
						stringBuilder = new StringBuilder();
					} else if (tagName.equalsIgnoreCase("TITLE")) {
						//System.out.println("Finding new title");
						isTitle = true;
					}
				}
				
				@Override
			    public void endElement(String uri, String localName, String tagName) throws SAXException {
					if (tagName.equalsIgnoreCase("TEXT")) {
			        	createTxtFile(stringBuilder.toString(), title);
			            stringBuilder = null;
			            isContent = false;
			            title = null;
			        } else if (tagName.equalsIgnoreCase("TITLE")) {
			        	isTitle = false;
			        }
			    }


				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (isContent) {
						stringBuilder.append(new String(ch, start, length));
					} else if (isTitle) {
						title = new String(ch, start, length);
					}
				}

			};

			saxParser.parse(fileName, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void createTxtFile(String text, String title) {
		title = title.replace(" ", "_").replace("/", "_");	
		File file = new File(title + ".txt");
		try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			writer.println(plainTextConverter.convert(removeCurlyBrackets(text)));
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String removeCurlyBrackets(String s) {
		//return s.replaceAll("\\s*\\{[^\\}]*\\}\\s*", " ");
	//	 String s = "stuff1 (foo1(bar1)foo2) stuff2 (bar2) stuff3";
	        String re = "\\{[^{}]*\\}";
	        Pattern p = Pattern.compile(re);
	        Matcher m = p.matcher(s);
	        while (m.find()) {
	            s = m.replaceAll("");
	            m = p.matcher(s);
	        }
	        return s;
	}

}