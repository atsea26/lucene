package com.ag.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class LuceneFirst {
    @Test
    public void createIndex() throws Exception{
        //1.创建一个Director对象，指定索引库保存的位置
            //把索引库保存在内存中
        //Directory directory = new RAMDirectory();
            //把是索引库保存在磁盘中
        //2.基于DIrector对象创建一个indexWrite对象
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath()),new IndexWriterConfig(new IKAnalyzer()));
        //3.读取磁盘上的文件，对应每个文件创建一个文档对象
        File dir = new File("D:\\java\\java进阶\\讲义+笔记+资料\\流行框架\\61.会员版(2.0)-就业课(2.0)-Lucene\\87.lucene\\lucene\\02.参考资料\\searchsource");
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            String filePath = file.getPath();
            String fileContent = FileUtils.readFileToString(file, "utf-8");
            long fileSize = FileUtils.sizeOf(file);
            //创建Field
            Field fieldName = new TextField("name",fileName, Field.Store.YES);
//            Field fieldPath = new TextField("path",filePath, Field.Store.YES);
            Field fieldPath = new StoredField("path",filePath);
            Field fieldContent = new TextField("content",fileContent, Field.Store.YES);
//            Field fieldSize = new TextField("size",fileSize+"", Field.Store.YES);
            Field fieldSizeValue = new LongPoint("size",fileSize);
            Field fieldSizeStore = new StoredField("size",fileSize);
            //创建文档对象
            Document document = new Document();
            //想文档对象中添加域
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
//            document.add(fieldSize);
            document.add(fieldSizeValue);
            document.add(fieldSizeStore);
            //把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //关闭indexWriter对象
        indexWriter.close();
    }
    @Test
    public void searchIndex() throws Exception{
        //创建一个director对象，指向索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\java\\temp-lucene\\index").toPath());
        //创建一个indexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建一个indexSearch对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建一个query对象
        Query query = new TermQuery(new Term("name","spring"));
        //执行查询，得到一个topDocs对象
            //查询对象  查询结构返回最大记录数
        TopDocs topDocs = indexSearcher.search(query, 10);
        //查询结果总记录数
        System.out.println(topDocs.totalHits);
        //取文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc doc : scoreDocs) {
            int docid = doc.doc;
            Document document = indexSearcher.doc(docid);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println(document.get("content"));
            System.out.println("-------------------------------------------------------------------------");
        }
        indexReader.close();
    }

    @Test
    public void TestQueryParser() throws Exception{
        QueryParser queryParser = new QueryParser("name",new IKAnalyzer());
        Query query = queryParser.parse("lucene是一个java开发的全文检索工具包");
//        QueryResult(query);

    }





    @Test
    public void testTokenStream() throws Exception{
        //创建一个analyzer对象，StandardAnalyzer对象
//        Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //调用TokenStream方法获得一个TokenStream对象
        TokenStream tokenStream = analyzer.tokenStream("", "承冰新全文检索是将整本书java、整篇文章中的任意内容信息查找出来的检索，java。它可以根据需要获得全文中有关章、节、段、句、词等信息，计算机程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明该词在文章中出现的次数和位置，当用户查询时根据建立的索引查找，类似于通过字典的检索字表查字的过程。");
        //想tokenStream对象中设置一个引用，相当于一个指针
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //调用tokenStream对象的reset方法
        tokenStream.reset();
        //调用while方法遍历tokenStream
        while (tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }
        tokenStream.close();


    }


}
