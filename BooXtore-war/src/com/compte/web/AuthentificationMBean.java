/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compte.web;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class AuthentificationMBean implements Serializable
{
    @EJB
    private CompteEJBRemote compteEJB;

    //Propriétés de l'authentification
    private String login;
    private String mdp;
    private String adr;
    private Client client;

    /** Creates a new instance of AuthentificationMBean */
    public AuthentificationMBean()
    {
    }

    /**
     * Méthode d'authentification
     * @return
     * @throws IOException
     */
    public String authentification() throws IOException
    {
        //Vérification de l'existance d'un compte correspondant dans la base de donnée
        if (compteEJB.authentification(login, mdp))
        {
            //Connexion à la base
            client = compteEJB.getLogin(login);
        }

        if (client == null)
        {
            //Affichage d'un erreur en cas de mauvaise information de oonnexion
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mauvais Login et/ou mot de passe"));
            return (login = mdp = null);
        }
        else
        {
            return "/top10.xhtml";
        }
    }

     /**
     * Méthode de déconnexion
     * @return
     */
    public String logout()
    {
        //Déconnexion de la session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "top10?faces-redirect=true";
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getMdp()
    {
        return mdp;
    }

    public void setMdp(String mdp)
    {
        this.mdp = mdp;
    }

    public Client getClient()
    {
        return client;
    }

    public boolean isLoggedIn()
    {
            return client != null;
    }
}