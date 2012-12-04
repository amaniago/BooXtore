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

    //Déclaration des listes
    private List<Livre> top;
    private List<Categorie> categories;
    private List<Livre> livres;
    //Déclaration d'un livre
    private Livre livre;
    private int nb;

    /**
     * Constructeur du bean manager
     */
    public LibrairieMBean()
    {
    }

    /**
     * Méthode permettant la récupération du top 10 des ventes
     * @return
     */
    public List<Livre> getTop()
    {
        return top = librairieEJB.getTop10();
    }

    /**
     * Méthode permettant la récupération de la totalité des livres
     * @return
     */
    public List<Livre> getLivres()
    {
        return livres = librairieEJB.getLivres();
    }

    /**
     * Méthode permettant la récupération d'un livre en fonction de son id
     * @param idLivre
     * @return
     */
    public Livre getLivre(int idLivre){
        return livre = librairieEJB.getLivre(idLivre);
    }

    /**
     * Méthode permettant la récupération de la liste des catégories
     * @return
     */
    public List<Categorie> getCategories()
    {
        return categories = librairieEJB.getCategories();
    }

    public int getPagination()
    {
        return nb = librairieEJB.getPagination();
    }

    public boolean isStock(Livre l)
    {
        String idEtat = l.getEtatLivre().getIdEtatLivre();
        return idEtat.equals("N") || idEtat.equals("S");
    }
}