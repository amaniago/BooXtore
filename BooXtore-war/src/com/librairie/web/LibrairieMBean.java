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

    //D�claration des listes
    private List<Livre> top;
    private List<Categorie> categories;
    private List<Livre> livres;

    //D�claration d'un livre
    private Livre livre;

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
     * M�thode permettant la r�cup�ration du top 10 des ventes
     * @return
     */
    public List<Livre> getTop()
    {
        return top = librairieEJB.getTop10();
    }

    /**
     * M�thode permettant la r�cup�ration de la totalit� des livres
     * @return
     */
    public List<Livre> getLivres()
    {
        return livres = librairieEJB.getLivres();
    }

    /**
     * M�thode permettant la r�cup�ration d'un livre en fonction de son id
     * @param idLivre
     * @return
     */
    public Livre getLivre()
    {
        return livre;
    }

    /**
     * M�thode permettant la r�cup�ration de la liste des cat�gories
     * @return
     */
    public List<Categorie> getCategories()
    {
        return categories = librairieEJB.getCategories();
    }

    public int getPagination()
    {
        return librairieEJB.getPagination();
    }

    /**
     * M�thode permettant de savoir si un livre est disponible
     * @param l Livre dont l'on v�rifie l'�tat
     * @return Disponibilit� du livre
     */
    public boolean isStock(Livre l)
    {
        String idEtat = l.getEtatLivre().getIdEtatLivre();
        return idEtat.equals("N") || idEtat.equals("S");
    }
}