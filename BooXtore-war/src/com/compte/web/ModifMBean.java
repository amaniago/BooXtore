package com.compte.web;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Kevin
 */
@ManagedBean
@RequestScoped
public class ModifMBean
{
    @EJB
    private CompteEJBRemote compteEJB;


    /** Creates a new instance of ModifMBean */
    public ModifMBean()
    {
    }

}
