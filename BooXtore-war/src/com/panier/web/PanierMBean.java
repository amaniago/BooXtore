package com.panier.web;

import Jpa.Classes.Livre;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean managé gérant implémentant le panier
 */
@ManagedBean
@SessionScoped
public class PanierMBean implements Serializable
{
    /**
     * Panier modéliser par une HashMap
     */
    private Map<Livre, Integer> mapLivres = new HashMap<>();

    /**
     * Variable utilisée pour définir des incréments
     */
    private int plus, moins;

    /**
     * Fonction permettan d'obtenir le panier pour la datatable
     * @return Panier sous forme d'une liste d'entrées
     */
    public List<Map.Entry<Livre, Integer>> getPanier()
    {
        return new ArrayList<>(mapLivres.entrySet());
    }

    public int getPlus()
    {
        return plus;
    }

    public void setPlus(int plus)
    {
        this.plus = plus;
    }

    public int getMoins()
    {
        return moins;
    }

    public void setMoins(int moins)
    {
        this.moins = moins;
    }

    public PanierMBean()
    {
    }

    /**
     * Méthode permettant d'incrémenter la quantité d'un livre dans le panier
     * @param livre Livre dont la quantité est à modifier
     * @param quantite Nouvelle quantité
     */
    public void ajouterLivre(Livre livre, int quantite)
    {
        if (mapLivres.containsKey(livre))
        {
            if (livre.getQuantiterDisponible() >= mapLivres.get(livre) + quantite)
                mapLivres.put(livre, mapLivres.get(livre) + quantite);
            else
                mapLivres.put(livre, livre.getQuantiterDisponible());
        }
        else
        {
            if (livre.getQuantiterDisponible() >= quantite)
                mapLivres.put(livre, quantite);
            else
                mapLivres.put(livre, livre.getQuantiterDisponible());
        }
    }

    /**
     * Surcharge de la méthode précédente dont la quantité est renseigner par la variable plus
     * @param livre Livre dont la quantité est à modifier
     */
    public void ajouterLivre(Livre livre)
    {
        ajouterLivre(livre, plus);
    }

    /**
     * Méthode permettant de décrémenter la quantité d'un livre dans le panier
     * @param livre Livre dont la quantité est à modifier
     * @param quantite quantite Nouvelle quantité
     */
    public void enleverLivre(Livre livre, int quantite)
    {
        if (mapLivres.containsKey(livre))
        {
            if (mapLivres.get(livre) > quantite)
                mapLivres.put(livre, mapLivres.get(livre) - quantite);
            else
                mapLivres.remove(livre);
        }
    }

    /**
     * Surcharge de la méthode précédente dont la quantité est renseigner par la variable moins
     * @param livre Livre dont la quantité est à modifier
     */
    public void enleverLivre(Livre livre)
    {
        enleverLivre(livre, moins);
    }

    /**
     * Méthode permettant de supprimer un livre du panier
     * @param livre Livre à supprimer du panier
     */
    public void supprimerLivre(Livre livre)
    {
        mapLivres.remove(livre);
    }

    /**
     * Méthode permettant de récupérer le panier sous forme de map, utilisé par l'EJB commande
     * @return Panier sous forme de liste
     */
    public Map<Livre, Integer> getPanierCommande()
    {
        return mapLivres;
    }
}