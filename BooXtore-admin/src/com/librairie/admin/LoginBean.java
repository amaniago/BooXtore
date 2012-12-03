/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@SessionScoped
public class loginBean
{
    @EJB
    private CompteEJBRemote compteEJB;
    private String login;
    private String mdp;
    private Client admin;

    /** Creates a new instance of loginBean */
    public loginBean()
    {
    }

    public void authentification(ActionEvent actionEvent) throws IOException
    {
        if(compteEJB.authentification(login, mdp))
        {
            this.admin = compteEJB.getLogin(login);
            if(admin.getCompte().getTypeCompte().equals("Administrateur"))
            {
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
}
