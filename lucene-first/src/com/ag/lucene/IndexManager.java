package com.ag.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;



public class IndexManager {
    private IndexWriter indexWriter;

    @Before
    public void init() throws Exception{
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath()),new IndexWriterConfig(new IKAnalyzer()));
    }

    @Test
    public void addDocument() throws Exception{
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath()),new IndexWriterConfig(new IKAnalyzer()));
        Document document = new Document();
        document.add(new TextField("name","新建的文件名字", Field.Store.YES));
        document.add(new TextField("content","新建的文件内容", Field.Store.NO));
        document.add(new StoredField("path","D:\\java\\temp-lucene"));
        indexWriter.addDocument(document);
        indexWriter.close();
    }
   /* @Test
    public void delAllDocument() throws Exception{
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath()),new IndexWriterConfig(new IKAnalyzer()));
        indexWriter.deleteAll();
        indexWriter.close();
    }*/
    /*@Test
    public void delDocumentByQuery() throws Exception{
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath()),new IndexWriterConfig(new IKAnalyzer()));
        indexWriter.deleteDocuments(new Term("name","apache"));
        indexWriter.close();
    }*/
   /* @Test
    public void updateDocument() throws Exception{
        Document document = new Document();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath()),new IndexWriterConfig(new IKAnalyzer()));
        document.add(new TextField("name1","更新过后的文档1", Field.Store.YES));
        document.add(new TextField("name2","更新过后的文档2", Field.Store.YES));
        document.add(new TextField("name3","更新过后的文档3", Field.Store.YES));
        indexWriter.updateDocument(new Term("name","spring"),document);
        indexWriter.close();
    }*/


}
