package com.compte.web;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean(name = "CompteFicheBean")
@RequestScoped
public class FicheCompteMBean
{
    @EJB
    private CompteEJBRemote compteEJB;

    private Client client;

    /** Creates a new instance of FicheCompteMBean */
    public FicheCompteMBean()
    {
    }

    public Client getLogin(String login){
        return client = compteEJB.getLogin(login);
    }
}
