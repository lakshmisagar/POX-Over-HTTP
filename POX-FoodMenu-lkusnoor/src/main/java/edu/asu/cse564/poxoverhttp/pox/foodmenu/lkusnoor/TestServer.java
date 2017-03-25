/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.poxoverhttp.pox.foodmenu.lkusnoor;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Lucky
 */
public class TestServer {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException{
    String content = "<NewFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n" +
"    <FoodItem country=\"GB\">\n" +
"        <name>Cornish Pasty</name>\n" +
"        <description>Tender cubes of steak, potatoes and swede wrapped in flakey short crust pastry.  Seasoned with lots of pepper.  Served with mashed potatoes, peas and a side of gravy</description>\n" +
"        <category>Dinner</category>\n" +
"        <price>15.95</price>\n" +
"    </FoodItem>\n" +
"</NewFoodItems >\n" +
"";
        FoodItemUtility foodItemUtility = new FoodItemUtility();
        int mAddOrGet = foodItemUtility.verifyAddOrGet(content);
        String mResponse = "";
        switch(mAddOrGet){
            case 1: mResponse += foodItemUtility.addFoodItem(content);
            break;
            case 2: mResponse += foodItemUtility.getFoodItem(content);
            break;
            case -1: mResponse += GlobalConstants.Invalid_Msg;
            break;
        }
        System.out.println(mResponse);
    } 
}
