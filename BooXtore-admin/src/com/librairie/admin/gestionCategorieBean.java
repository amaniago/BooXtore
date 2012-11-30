/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@RequestScoped
public class gestionCategorieBean
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    /** Creates a new instance of gestionCategorieBean */
    public gestionCategorieBean()
    {
    }

    public List<Categorie> getAllCategories()
    {
        return librairieEJB.getCategories();
    }

    public void onEdit(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Catégorie modifiée !");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
