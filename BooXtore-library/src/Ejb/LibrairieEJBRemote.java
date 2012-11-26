package Ejb;

import Jpa.Classes.Categorie;
import Jpa.Classes.Livre;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LibrairieEJBRemote
{
    public List<Livre> getListe();

    public List<Livre> getTop10();

    public Livre getLivre(int idLivre);

    public Livre ajouterLivre(String titre, Date date, String resume, String sommaire, int quantite, String auteur, String editeur, BigDecimal prix, String idEtat, int idCategorie);

    public void modifierLivre(Livre livre);

    public void supprimerLivre(Livre livre);

    public Categorie ajouterCategorie(String nom);

    public void modifierCategorie(Categorie categorie);

    public void supprimerCategorie(Categorie categorie);
}