/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panier.web;

import Ejb.CommandeEJBRemote;
import com.compte.web.AuthentificationMBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kevin
 */
@ManagedBean
@SessionScoped

public class commandeMBean
{
    @EJB
    private CommandeEJBRemote CommandeEJB;

//    @ManagedProperty(value="#{AuthentificationMBean}")
//    private AuthentificationMBean authentificationMBean;
//
//    @ManagedProperty(value="#{PanierMBean}")
//    private PanierMBean panierMBean;


    /** Creates a new instance of commandeMBean */
    public commandeMBean()
    {
    }

    /**
     * Méthode qui permet d'effectuer la commande
     * @param client
     * @param panier
     */
    public void commande()
    {
        AuthentificationMBean a = (AuthentificationMBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authentificationMBean");
        PanierMBean p = (PanierMBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("panierMBean");
        //Ajout d'un compte utilisateur
        CommandeEJB.creationCommande(a.getClient(), p.getPanierCommande());
    }

//    public void setPanierMBean(PanierMBean panierMBean)
//    {
//        this.panierMBean = panierMBean;
//    }
//
//    public void setAuthentificationMBean(AuthentificationMBean authentificationMBean)
//    {
//        this.authentificationMBean = authentificationMBean;
//    }


}
