package Ejb;

import Jpa.Classes.Livre;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LibrairieEJB implements LibrairieEJBRemote
{
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Livre> getListe()
    {
        return em.createNamedQuery("Livre.findAll").getResultList();
    }

    @Override
    public List<Livre> getTop10()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Livre getLivre(int idLivre)
    {
        return (Livre) em.createNativeQuery("Livre.findByIdLivre", Integer.toString(idLivre)).getSingleResult();
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