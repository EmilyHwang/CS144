package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
    
    /** Creates a new instance of Indexer */
    public Indexer() {
    }
	
	private IndexWriter indexWriter = null;
    
    public void rebuildIndexes() throws IOException {

    Connection conn = null;
	Directory indexDir;
	IndexWriterConfig config;

        // create a connection to the database to retrieve Items from MySQL
	try {
	    conn = DbManager.getConnection(true);
	
		indexDir = FSDirectory.open(new File("/var/lib/lucene/index-directory/"));
		config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
		indexWriter = new IndexWriter(indexDir, config);
	
	//To index item, grab Item from database, get id, name, description. use ID and go to ItemCatagory
	
		Statement itemStmt = conn.createStatement();
		
		String itemQuery = "SELECT ItemID, Name, Description FROM Item";
		ResultSet itemRs = itemStmt.executeQuery(itemQuery);
		
		Statement categoryStmt = conn.createStatement();
		PreparedStatement preparedStmt = conn.prepareStatement("SELECT Category FROM ItemCategory WHERE ItemID = ?");
		
		String itemId;
		String name;
		String description;
		String categories = "";
	
		while(itemRs.next()) {
			itemId = itemRs.getString("ItemID");
			name = itemRs.getString("Name");
			description = itemRs.getString("Description");
			categories = "";
			
			//get categories
			preparedStmt.setString(1, itemId);
			ResultSet categoryRs = preparedStmt.executeQuery();
			
			while(categoryRs.next()){
				categories += categoryRs.getString("Category") + " ";
			}
			
			String fullSearchableText = name + " " + categories + " " + description;
			
			//System.out.println(itemId + " " + fullSearchableText);
			
			Document doc = new Document();
			doc.add(new StringField("itemID", itemId, Field.Store.YES));
			doc.add(new StringField("name", name, Field.Store.YES));
			doc.add(new TextField("fullSearchableText", fullSearchableText, Field.Store.NO));
			indexWriter.addDocument(doc);
			
			categoryRs.close();
		}
		
		itemRs.close();
		
		indexWriter.close();
		conn.close();
	} catch (SQLException ex) {
		System.err.println("SQLException: " + ex.getMessage());
	}
    }    

    public static void main(String args[]) {
        Indexer idx = new Indexer();
		try{
			idx.rebuildIndexes();
		} catch (IOException ex) {
			System.err.println("IOException: " + ex.getMessage());
		}
	 
		
    }   
}
