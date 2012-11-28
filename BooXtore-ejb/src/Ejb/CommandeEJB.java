package Ejb;

import Jpa.Classes.Client;
import Jpa.Classes.Commande;
import Jpa.Classes.Contenir;
import Jpa.Classes.EtatCommande;
import Jpa.Classes.Livre;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB gérant les commandes et permettant la récupération de celles-ci, et leurs gestions
 */
@Stateless
public class CommandeEJB implements CommandeEJBRemote
{
    /**
     * Contexte de persistance injecté gérant les transactions, dont le cycle de vie et géré par le container d'EJB.
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Permet la création d'une nouvelle commande
     * @param client Client rattaché à la commande
     * @param panier Liste des livres et quantités correspondantes de ceux ci
     * @return Commande crée
     */
    @Override
    public Commande creationCommande(Client client, Map<Livre, Integer> panier)
    {
        Commande commande = new Commande();
        commande.setLogin(client);
        commande.setEtatCommande(em.find(EtatCommande.class, "P"));
        List<Contenir> lstContenir = new ArrayList<>();
        em.persist(commande);
        em.flush();

        for (Map.Entry<Livre, Integer> entry : panier.entrySet())
        {
            Livre livre = entry.getKey();
            Integer quantite = entry.getValue();
            Contenir contenir = new Contenir(commande.getIdCommande(), livre.getIdLivre());
            contenir.setCommande(commande);
            contenir.setLivre(livre);
            contenir.setQuantiteCommander(quantite);
            lstContenir.add(contenir);
        }
        commande.setContenirList(lstContenir);
        em.merge(commande);

        return commande;
    }

    /**
     * Obtient la commande dont l'identifiant correspond à celui spécifié
     * @param idCommande Identifiant de la commande recherché
     * @return Commande recherchée
     */
    @Override
    public Commande getCommande(int idCommande)
    {
        Query query = em.createNamedQuery("Commande.findByIdCommande", Commande.class);
        query.setParameter("idCommande", idCommande);
        return (Commande) query.getSingleResult();
    }

    /**
     * Permet de modifier l'état de la commande
     * @param commande Commande à modifier
     * @param idEtatCommande Identifiant du nouvel état de la commande
     */
    @Override
    public void setEtatCommande(Commande commande, String idEtatCommande)
    {
        commande.setEtatCommande(em.find(EtatCommande.class, idEtatCommande));
        em.merge(commande);
    }

    /**
     * Permet d'obtenir la liste de toutes les commandes
     * @return Liste des commandes
     */
    @Override
    public List<Commande> getCommandes()
    {
        List<Commande> lst = new ArrayList<>();
        Query query = em.createNamedQuery("Commande.findAll");
        query.setHint("eclipselink.result-collection-type", java.util.ArrayList.class);
        return query.getResultList();
    }

    /**
     * Permet d'obtenir la liste de tout les états possible
     * @return Liste des états
     */
    @Override
    public List<EtatCommande> getEtats()
    {
        List<EtatCommande> lst = new ArrayList<>();
        Query query = em.createNamedQuery("EtatCommande.findAll");
        query.setHint("eclipselink.result-collection-type", java.util.ArrayList.class);
        return query.getResultList();
    }
}