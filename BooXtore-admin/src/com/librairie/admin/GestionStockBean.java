package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Livre;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "GestionStockBean")
@ViewScoped
public class GestionStockBean implements Serializable
{
    private Livre livreModifie;
    private Integer quantiteDisponible;
    private Integer seuil;

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
     * Constructeur GestionStockBean
     * @throws IOException
     */
    public GestionStockBean() throws IOException
    {
    }

    /**
     * Méthode qui retourne la liste des livres
     * @return
     */
    public List<Livre> getAllLivres()
    {
        return librairieEJB.getLivres();
    }

    /**
     * Méthode de modification de la quantité disponible
     * @param actionEvent
     * @throws IOException
     */
    public void modifierQuantite(ActionEvent actionEvent) throws IOException
    {
        if(quantiteDisponible != null)
        {
            livreModifie.setQuantiterDisponible(quantiteDisponible);
        }
        if(seuil != null)
        {
            livreModifie.setSeuil(seuil);
        }
        librairieEJB.modifierLivre(livreModifie);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gestionstock.xhtml");
    }

    public Livre getLivreModifie()
    {
        return livreModifie;
    }

    public void setLivreModifie(Livre livreModifie)
    {
        this.livreModifie = livreModifie;
    }

    public Integer getQuantiteDisponible()
    {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(Integer quantiteDisponible)
    {
        this.quantiteDisponible = quantiteDisponible;
    }

    public Integer getSeuil()
    {
        return seuil;
    }

    public void setSeuil(Integer seuil)
    {
        this.seuil = seuil;
    }
}