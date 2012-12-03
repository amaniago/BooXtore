/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@RequestScoped
public class creationCategorieBean implements Serializable
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    private String nomCategorie;

    /** Creates a new instance of creationCategorieBean */
    public creationCategorieBean()
    {
        
    }

    /**
     * Permet de créer une catégorie
     * @param actionEvent
     * @throws IOException
     */
    public void creerCategorie(ActionEvent actionEvent) throws IOException
    {
        //Création de la catégorie en base
        librairieEJB.ajouterCategorie(nomCategorie);
        //Redirection vers la page de gestion des catégories
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
}
