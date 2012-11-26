package Ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CompteEJB implements CompteEJBRemote
{
    /**
     * Contexte de persistance injecté gérant les transactions, dont le cycle de vie et géré par le container d'EJB.
     */
    @PersistenceContext
    EntityManager em;

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
    public void modifierCompte(String login, String mdp, String nom, String prenom, String mail, String adr)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}