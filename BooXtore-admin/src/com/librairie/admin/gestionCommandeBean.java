/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.CommandeEJBRemote;
import Jpa.Classes.Commande;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Kevin
 */
@ManagedBean (name="gestionCommandeBean")
@ViewScoped
public class gestionCommandeBean
{
    @EJB
    private CommandeEJBRemote commandeEJB;

    public List<Commande> getAllCommande()
    {
        return commandeEJB.getCommandes();
    }
    /** Creates a new instance of gestionCommandeBean */
    public gestionCommandeBean()
    {
    }
}
