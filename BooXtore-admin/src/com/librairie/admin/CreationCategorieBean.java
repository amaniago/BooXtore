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
@ManagedBean (name="CreationCategorieBean")
@RequestScoped
public class CreationCategorieBean implements Serializable
{
    @EJB
    private LibrairieEJBRemote librairieEJB;
    private String nomCategorie;

    /**
     * Constructeur CreationCategorieBean
     * @throws IOException
     */
    public CreationCategorieBean() throws IOException
    {
        //Verification si la session a été démarrée
        LoginBean login = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
        if (login.getAdmin() == null)
        {
            //Redirection vers l'authentification si l'utilisateur n'est pas authentifié
            FacesContext.getCurrentInstance().getExternalContext().redirect("authentification.xhtml");
        }
    }

    /**
     * Méthode de création d'une catégorie
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
