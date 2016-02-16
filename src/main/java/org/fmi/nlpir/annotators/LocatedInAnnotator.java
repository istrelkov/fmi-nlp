package org.fmi.nlpir.annotators;

import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ie.machinereading.structure.EntityMention;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.RelationMentionsAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class LocatedInAnnotator extends AbstractAnotator {

	@Override
	public String getAnnotated(Annotation annotation) {
		StringBuilder builder = new StringBuilder();
		for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			Collection<RelationMention> triples = sentence.get(RelationMentionsAnnotation.class);

			for (RelationMention relationMention : triples) {
				List<EntityMention> entityMentionArgs = relationMention.getEntityMentionArgs();
				if (entityMentionArgs.get(0).getType().equalsIgnoreCase("LOCATION")
						&& entityMentionArgs.get(1).getType().equalsIgnoreCase("LOCATION") &&
						relationMention.getTypeProbabilities().getCount("Located_In") > 0.001) {
					builder.append(relationMention);
				}
			}
		}
		return builder.toString();
	}

}
