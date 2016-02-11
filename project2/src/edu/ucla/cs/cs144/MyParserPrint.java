/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParserPrint {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        Element root = doc.getDocumentElement();
        Element[] itemArr = getElementsByTagNameNR(root, "Item");

        File itemFile = new File ("item.csv");
        File sellerFile = new File ("seller.csv");
        File bidderFile = new File ("bidder.csv");
        File bidFile = new File ("bid.csv");
        File categoryFile = new File ("category.csv");

        try {
            PrintWriter itemWriter = new PrintWriter(new BufferedWriter(new FileWriter(itemFile, true)));
            PrintWriter sellerWriter = new PrintWriter(new BufferedWriter(new FileWriter(sellerFile, true)));
            PrintWriter bidderWriter = new PrintWriter(new BufferedWriter(new FileWriter(bidderFile, true)));
            PrintWriter bidWriter = new PrintWriter(new BufferedWriter(new FileWriter(bidFile, true)));
            PrintWriter categoryWriter = new PrintWriter(new BufferedWriter(new FileWriter(categoryFile, true)));

            // Now ItemArr is an array of element with item tag.
            // We want to get individual attributes and write to 5 different csv file
            for (int i = 0; i < itemArr.length; i++){
                Element currentItem = itemArr[i];

                // First we insert the information to the Item Table
                String itemRow = "";
                String itemID = currentItem.getAttributes().getNamedItem("ItemID").getNodeValue();

                itemRow += itemID + "*|*"
                        + getElementTextByTagNameNR(currentItem, "Name") + "*|*"
                        + strip(getElementTextByTagNameNR(currentItem, "Currently")) + "*|*"
                        + strip(getElementTextByTagNameNR(currentItem, "First_Bid")) + "*|*"
                        + strip(getElementTextByTagNameNR(currentItem, "Buy_Price")) + "*|*"
                        + getElementTextByTagNameNR(currentItem, "Number_of_Bids") + "*|*"
                        + getElementTextByTagNameNR(currentItem, "Location") + "*|*";

                if (getElementByTagNameNR(currentItem, "Location").hasAttribute("Longitude")){
                    itemRow += getElementByTagNameNR(currentItem, "Location").getAttributes().getNamedItem("Latitude").getNodeValue() + "*|*";
                    itemRow += getElementByTagNameNR(currentItem, "Location").getAttributes().getNamedItem("Longitude").getNodeValue() + "*|*";
                } else {
                    itemRow += "*|**|*";
                } 

                // Format the date to SQL format
                String started = getElementTextByTagNameNR(currentItem, "Started");
                String ends = getElementTextByTagNameNR(currentItem, "Ends");
                
      
                Date date = new SimpleDateFormat ("MMM-dd-yy HH:mm:ss").parse(started);
                started = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format(date);
                    
                date = new SimpleDateFormat ("MMM-dd-yy HH:mm:ss").parse(ends);
                ends = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format(date);

                itemRow += getElementTextByTagNameNR(currentItem, "Country") + "*|*"
                        + started + "*|*"
                        + ends + "*|*"
                        + getElementByTagNameNR(currentItem, "Seller").getAttributes().getNamedItem("UserID").getNodeValue() + "*|*";
                String description = getElementTextByTagNameNR(currentItem, "Description");    
		if(description.length() > 4000) {
			description = description.substring(0, 4000);
		}

		itemRow += description;

                itemWriter.println (itemRow);  

                // Second, we deal with the Seller table
                String sellerRow = "";
                
                sellerRow += getElementByTagNameNR(currentItem, "Seller").getAttributes().getNamedItem("UserID").getNodeValue() + "*|*";
                sellerRow += getElementByTagNameNR(currentItem, "Seller").getAttributes().getNamedItem("Rating").getNodeValue();

                sellerWriter.println (sellerRow);

                // Third, the Bid Table and the Bidder Table
                Element[] itemBids = getElementsByTagNameNR(getElementByTagNameNR(currentItem, "Bids"), "Bid");

                for (int k = 0; k < itemBids.length; k++){
                    String bidRow = "";
                    String bidderRow = "";
                    Element currentBid = itemBids[k];
                    Element currentBidder = getElementByTagNameNR(currentBid, "Bidder");

                    String bidderID = currentBidder.getAttributes().getNamedItem("UserID").getNodeValue();
                    bidderRow += bidderID + "*|*" 
                                + currentBidder.getAttributes().getNamedItem("Rating").getNodeValue() + "*|*" 
                                + getElementTextByTagNameNR(currentBidder, "Location") + "*|*"
                                + getElementTextByTagNameNR(currentBidder, "Country") + "";

                    String bidTime = getElementTextByTagNameNR(currentBid, "Time");
                
                   
                    date = new SimpleDateFormat ("MMM-dd-yy HH:mm:ss").parse(bidTime);
                    bidTime = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format(date);

                    bidRow += itemID + "*|*"  + bidderID + "*|*" 
                            + bidTime + "*|*" 
                            + strip(getElementTextByTagNameNR(currentBid, "Amount")) + "";
                    
                    bidderWriter.println (bidderRow);
                    bidWriter.println (bidRow);
                }

                // Finally, the itemToCategory table
                Element[] itemCategories = getElementsByTagNameNR(currentItem, "Category");

                for (int k = 0; k < itemCategories.length; k++){
                    String catRow = itemID + "*|*" + getElementText(itemCategories[k]);
                    categoryWriter.println(catRow);
                }

            }        

            itemWriter.close();
            sellerWriter.close();
            bidderWriter.close();
            bidWriter.close();
            categoryWriter.close();
        } catch (FileNotFoundException e) {
        	System.out.println("Unable to find file");
        } catch (IOException e){
        	System.out.println("IOException");
        } catch (ParseException e){
            System.out.println("Can't parse this date!");
            System.exit(3);
        }
        
        /**************************************************************/
        
        recursiveDescent(doc, 0);
    }
    
    public static void recursiveDescent(Node n, int level) {
        // adjust indentation according to level
        for(int i=0; i<4*level; i++)
            System.out.print(" ");
        
        // dump out node name, type, and value  
        String ntype = typeName[n.getNodeType()];
        String nname = n.getNodeName();
        String nvalue = n.getNodeValue();
        
        System.out.println("Type = " + ntype + ", Name = " + nname + ", Value = " + nvalue);
        
        // dump out attributes if any
        org.w3c.dom.NamedNodeMap nattrib = n.getAttributes();
        if(nattrib != null && nattrib.getLength() > 0)
            for(int i=0; i<nattrib.getLength(); i++)
                recursiveDescent(nattrib.item(i),  level+1);
        
        // now walk through its children list
        org.w3c.dom.NodeList nlist = n.getChildNodes();
        
        for(int i=0; i<nlist.getLength(); i++)
            recursiveDescent(nlist.item(i), level+1);
    }  
    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
