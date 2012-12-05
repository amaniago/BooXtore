package com.panier.web;

import Ejb.CommandeEJBRemote;
import Jpa.Classes.Client;
import Jpa.Classes.Commande;
import com.compte.web.AuthentificationMBean;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.ws.WebServiceRef;
import WebServ.BanqueService_Service;
import javax.annotation.PostConstruct;

@ManagedBean
@ViewScoped
public class commandeMBean
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/BanqueService/BanqueService.wsdl")
    private BanqueService_Service service;
    @EJB
    private CommandeEJBRemote CommandeEJB;
    private String carte;
    private List<Commande> histo;

    public String getCarte()
    {
        return carte;
    }

    public void setCarte(String carte)
    {
        this.carte = carte;
    }

    /** Creates a new instance of commandeMBean */
    public commandeMBean()
    {
    }

    @PostConstruct
    public void verificationAuthentification() throws IOException
    {
        //Verification si la session a été démarrée
        AuthentificationMBean authentificationMBean = (AuthentificationMBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authentificationMBean");
        if (authentificationMBean.getClient() == null)
        {
            //Redirection vers l'authentification si l'utilisateur n'est pas authentifié
            FacesContext.getCurrentInstance().getExternalContext().redirect("top10.xhtml");
        }
    }

    /**
     * Mï¿½thode qui permet d'effectuer la commande
     * @param client
     * @param panier
     */
    public void commande() throws IOException
    {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        AuthentificationMBean a = (AuthentificationMBean) context.getSessionMap().get("authentificationMBean");
        PanierMBean p = (PanierMBean) context.getSessionMap().get("panierMBean");
        if (transaction(carte))
        {
            CommandeEJB.creationCommande(a.getClient(), p.getPanierCommande());
            context.getSessionMap().remove("panierMBean");
            context.redirect("top10.xhtml");
        }
    }

    private Boolean transaction(java.lang.String numCb)
    {
        WebServ.BanqueService port = service.getBanqueServicePort();
        return port.transaction(numCb);
    }

    public List<Commande> histo() throws IOException
    {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        AuthentificationMBean authentificationMBean = (AuthentificationMBean) context.getSessionMap().get("authentificationMBean");
        return CommandeEJB.getHisto(authentificationMBean.getClient());
    }
}