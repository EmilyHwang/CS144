package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

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
import org.xml.sax.InputSource;
import org.xml.sax.ErrorHandler;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
			String itemId = request.getParameter("id");

      if (itemId == "") {
        request.setAttribute("empty", true);
      } else {
        request.setAttribute("empty", false);

  			AuctionSearchClient newSearch = new AuctionSearchClient();
  			String xmlResult = newSearch.getXMLDataForItemId(itemId);

        if (xmlResult == "") {
          request.setAttribute("not_found", true);
        } else {
          request.setAttribute("not_found", false);

    			// We are going to use Project 2 to parse the xml string
    			Document doc = null;
    			DocumentBuilder builder = null;

    			try {
    				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    				factory.setValidating(false);
    				factory.setIgnoringElementContentWhitespace(true);      
    				builder = factory.newDocumentBuilder();
    				InputSource is = new InputSource(new StringReader(xmlResult));
    				doc = builder.parse(is);
    			} catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
          } catch (SAXException e) {
            System.out.println("Parsing error on file ");
            e.printStackTrace();
            System.exit(3);
          } catch (ParserConfigurationException e) {
          	System.out.println("Parsing configuration error");
          	e.printStackTrace();
            System.exit(3);
          }

          Item item = parseItem(doc);
          request.setAttribute("item", item);        
        }
      }
			request.getRequestDispatcher("/ItemResult.jsp").forward(request, response);
    }

    private Item parseItem (Document doc) {
    	Element root = doc.getDocumentElement();

			// Now use the Element function to retrieve all value 
			String name = getElementTextByTagNameNR(root, "Name");
			String id = root.getAttributes().getNamedItem("ItemID").getNodeValue();
			String currently = getElementTextByTagNameNR(root, "Currently");
			String first_bid = getElementTextByTagNameNR(root, "First_Bid");
			String buy_price = getElementTextByTagNameNR(root, "Buy_Price");
			String num_bids = getElementTextByTagNameNR(root, "Number_of_Bids");
			String country = getElementTextByTagNameNR(root, "Country");
			String started = getElementTextByTagNameNR(root, "Started");
			String ends = getElementTextByTagNameNR(root, "Ends");
			String description = getElementTextByTagNameNR(root, "Description");
			
			ArrayList<String> location = new ArrayList();
			location.add(getElementTextByTagNameNR(root, "Location"));
			if (getElementByTagNameNR(root, "Location").hasAttribute("Longitude")){
        location.add(getElementByTagNameNR(root, "Location").getAttributes().getNamedItem("Latitude").getNodeValue());
        location.add(getElementByTagNameNR(root, "Location").getAttributes().getNamedItem("Longitude").getNodeValue());
      } else {
        location.add("");
        location.add("");
      } 

			ArrayList<String> seller = new ArrayList();
			seller.add(getElementByTagNameNR(root, "Seller").getAttributes().getNamedItem("UserID").getNodeValue());
			seller.add(getElementByTagNameNR(root, "Seller").getAttributes().getNamedItem("Rating").getNodeValue());
			
			ArrayList<ArrayList<String>> bids = parseBids(getElementsByTagNameNR(getElementByTagNameNR(root, "Bids"), "Bid"));

    	Item item = new Item(name, id, currently, first_bid, buy_price, num_bids,
													 location, country, started, ends, 
													 seller, description, bids);

    	return item;
    }

    private ArrayList<ArrayList<String>> parseBids (Element[] bids){
    	ArrayList<ArrayList<String>> result = new ArrayList();

    	for (int i = 0; i < bids.length; i++){
    		Element currentBid = bids[i];
    		Element currentBidder = getElementByTagNameNR(currentBid, "Bidder");

    		ArrayList<String> temp = new ArrayList();
    		temp.add(currentBidder.getAttributes().getNamedItem("UserID").getNodeValue());
    		temp.add(currentBidder.getAttributes().getNamedItem("Rating").getNodeValue());
    		temp.add(getElementTextByTagNameNR(currentBidder, "Location"));
    		temp.add(getElementTextByTagNameNR(currentBidder, "Country"));
    		temp.add(getElementTextByTagNameNR(currentBid, "Time"));
    		temp.add(getElementTextByTagNameNR(currentBid, "Amount"));

    		result.add(temp);
    	}

    	return result;
    }

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

}

