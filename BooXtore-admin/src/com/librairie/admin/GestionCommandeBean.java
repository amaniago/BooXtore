/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.CommandeEJBRemote;
import Jpa.Classes.Commande;
import Jpa.Classes.EtatCommande;
import java.io.IOException;
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
@ManagedBean (name="gestionCommandeBean")
@ViewScoped
public class gestionCommandeBean
{
     /** Creates a new instance of gestionCommandeBean */
    public gestionCommandeBean()
    {
    }

    @EJB
    private CommandeEJBRemote commandeEJB;

    private String etatCommande;

    private Commande commandeModifie;

    public List<Commande> getAllCommande()
    {
        return commandeEJB.getCommandes();
    }

    public List<EtatCommande> getEtats()
    {
        return commandeEJB.getEtats();
    }

    public void changeEtatCommande(ActionEvent actionEvent) throws IOException
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
