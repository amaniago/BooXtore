package com.librairie.web;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Livre;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "LibrairieBean")
@ViewScoped
public class LibrairieMBean
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    private List<Livre> top;

    public List<Livre> getTop()
    {
        top = librairieEJB.getTop10();
        return top;
    }

    /**
     * Constructeur du bean manager
     */
    public LibrairieMBean()
    {
    }
}