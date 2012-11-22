package Ejb;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LibrairieEJBRemote
{
    //TODO : Remplacer Object par Livre
    List<Object> getListe();

    List<Object> getTop10();

    Object getLivre(int id);

    void setStock(Object o);

    void ajouterLivre();    //TODO : Ajouter propriété du livre

    void modifierLivre(Object o);   //TODO : Ajouter propriété du livre

    void supprimerLivre(Object o);
}