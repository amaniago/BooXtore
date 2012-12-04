package com.librairie.web;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import Jpa.Classes.Livre;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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

    public LibrairieMBean()
    {
    }

    /**
     * Initialisateur du bean manager
     */
    @PostConstruct
    public void init()
    {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params != null && params.containsKey("idLivre"))
            livre = librairieEJB.getLivre(Integer.parseInt(params.get("idLivre")));
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
    public Livre getLivre()
    {
        return livre;
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

    /**
     * Méthode permettant de savoir si un livre est disponible
     * @param l Livre dont l'on vérifie l'état
     * @return Disponibilité du livre
     */
    public boolean isStock(Livre l)
    {
        String idEtat = l.getEtatLivre().getIdEtatLivre();
        return idEtat.equals("N") || idEtat.equals("S");
    }
}
