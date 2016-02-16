package org.fmi.nlpir.annotators;

import java.io.File;
import java.util.Properties;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public abstract class AbstractAnotator {
	
	private final Properties props;
	private final StanfordCoreNLP pipeline;

	public AbstractAnotator(Properties props) {
		super();
		this.props = props;
		pipeline = new StanfordCoreNLP(props);
	}
	
	public AbstractAnotator() {
		this(new Properties(){
			private static final long serialVersionUID = 1L;
		{
	    setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner,parse,relation");
	    setProperty("relationFeatures", "arg_words,arg_type,dependency_path_lowlevel,dependency_path_words,surface_path_POS,entities_between_args,full_tree_path");
		}});
	}
	
	public Properties getProperties() {
		return props;
	}

	public Annotation annotate(File f) {
		Annotation annotation = new Annotation(IOUtils.stringFromFile(f.getAbsolutePath()));
		pipeline.annotate(annotation);
		return annotation;
	}
	
	public abstract String getAnnotated(Annotation annotation);
}
