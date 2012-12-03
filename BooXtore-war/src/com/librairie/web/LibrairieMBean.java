package com.librairie.web;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import Jpa.Classes.Livre;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "LibrairieBean")
@ViewScoped
public class LibrairieMBean implements Serializable
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    private List<Livre> top;
    private List<Categorie> categories;
    private List<Livre> livres;
    private Livre livre;

    public List<Livre> getTop()
    {
        return top = librairieEJB.getTop10();
    }

    public List<Livre> getLivres()
    {
        return livres = librairieEJB.getLivres();
    }

    public Livre getLivre(int idLivre){
        return livre = librairieEJB.getLivre(idLivre);
    }

    public List<Categorie> getCategories()
    {
        return categories = librairieEJB.getCategories();
    }

    /**
     * Constructeur du bean manager
     */
    public LibrairieMBean()
    {
    }

    public boolean isStock(Livre l)
    {
        String idEtat = l.getEtatLivre().getIdEtatLivre();
        return idEtat.equals("N") || idEtat.equals("S");
    }
}