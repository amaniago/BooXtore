package com.panier.web;

import Ejb.CommandeEJBRemote;
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
import webserv.BanqueService_Service;

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

    /**
     * M�thode qui permet d'effectuer la commande
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
        webserv.BanqueService port = service.getBanqueServicePort();
        return port.transaction(numCb);
    }

    public List<Commande> getHisto(String client) throws IOException
    {
        return CommandeEJB.getHisto(client);
    }
}