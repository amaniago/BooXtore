/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
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
public class gestionLivreBean
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    /** Creates a new instance of gestionLivreBean */
    public gestionLivreBean()
    {
    }

    public List<Livre> getAllLivres()
    {
        return librairieEJB.getLivres();
    }

    public List<Categorie> getAllCategories()
    {
        return librairieEJB.getCategories();
    }

    public void onEdit(RowEditEvent event)
    {

        FacesMessage msg = new FacesMessage("Livre modifié !");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
