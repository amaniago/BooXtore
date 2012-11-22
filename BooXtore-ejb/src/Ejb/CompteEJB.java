package Ejb;

import javax.ejb.Stateless;

@Stateless
public class CompteEJB implements CompteEJBRemote
{
    @Override
    public boolean authentification(String login, String mdp)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void inscription(String login, String mdp, String nom, String prenom, String mail, String adr)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifierCompte(String login)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}