/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Livre;
import java.io.IOException;
import java.io.Serializable;
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
public class gestionStockBean implements Serializable
{
    private Livre livreModifie;
    private Integer quantiteDisponible;

    @EJB
    private LibrairieEJBRemote librairieEJB;

    /** Creates a new instance of gestionStockBean */
    public gestionStockBean()
    {
    }

    public List<Livre> getAllLivres()
    {
        return librairieEJB.getLivres();
    }

    public void changeQuantite(ActionEvent actionEvent) throws IOException
    {
        livreModifie.setQuantiterDisponible(quantiteDisponible);
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
}
