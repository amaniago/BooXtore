package Ejb;

import javax.ejb.Remote;

@Remote
public interface CommandeEJBRemote
{
    //TODO : Remplacer object par un panier ou une liste de livre, retourner peut être une commande
    void creationCommande(Object o);

    //TODO : Remplacer object par une commande
    Object getCommande(int IdCommande);

    //TODO : Définir l'état comme une string ou un id, passer l'id ou la commande
    void setEtatCommande(int IdCommande, String Etat);
}