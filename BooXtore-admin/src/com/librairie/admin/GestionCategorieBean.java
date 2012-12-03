/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
public class GestionCategorieBean implements Serializable
{
    private Categorie categorieModifie;
    private String nomCategorie;
    @EJB
    private LibrairieEJBRemote librairieEJB;

    /**
     * Constructeur GestionCategorieBean
     * @throws IOException
     */
    public GestionCategorieBean() throws IOException
    {
        //Verification si la session a été démarrée
        LoginBean login = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
        if (login.getAdmin() == null)
        {
            //Redirection vers l'authentification si l'utilisateur n'est pas authentifié
            FacesContext.getCurrentInstance().getExternalContext().redirect("authentification.xhtml");
        }
    }

    /**
     * Méthode qui retourne la liste des catégories
     * @return
     */
    public List<Categorie> getAllCategories()
    {
        return librairieEJB.getCategories();
    }

    /**
     * Méthode de modification d'une catégorie
     * @param actionEvent
     * @throws IOException
     */
    public void modifierCategorie(ActionEvent actionEvent) throws IOException
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
