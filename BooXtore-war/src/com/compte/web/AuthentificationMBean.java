/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compte.web;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kevin
 */
@ManagedBean
@SessionScoped
public class AuthentificationMBean
{
    @EJB
    private CompteEJBRemote compteEJB;

    /** Creates a new instance of AuthentificationMBean */
    public AuthentificationMBean()
    {
    }

    private String login;
    private String mdp;
    private Client client;


    public String authentifiation() {

        if(compteEJB.authentification(login, mdp))
        {
            client = compteEJB.getLogin(login);
        }

        if (client == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mauvais Login et/ou mot de passe"));
            return (login = mdp = null);
        } else {
            return "/index.xhtml";
        }
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

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return client != null;
    }
}
