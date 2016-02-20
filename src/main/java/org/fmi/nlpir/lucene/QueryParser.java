package org.fmi.nlpir.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;

public class QueryParser {

	public Document[] search(Indexer index, String query) throws ParseException, IOException {
		String[] fileds = new String[] { Indexer.RELATIONS, Indexer.TEXT };
		BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD, BooleanClause.Occur.MUST };
		Query parsedQuery = 
//				new org.apache.lucene.queryparser.classic.QueryParser(Version.LUCENE_40, Indexer.TEXT, index.getAnalyzer()).parse(query);
				MultiFieldQueryParser.parse(Indexer.VERSION, query, fileds, flags, index.getAnalyzer());
		int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open(index.getIndex());
		reader.docFreq(new Term(Indexer.TEXT, "service"));
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(parsedQuery, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		System.out.println("Scored "+hits.length+" docs");
		Document[] result = new Document[hits.length];
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			result[i] = d;
		}
		return result;
	}

}
