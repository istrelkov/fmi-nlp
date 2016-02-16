package org.fmi.nlpir.cli;

import java.io.File;

import org.fmi.nlpir.annotators.AbstractAnotator;
import org.fmi.nlpir.annotators.LocatedInAnnotator;
import org.fmi.nlpir.annotators.LocationAnotator;
import org.fmi.nlpir.annotators.ParseTreeAnotator;
import org.fmi.nlpir.annotators.RelationTripleAnnotator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

	@Parameter(names = { "--file", "-f" }, required = true)
	File file;
	@Parameter(names = { "--annotation", "-a"}, required = true, description= "Available attotators: relations, locations, tree, located-in")
	String annotation;

	public static void main(String... args) {
		Main main = new Main();
		new JCommander(main, args);
		main.run();
	}

	public void run() {
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
	}

}
