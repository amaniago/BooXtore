/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compte.web;

import Ejb.CompteEJBRemote;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kevin
 */
@ManagedBean
@ViewScoped
public class InscriptionMBean
{
    /** Creates a new instance of InscriptionMBean */
    public InscriptionMBean()
    {
    }
    @EJB
    private CompteEJBRemote CompteEJB;
    private String login;
    private String pwd;
    private String nom;
    private String prenom;
    private String mail;
    private String adr;
    private String codePostal;
    private String ville;

    /**
     * Méthode d'inscription d'un compte utilisateur
     * @throws IOException
     */
    public void inscription() throws IOException
    {
        //Ajout d'un compte utilisateur
        CompteEJB.inscription(login, adr, nom, prenom, mail, adr, codePostal, ville);
        FacesContext.getCurrentInstance().getExternalContext().redirect("top10.xhtml");
    }


    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getAdr()
    {
        return adr;
    }

    public void setAdr(String adr)
    {
        this.adr = adr;
    }

    public String getCodePostal()
    {
        return codePostal;
    }

    public void setCodePostal(String codePostal)
    {
        this.codePostal = codePostal;
    }

    public String getVille()
    {
        return ville;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }


}
