package Ejb;

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
    public Object getCommande(int IdCommande)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEtatCommande(int IdCommande, String Etat)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}