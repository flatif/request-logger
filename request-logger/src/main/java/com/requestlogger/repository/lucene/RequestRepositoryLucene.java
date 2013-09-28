package com.requestlogger.repository.lucene;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import lombok.SneakyThrows;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.requestlogger.Request;
import com.requestlogger.RequestRepository;

public class RequestRepositoryLucene<R extends Request> implements RequestRepository<R> {
	
	private Directory directory = new RAMDirectory();
	private IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, new StandardAnalyzer(Version.LUCENE_44));
	
	private final IndexWriter writer;
	private final IndexReader reader;
	private final IndexSearcher searcher;
	
	public RequestRepositoryLucene() {
		try {
			this.writer = new IndexWriter(directory, config);
			this.reader = DirectoryReader.open(directory);
			this.searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			throw new RuntimeException("Can't initialize lucene index", e);
		}
	}
	@SneakyThrows
	public void save(R request) {
		
//		Document document = new Document();
//		
//		document.add(new StringField("id", request.requestId(), Store.YES));
//		document.add(new StringField("executionResultOutcome", request.executionResult().getOutcome().name(), Store.YES));
//		document.add(new StringField("executionResultThrowable", request.executionResult().getThrowable(), Store.YES));
//		document.add(new LongField("date", request.date().getTime(), Store.YES));
//		document.add(new TextField("methodInvocations", mapper.writeValueAsString(request.methodInvocations()), Store.YES));
//		document.add(new StringField("userId", request.userDescriptor().getUserId(), Store.YES));
//		document.add(new StringField("httpMethod", request.method().name(), Store.YES));
//		document.add(new StringField("url", request.url(), Store.YES));
		
		writer.addDocument(converter().convert(request));
	}
	@SneakyThrows
	public List<R> findAll() {
		Query query = new MatchAllDocsQuery();
		return convert(searcher.search(query, 100));
	}
	
	private List<R> convert(TopDocs topDocs) throws IOException{
		final LinkedList<R> result = new LinkedList<R>();
		
		for (ScoreDoc doc : topDocs.scoreDocs) {
			result.add(convert(doc));
		}
		return result;
	}
	
	private R convert(ScoreDoc doc) throws IOException {
//		HttpRequest request = new HttpRequest();
		Document document = searcher.doc(doc.doc);
//		
//		request.date(new Date(Long.parseLong(document.get("date"))));
//		request.executionResult(new RequestExecutionResult(RequestExecutionOutcome.valueOf(document.get("executionResultOutcome")), document.get("executionResultThrowable")));
//		request.method(HttpMethod.valueOf(document.get("httpMethod")));
//		request.requestId(document.get("id"));
		return converter().toRequest(document);
	}
	
	private AbstractRequestConverter<R> converter() {
		return null;
	}

}
