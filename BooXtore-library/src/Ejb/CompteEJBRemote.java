package Ejb;

import javax.ejb.Remote;

@Remote
public interface CompteEJBRemote
{
    boolean authentification(String login, String mdp);

    void inscription(String login, String mdp, String nom, String prenom, String mail, String adr);

    void modifierCompte(String login, String mdp, String nom, String prenom, String mail, String adr);
}