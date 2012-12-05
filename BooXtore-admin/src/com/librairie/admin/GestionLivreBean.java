package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import Jpa.Classes.Livre;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "GestionLivreBean")
@ViewScoped
public class GestionLivreBean implements Serializable
{
    private Livre livreModifie;
    private String titre;
    private Date dateDeParution;
    private String auteur;
    private String editeur;
    private String resume;
    private String sommaire;
    private BigDecimal prix;
//    private String etatLivre;
//    private Integer categorie;

    @EJB
    private LibrairieEJBRemote librairieEJB;

    @PostConstruct
    public void verificationAuthentification() throws IOException
    {
        //Verification si la session a été démarrée
        LoginBean login = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
        if (login.getAdmin() == null)
        {
            //Redirection vers l'authentification si l'utilisateur n'est pas authentifié
            FacesContext.getCurrentInstance().getExternalContext().redirect("authentification.xhtml");
        }
    }

    /**
     * Constructeur GestionLivreBean
     * @throws IOException
     */
    public GestionLivreBean()
    {
    }

    /**
     * Méthode de modification d'un livre
     * @param actionEvent
     * @throws IOException
     */
    public void modifierLivre(ActionEvent actionEvent) throws IOException
    {
        if (!titre.isEmpty())
        {
            livreModifie.setTitre(titre);
        }
        if (!auteur.isEmpty())
        {
            livreModifie.setAuteur(auteur);
        }
        if (!editeur.isEmpty())
        {
            livreModifie.setEditeur(editeur);
        }
        if (prix != null)
        {
            livreModifie.setPrix(prix);
        }

        livreModifie.setDateParution(dateDeParution);
        livreModifie.setResume(resume);
        livreModifie.setSommaire(sommaire);

        librairieEJB.modifierLivre(livreModifie);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestionlivre.xhtml");
    }

    /**
     * Méthode de suppression d'un livre
     * @throws IOException
     */
    public void supprimerLivre() throws IOException
    {
        librairieEJB.supprimerLivre(livreModifie);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestionlivre.xhtml");
    }

    /**
     * Liste des livres
     * @return
     */
    public List<Livre> getAllLivres()
    {
        return librairieEJB.getLivres();
    }

    /**
     * Liste des catégories
     * @return
     */
    public List<Categorie> getAllCategories()
    {
        return librairieEJB.getCategories();
    }

    public Livre getLivreModifie()
    {
        return livreModifie;
    }

    public void setLivreModifie(Livre livreModifie)
    {
        this.livreModifie = livreModifie;
    }

    public String getTitre()
    {
        if (livreModifie != null)
        {
            return titre = livreModifie.getTitre();
        }
        else
        {
            return titre;
        }
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public Date getDateDeParution()
    {
        if (livreModifie != null)
        {
            return dateDeParution = livreModifie.getDateParution();
        }
        else
        {
            return dateDeParution;
        }
    }

    public void setDateDeParution(Date dateDeParution)
    {
        this.dateDeParution = dateDeParution;
    }

    public String getAuteur()
    {
        if (livreModifie != null)
        {
            return auteur = livreModifie.getAuteur();
        }
        else
        {
            return auteur;
        }
    }

    public void setAuteur(String auteur)
    {
        this.auteur = auteur;
    }

    public String getEditeur()
    {
        if (livreModifie != null)
        {
            return editeur = livreModifie.getEditeur();
        }
        else
        {
            return editeur;
        }
    }

    public void setEditeur(String editeur)
    {
        this.editeur = editeur;
    }

    public String getResume()
    {
        if (livreModifie != null)
        {
            return resume = livreModifie.getResume();
        }
        else
        {
            return resume;
        }
    }

    public void setResume(String resume)
    {
        this.resume = resume;
    }

    public String getSommaire()
    {
        if (livreModifie != null)
        {
            return sommaire = livreModifie.getSommaire();
        }
        else
        {
            return sommaire;
        }
    }

    public void setSommaire(String sommaire)
    {
        this.sommaire = sommaire;
    }

    public BigDecimal getPrix()
    {
        if (livreModifie != null)
        {
            return prix = livreModifie.getPrix();
        }
        else
        {
            return prix;
        }
    }

    public void setPrix(BigDecimal prix)
    {
        this.prix = prix;
    }
}