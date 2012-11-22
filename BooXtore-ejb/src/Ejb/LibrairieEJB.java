package Ejb;

import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class LibrairieEJB implements LibrairieEJBRemote
{
    @Override
    public List<Object> getListe()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object> getTop10()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getLivre(int id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setStock(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ajouterLivre()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifierLivre(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void supprimerLivre(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}