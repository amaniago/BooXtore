/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@RequestScoped
public class gestionCategorieBean
{
    private Categorie categorieModifie;
    private String nomCategorie;

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

    public void changeCategorie(ActionEvent actionEvent) throws IOException
    {
        categorieModifie.setNomCategorie(nomCategorie);
        librairieEJB.modifierCategorie(categorieModifie);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestioncategorie.xhtml");
    }

    public String getNomCategorie()
    {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie)
    {
        this.nomCategorie = nomCategorie;
    }

    public Categorie getCategorieModifie()
    {
        return categorieModifie;
    }

    public void setCategorieModifie(Categorie categorieModifie)
    {
        this.categorieModifie = categorieModifie;
    }
}
