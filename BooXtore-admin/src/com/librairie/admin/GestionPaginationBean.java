package com.librairie.admin;

import Ejb.LibrairieEJBRemote;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class GestionPaginationBean
{
    @EJB
    private LibrairieEJBRemote librairieEJB;
    private Integer pagination;

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

    /** Creates a new instance of GestionPaginationBean */
    public GestionPaginationBean()
    {
    }

    public void modifierPagination(ActionEvent actionEvent) throws IOException
    {
        librairieEJB.setPagination(pagination);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modification validée"));
    }

    public Integer getPagination()
    {
        pagination = this.librairieEJB.getPagination();
        return pagination;
    }

    public void setPagination(Integer pagination)
    {
        this.pagination = pagination;
    }
}