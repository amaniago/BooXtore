package Ejb;

import Jpa.Classes.Commande;
import javax.ejb.Stateless;

@Stateless
public class CommandeEJB implements CommandeEJBRemote
{
    @Override
    public void creationCommande(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Commande getCommande(int IdCommande)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEtatCommande(int IdCommande, String idEtatCommande)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}