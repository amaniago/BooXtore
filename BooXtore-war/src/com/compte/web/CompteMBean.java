package com.compte.web;

import Ejb.CompteEJBRemote;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Kevin
 */
@ManagedBean(name = "CompteBean")
@ViewScoped
public class CompteMBean
{

    @EJB
    private CompteEJBRemote CompteEJB;

    /** Creates a new instance of CompteMBean */
    public CompteMBean()
    {

    }

    public void inscription(String login, String mdp, String nom, String prenom, String mail, String adr, String codePostal, String ville){
        CompteEJB.inscription(login, mdp, nom, prenom, mail, adr, codePostal, ville);
    }

}
