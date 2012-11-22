package Ejb;

import Jpa.Classes.Commande;
import javax.ejb.Remote;

@Remote
public interface CommandeEJBRemote
{
    //TODO : Remplacer object par un panier ou une liste de livre, retourner peut être une commande
    void creationCommande(Object o);

    Commande getCommande(int IdCommande);

    void setEtatCommande(int IdCommande, String idEtatCommande);
}