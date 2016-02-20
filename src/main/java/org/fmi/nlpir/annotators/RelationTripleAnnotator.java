package org.fmi.nlpir.annotators;

import java.util.Collection;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class RelationTripleAnnotator extends AbstractAnotator {

	@Override
	public String getAnnotated(Annotation annotation) {
		StringBuilder builder = new StringBuilder();
		for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			// Get the OpenIE triples for the sentence
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// Print the triples
			for (RelationTriple triple : triples) {
				if (!triple.subjectHead().ner().equalsIgnoreCase("o")) {
					builder.append(triple.confidence + " | " + triple.subjectLemmaGloss() + " | "
							+ triple.relationLemmaGloss() + " | " + triple.objectLemmaGloss());
					builder.append('\n');
				}
			}
		}
		return builder.toString();
	}

}
