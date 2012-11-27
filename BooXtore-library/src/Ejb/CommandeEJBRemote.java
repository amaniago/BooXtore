package Ejb;

import Jpa.Classes.Client;
import Jpa.Classes.Commande;
import Jpa.Classes.Livre;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface CommandeEJBRemote
{
    public Commande creationCommande(Client client, Map<Livre, Integer> panier);

    public Commande getCommande(int idCommande);

    public void setEtatCommande(Commande commande, String idEtatCommande);

    public List<Commande> getCommandes();
}