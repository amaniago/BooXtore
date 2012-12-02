/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Kevin
 */
@ManagedBean
@ViewScoped
public class creationLivreBean implements Serializable
{
    @EJB
    private LibrairieEJBRemote librairieEJB;

    //Propriétés d'un livre
    private String titre;
    private Date dateDeParution;
    private String auteur;
    private String editeur;
    private String resume;
    private String sommaire;
    private BigDecimal prix;
    private String etatLivre;
    private Integer categorie;
    private Integer quantite;


    /** Creates a new instance of creationLivreBean */
    public creationLivreBean()
    {
    }

    public void creerLivre(ActionEvent actionEvent) throws IOException
    {
        //Création de la catégorie en base
        //librairieEJB.ajouterLivre(titre, dateDeParution, resume, sommaire, quantite, auteur, editeur, prix, etatLivre, categorie);
        //Redirection vers la page de gestion des catégories
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestionlivre.xhtml");
    }

    /**
     * Retourne une liste de catégorie
     * @return
     */
    public List<Categorie> getAllCategories()
    {
        return librairieEJB.getCategories();
    }



    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public Date getDateDeParution()
    {
        return dateDeParution;
    }

    public void setDateDeParution(Date dateDeParution)
    {
        this.dateDeParution = dateDeParution;
    }

    public String getAuteur()
    {
        return auteur;
    }

    public void setAuteur(String auteur)
    {
        this.auteur = auteur;
    }

    public String getEditeur()
    {
        return editeur;
    }

    public void setEditeur(String editeur)
    {
        this.editeur = editeur;
    }

    public String getResume()
    {
        return resume;
    }

    public void setResume(String resume)
    {
        this.resume = resume;
    }

    public String getSommaire()
    {
        return sommaire;
    }

    public void setSommaire(String sommaire)
    {
        this.sommaire = sommaire;
    }

    public BigDecimal getPrix()
    {
        return prix;
    }

    public void setPrix(BigDecimal prix)
    {
        this.prix = prix;
    }

    public String getEtatLivre()
    {
        return etatLivre;
    }

    public void setEtatLivre(String etatLivre)
    {
        this.etatLivre = etatLivre;
    }

    public Integer getCategorie()
    {
        return categorie;
    }

    public void setCategorie(Integer categorie)
    {
        this.categorie = categorie;
    }

    public Integer getQuantite()
    {
        return quantite;
    }

    public void setQuantite(Integer quantite)
    {
        this.quantite = quantite;
    }
}
