package com.compte.web;

import Jpa.Classes.Client;
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
    private Client client;

    public Client getLogin(String Login){
    return client = CompteEJB.getLogin(Login);
    }



}
