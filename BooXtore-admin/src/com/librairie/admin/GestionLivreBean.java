/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import Jpa.Classes.Livre;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@ViewScoped
public class GestionLivreBean implements Serializable
{
    private Livre livreModifie;
    @EJB
    private LibrairieEJBRemote librairieEJB;

    /**
     * Constructeur GestionLivreBean
     * @throws IOException
     */
    public GestionLivreBean() throws IOException
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
     * Méthode de modification d'un livre
     * @param actionEvent
     * @throws IOException
     */
    public void modifierLivre(ActionEvent actionEvent) throws IOException
    {
        librairieEJB.modifierLivre(livreModifie);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestioncommande.xhtml");
    }

    /**
     * Liste des livres
     * @return
     */
    public List<Livre> getAllLivres()
    {
        return librairieEJB.getLivres();
    }

    /**
     * Liste des catégories
     * @return
     */
    public List<Categorie> getAllCategories()
    {
        return librairieEJB.getCategories();
    }

    public Livre getLivreModifie()
    {
        return livreModifie;
    }

    public void setLivreModifie(Livre livreModifie)
    {
        this.livreModifie = livreModifie;
    }
}
