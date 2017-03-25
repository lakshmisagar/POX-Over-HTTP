/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.poxoverhttp.pox.foodmenu.lkusnoor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author Lucky
 */
@Path("inventory")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() throws IOException {
        XmlCopyOperation s = XmlCopyOperation.getInstance();
    }

    /**
     * Retrieves representation of an instance of edu.asu.cse564.poxoverhttp.pox.foodmenu.lkusnoor.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getXml() {
        //TODO return proper representation object
        return "<html><body><h1>A Restfull Hello, World!!</body></h1></html>";
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response postXml(String content) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        FoodItemUtility foodItemUtility = new FoodItemUtility();
        int mAddOrGet = foodItemUtility.verifyAddOrGet(content);
        String mResponse = null;
        switch(mAddOrGet){
            case 1: mResponse = foodItemUtility.addFoodItem(content);
            break;
            case 2: mResponse = foodItemUtility.getFoodItem(content);
            break;
            case -1: mResponse = GlobalConstants.Invalid_Msg;
        }
        return Response.ok().entity(mResponse).build();
    }

    public static void CopyXML() throws FileNotFoundException, IOException {
       InputStream in = null;
       OutputStream os = null;

        try{
        in = FoodItemUtility.class.getClassLoader().getResourceAsStream("FoodItemData.xml");
        os = new FileOutputStream(GlobalConstants.mFilePath);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buf)) > 0) {
            os.write(buf, 0, bytesRead);
        }
        } finally {
            in.close();
            os.close();
        }
     }
}
