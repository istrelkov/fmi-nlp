package org.fmi.nlpir.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.fmi.nlpir.annotators.RelationTripleAnnotator;

import edu.stanford.nlp.io.IOUtils;

public class Indexer {

	public static final String TEXT = "text";

	public static final String RELATIONS = "relations";

	public static final int BUFFER_SIZE = 4096;
	
	public static final Version VERSION = Version.LUCENE_40;

	private StandardAnalyzer analyzer;

	private Directory index;

	private IndexWriterConfig config;

	public Indexer(File intexDir) throws IOException {
		super();
		analyzer = new StandardAnalyzer(VERSION);
		config = new IndexWriterConfig(VERSION, analyzer);
		index = new NIOFSDirectory(intexDir);
	}

	public void index(File... files) {
		RelationTripleAnnotator relationTripleAnnotator = new RelationTripleAnnotator();
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		try (IndexWriter writer = new IndexWriter(index, config)) {
			for (File file : files) {
				System.out.println("Indexing "+file);
				Document doc = new Document();
				try (FileInputStream fis = new FileInputStream(file); FileChannel channel = fis.getChannel()) {
					buffer.clear();
					channel.read(buffer);
					String str = new String(buffer.array(), getCharset());
					System.out.println("Annotating "+file);
					String annotated = relationTripleAnnotator.getAnnotated(relationTripleAnnotator.annotate(str));
					doc.add(new TextField(RELATIONS, annotated, Field.Store.YES));
				}
				try(FileInputStream fis = new FileInputStream(file)) {
					doc.add(new TextField(TEXT, IOUtils.stringFromFile(file.getAbsolutePath()), Field.Store.YES));
					System.out.println("Adding index for "+file);
					writer.addDocument(doc);
					System.out.println("Done for "+file);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private Charset getCharset() {
		return Charset.forName("UTF-8");
	}
	
	public Directory getIndex() {
		return index;
	}
	
	public StandardAnalyzer getAnalyzer() {
		return analyzer;
	}

}
