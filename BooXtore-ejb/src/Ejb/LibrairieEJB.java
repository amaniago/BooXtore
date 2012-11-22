package Ejb;

import Jpa.Classes.Livre;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class LibrairieEJB implements LibrairieEJBRemote
{
    @Override
    public List<Livre> getListe()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Livre> getTop10()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Livre getLivre(int idLivre)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setStock(Livre livre)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ajouterLivre(String titre, Date date, String resume, String sommaire, int quantite, String auteur, String editeur, double prix, String etat)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifierLivre(Livre livre, String titre, Date date, String resume, String sommaire, int quantite, String auteur, String editeur, double prix, String etat)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void supprimerLivre(Livre livre)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}