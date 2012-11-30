/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compte.web;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

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
    private String login;
    private String mdp;
    private Client client;

    /** Creates a new instance of loginBean */
    public AuthentificationMBean()
    {
    }

    public void authentification(ActionEvent actionEvent) throws IOException
    {
            this.client = compteEJB.getLogin(login);

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
}
