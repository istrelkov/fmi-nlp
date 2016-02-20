package org.fmi.nlpir.annotators;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class LocationAnotator extends AbstractAnotator {

	@Override
	public String getAnnotated(Annotation annotation) {
		CRFClassifier<CoreMap> classifier = CRFClassifier.getDefaultClassifier();
//		Map<String, String> locations = new HashMap<>();
		StringBuilder builder = new StringBuilder();
		for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			builder.append(classifier.classifyWithInlineXML(sentence.toString()));
//			for (ListIterator<CoreLabel> iterator = sentence.get(TokensAnnotation.class).listIterator(); iterator
//					.hasNext();) {
//				CoreLabel token = iterator.next();
//				if(token.ner().equalsIgnoreCase("location")){
//					locations.put(token.originalText(), token.ner());
//				}
//				String type = token.get(NamedEntityTagAnnotation.class);
//				if (type.equalsIgnoreCase("location")) {
//					if (iterator.hasPrevious()) {
//						CoreLabel previous = iterator.previous();
//						if (previous.ner().equalsIgnoreCase("location")) {
//							builder.append(token.get(TextAnnotation.class));
//						} else {
//							locations.put(builder.toString(), type);
//							builder.setLength(0);
//						}
//					} else {
//					builder.append(token.get(TextAnnotation.class));
//					}
//				}
//			}
//		}
//		for(String location : locations.keySet()){
//			builder.append(location);
//			builder.append('\n');
		}
			
		return builder.toString();
	}

}
