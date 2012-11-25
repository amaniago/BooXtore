package Ejb;

import Jpa.Classes.Commande;
import javax.ejb.Remote;

@Remote
public interface CommandeEJBRemote
{
    //TODO : Remplacer object par un panier ou une liste de livre, retourner peut être une commande
    public void creationCommande(Object o);

    public Commande getCommande(int IdCommande);

    public void setEtatCommande(int IdCommande, String idEtatCommande);
}