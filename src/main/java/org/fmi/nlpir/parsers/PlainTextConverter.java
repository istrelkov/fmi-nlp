package org.fmi.nlpir.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class PlainTextConverter {

    public String convert(String wikiMarkupText) {
        StringWriter writer = new StringWriter();

        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        builder.setEmitAsDocument(false);

        MarkupParser parser = new MarkupParser(new MediaWikiDialect());
        parser.setBuilder(builder);
        parser.parse(wikiMarkupText);

        final String htmlText = writer.toString();
        final StringBuilder cleaned = new StringBuilder();

        HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
                public void handleText(char[] data, int pos) {
                    cleaned.append(new String(data)).append('\n');
                }
        };
        try {
			new ParserDelegator().parse(new StringReader(htmlText), callback, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return cleaned.toString();
    }
}