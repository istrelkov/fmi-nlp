package org.fmi.nlpir.cli;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.fmi.nlpir.annotators.AbstractAnotator;
import org.fmi.nlpir.annotators.LocatedInAnnotator;
import org.fmi.nlpir.annotators.LocationAnotator;
import org.fmi.nlpir.annotators.ParseTreeAnotator;
import org.fmi.nlpir.annotators.RelationTripleAnnotator;
import org.fmi.nlpir.lucene.Indexer;
import org.fmi.nlpir.lucene.QueryParser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Strings;

public class Main {

	@Parameter(names = { "--file", "-f" })
	File file;
	@Parameter(names = { "--annotation",
			"-a" }, description = "Available attotators: relations, locations, tree, located-in")
	String annotation;

	@Parameter(names = { "--index", "-i" }, description = "index dir location")
	File index;
	
	@Parameter(names = { "--query", "-q" }, description = "query")
	String query;
	
	public static void main(String... args) throws IOException, ParseException {
		Main main = new Main();
		new JCommander(main, args);
		main.run();
	}

	public void run() throws IOException, ParseException {
		if (!Strings.isStringEmpty(annotation)) {
			AbstractAnotator annotator = null;
			switch (annotation) {
			case "relations":
				annotator = new RelationTripleAnnotator();
				break;
			case "locations":
				annotator = new LocationAnotator();
				break;
			case "tree":
				annotator = new ParseTreeAnotator();
				break;
			case "located-in":
				annotator = new LocatedInAnnotator();
				break;
			default:
				System.err.println("Annotator " + annotation + " not supported");
				return;
			}
			System.out.printf(annotator.getAnnotated(annotator.annotate(file)));
		} else if(file != null){
			new Indexer(index).index(file.listFiles());
		} else {
			Document[] search = new QueryParser().search(new Indexer(index), query);
			for (Document document : search) {
				IndexableField text = document.getField(Indexer.TEXT);
				if(text != null && text.stringValue() != null){
				System.out.println(text.stringValue().substring(0, 60));
				}
			}
		}
	}

}
