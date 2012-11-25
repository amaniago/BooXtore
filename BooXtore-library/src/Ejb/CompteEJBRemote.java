package Ejb;

import javax.ejb.Remote;

@Remote
public interface CompteEJBRemote
{
    public boolean authentification(String login, String mdp);

    public void inscription(String login, String mdp, String nom, String prenom, String mail, String adr);

    public void modifierCompte(String login, String mdp, String nom, String prenom, String mail, String adr);
}