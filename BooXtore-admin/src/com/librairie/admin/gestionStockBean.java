/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Livre;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@ViewScoped
public class gestionStockBean
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    /** Creates a new instance of gestionStockBean */
    public gestionStockBean()
    {
    }

    public List<Livre> getAllLivres()
    {
        return librairieEJB.getLivres();
    }

    public void onEdit(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Quantité Modifiée");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
