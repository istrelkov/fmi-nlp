package org.fmi.nlpir.annotators;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.OpenIE;
import edu.stanford.nlp.naturalli.SentenceFragment;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class ParseTreeAnotator extends AbstractAnotator {

	@Override
	public String getAnnotated(Annotation annotation) {
		StringBuilder builder = new StringBuilder();
		for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			List<SentenceFragment> clauses = new OpenIE(getProperties()).clausesInSentence(sentence);
			for (SentenceFragment clause : clauses) {
				builder.append(clause.parseTree);
			}
		}
		return builder.toString();
	}

}
