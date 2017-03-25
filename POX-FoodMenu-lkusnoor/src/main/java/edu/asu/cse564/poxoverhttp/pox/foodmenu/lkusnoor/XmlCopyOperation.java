/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.poxoverhttp.pox.foodmenu.lkusnoor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucky
 */
public class XmlCopyOperation {
    private static XmlCopyOperation sInstance = null;

    static XmlCopyOperation getInstance() {
     if (sInstance == null) {
            sInstance = new XmlCopyOperation();
        }
        return sInstance;
    }
    private XmlCopyOperation(){
        try {
            GenericResource.CopyXML();
        } catch (IOException ex) {
            Logger.getLogger(XmlCopyOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
