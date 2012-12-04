/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.CommandeEJBRemote;
import Jpa.Classes.Commande;
import Jpa.Classes.EtatCommande;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean(name = "GestionCommandeBean")
@ViewScoped
public class GestionCommandeBean implements Serializable
{
    @EJB
    private CommandeEJBRemote commandeEJB;
    private String etatCommande;
    private Commande commandeModifie;

    @PostConstruct
    public void verificationAuthentification() throws IOException
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
     * Constructeur GestionCommandeBean
     * @throws IOException
     */
    public GestionCommandeBean()
    {
    }

    /**
     * Méthode qui retourne la liste des commandes
     * @return
     */
    public List<Commande> getAllCommande()
    {
        return commandeEJB.getCommandes();
    }

    /**
     * Méthode qui retourne la liste des états possible pour une commande
     * @return
     */
    public List<EtatCommande> getEtats()
    {
        return commandeEJB.getEtats();
    }

    /**
     * Méthode de modification d'une commande
     * @param actionEvent
     * @throws IOException
     */
    public void modifierEtatCommande(ActionEvent actionEvent) throws IOException
    {
        commandeEJB.setEtatCommande(commandeModifie, etatCommande);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestioncommande.xhtml");
    }

    public String getEtatCommande()
    {
        return etatCommande;
    }

    public void setEtatCommande(String etatCommande)
    {
        this.etatCommande = etatCommande;
    }

    public Commande getCommandeModifie()
    {
        return commandeModifie;
    }

    public void setCommandeModifie(Commande commande)
    {
        this.commandeModifie = commande;
    }
}
