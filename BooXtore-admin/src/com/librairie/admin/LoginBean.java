/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable
{
    @EJB
    private CompteEJBRemote compteEJB;
    private String login;
    private String mdp;
    private Client admin;

    /**
     * Constructeur LoginBean
     */
    public LoginBean() throws IOException
    {
    }

    /**
     * Méthode permettant l'authentification
     * @param actionEvent
     * @throws IOException
     */
    public void authentification(ActionEvent actionEvent) throws IOException
    {
        //Si l'utilisateur est authentifié
        if (compteEJB.authentification(login, mdp))
        {
            //Récupération du compte
            this.admin = compteEJB.getLogin(login);
            //Vérification si l'utilisateur est administrateur
            if (admin.getCompte().getTypeCompte().equals("Administrateur"))
            {
                //Redirection vers la page d'index
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
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

    public Client getAdmin()
    {
        return admin;
    }

    public void setAdmin(Client admin)
    {
        this.admin = admin;
    }
}
