/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.web;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Livre;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Guillaume
 */
    public class LibrairieMBean {
    @EJB
    private LibrairieEJBRemote librairieEJB;
    
    private List<Livre> top10;
    /**
     * Creates a new instance of LibrairieMBean
     */
    public LibrairieMBean() {
    }
    
    public List<Livre> getTop10(){
        top10 = librairieEJB.getTop10();
        return top10;
    }
}
