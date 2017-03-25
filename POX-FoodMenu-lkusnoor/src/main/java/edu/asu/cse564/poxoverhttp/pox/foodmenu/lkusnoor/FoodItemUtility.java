/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.poxoverhttp.pox.foodmenu.lkusnoor;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Lucky
 */
public class FoodItemUtility {
    
    private static int mGeneratedId = 0;
    //private final String mFilepath = "FoodItemData.xml";
    private static int alreadyExistId;
    private boolean isAdd(String content){
        if(content.startsWith("<NewFoodItems")){
            return true;
        }return false;
    }
    
    private boolean isGet(String content){
        if(content.startsWith("<SelectedFoodItems")){
            return true;
        }return false;
    }
    public int verifyAddOrGet(String content){
        int result = GlobalConstants.Invalid;
        if(isAdd(content)){
            result = GlobalConstants.addItem;
         }else if(isGet(content)){
            result = GlobalConstants.getItem;
        }   
        return result;
    }

    String addFoodItem(String content) throws SAXException, IOException, ParserConfigurationException {
        String name, category, country, description, price, id;
       
        File file = new File(GlobalConstants.mFilePath);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document dataBaseDoc = docBuilder.parse(file);
        Node foodItemData = dataBaseDoc.getElementsByTagName("FoodItemData").item(0);
        
        Document addItemDoc;
        try{
            addItemDoc = getDocument(content);
        }catch (Exception ex) {
            return "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
        }
        StringBuilder itemAddedResponse = new StringBuilder();
        itemAddedResponse.append("<FoodItemAdded xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n");
        
        StringBuilder itemExistResponse = new StringBuilder();
        itemExistResponse.append("<FoodItemExists xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n");
        
        boolean isAdded = false;
        boolean isExists = false;
        
        NodeList addItemNodeList = addItemDoc.getElementsByTagName("FoodItem");
        for(int i=0; i < addItemNodeList.getLength(); i++){
            Element addItemNode = (Element)addItemNodeList.item(i);
            name = addItemNode.getElementsByTagName("name").item(0).getTextContent();
            category = addItemNode.getElementsByTagName("category").item(0).getTextContent();
            country = addItemNode.getAttribute("country");
            description = addItemNode.getElementsByTagName("description").item(0).getTextContent();
            price = addItemNode.getElementsByTagName("price").item(0).getTextContent();
            
            boolean alreadyExists = checkifAlreadyExists(dataBaseDoc,name,category);
            if(alreadyExists){
                itemExistResponse.append("  <FoodItemId>"+ alreadyExistId +"</FoodItemId>\n");
                isExists = true;
            }else{
                isAdded = true;
                Element FoodItem = dataBaseDoc.createElement("FoodItem");
                foodItemData.appendChild(FoodItem);

                Attr attr = dataBaseDoc.createAttribute("country");
                attr.setValue(country);
                FoodItem.setAttributeNode(attr);

                Element idNode = dataBaseDoc.createElement("id");
                idNode.appendChild(dataBaseDoc.createTextNode(String.valueOf(mGeneratedId)));
                FoodItem.appendChild(idNode);

                Element nameNode = dataBaseDoc.createElement("name");
                nameNode.appendChild(dataBaseDoc.createTextNode(name));
                FoodItem.appendChild(nameNode);

                Element descriptionNode = dataBaseDoc.createElement("description");
                descriptionNode.appendChild(dataBaseDoc.createTextNode(description));
                FoodItem.appendChild(descriptionNode);

                Element categoryNode = dataBaseDoc.createElement("category");
                categoryNode.appendChild(dataBaseDoc.createTextNode(category));
                FoodItem.appendChild(categoryNode);

                Element priceNode = dataBaseDoc.createElement("price");
                priceNode.appendChild(dataBaseDoc.createTextNode(price));
                FoodItem.appendChild(priceNode);
                foodItemData.appendChild(FoodItem); 
                
                itemAddedResponse.append("  <FoodItemId>"+ String.valueOf(mGeneratedId) +"</FoodItemId>\n");
            }
        }
        itemAddedResponse.append("</FoodItemAdded>\n");
        itemExistResponse.append("</FoodItemExists>\n");
        
        try{
            // write to xml 
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(dataBaseDoc);
            StreamResult result = new StreamResult(new File(GlobalConstants.mFilePath));
            transformer.transform(source, result);
        }catch (Exception e){
            e.printStackTrace();
        }
        String result = "";
        if(isAdded){
            result +=  itemAddedResponse.toString();
        }
        if(isExists){
            result += itemExistResponse.toString();
        }
        return result;
    }

    String getFoodItem(String content) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        
        File file = new File(GlobalConstants.mFilePath);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document dataBaseDoc = docBuilder.parse(file);
        
        Document getItemDoc;
        try{
            getItemDoc = getDocument(content);
        }catch (Exception ex) {
            return "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("<RetrievedFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n");
        
        NodeList getItemNodeList = getItemDoc.getElementsByTagName("FoodItemId");
        for(int i=0; i < getItemNodeList.getLength(); i++){
            Element foodItemId = (Element)getItemNodeList.item(i);
            String id = foodItemId.getTextContent();
            
            String matchedNode = checkIfIdExists(dataBaseDoc,id);
            if(matchedNode!=null){
                result.append("   "+matchedNode);
            }else{
                String invalidId = "     <InvalidFoodItem>\n" +
                                  "         <FoodItemId>"+id+"</FoodItemId>\n" +
                                  "     </InvalidFoodItem>\n";
                    result.append(invalidId);
            }
        }
        
        result.append("</RetrievedFoodItems>\n");
        return result.toString();
    }

    private Document getDocument(String content) throws SAXException, IOException, ParserConfigurationException {
         DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         InputSource is = new InputSource();
         is.setCharacterStream(new StringReader(content));
         Document doc = db.parse(is);
         doc.getDocumentElement().normalize();
         return doc;
    }

    private boolean checkifAlreadyExists(Document dataBaseDoc, String name, String category) {
        NodeList dbItemNodeList = dataBaseDoc.getElementsByTagName("FoodItem");
        for(int i=0; i < dbItemNodeList.getLength(); i++){
            Element addItemNode = (Element)dbItemNodeList.item(i);
            String dbname = addItemNode.getElementsByTagName("name").item(0).getTextContent();
            String dbcategory = addItemNode.getElementsByTagName("category").item(0).getTextContent();
            String id = addItemNode.getElementsByTagName("id").item(0).getTextContent();
            if(Integer.valueOf(id)>= mGeneratedId){
                mGeneratedId = Integer.valueOf(id)+1;
            }
            System.out.println("name :"+name+ " dbname :"+dbname);
            if(name.equalsIgnoreCase(dbname) && category.equalsIgnoreCase(dbcategory)){
                alreadyExistId = Integer.valueOf(id);
                return true;
            }
        }
        return false;
     }

    private String checkIfIdExists(Document dataBaseDoc, String id) throws TransformerConfigurationException, TransformerException {
    NodeList dbItemNodeList = dataBaseDoc.getElementsByTagName("FoodItem");
        for(int i=0; i < dbItemNodeList.getLength(); i++){
            Element addItemNode = (Element)dbItemNodeList.item(i);
            String old_id = addItemNode.getElementsByTagName("id").item(0).getTextContent();
            
            if(old_id.equalsIgnoreCase(id)){
                DOMSource src = new DOMSource();
                StringWriter mStringWriter = new StringWriter();
                StreamResult mStreamResult = new StreamResult(mStringWriter);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                src.setNode(addItemNode);
                transformer.transform(src, mStreamResult);
                return mStringWriter.toString()+"\n";
            }
        }
        return null;
    }
}
