package Ejb;

import Jpa.Classes.Livre;
import java.sql.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LibrairieEJBRemote
{
    List<Livre> getListe();

    List<Livre> getTop10();

    Livre getLivre(int idLivre);

    void setStock(Livre livre);

    void ajouterLivre(String titre, Date date, String resume, String sommaire, int quantite, String auteur, String editeur, double prix, String etat);

    void modifierLivre(Livre livre, String titre, Date date, String resume, String sommaire, int quantite, String auteur, String editeur, double prix, String etat);

    void supprimerLivre(Livre livre);
}