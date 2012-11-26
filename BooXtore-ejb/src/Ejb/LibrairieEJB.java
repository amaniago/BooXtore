package Ejb;

import Jpa.Classes.Categorie;
import Jpa.Classes.EtatLivre;
import Jpa.Classes.Livre;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * EJB gérant les livres et permettant la récupération de ceux ci, et leurs gestions
 */
@Stateless
public class LibrairieEJB implements LibrairieEJBRemote
{
    /**
     * Contexte de persistance injecté gérant les transactions, dont le cycle de vie et géré par le container d'EJB.
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Retourne les livres présent dans la base
     * @return liste des livres du catalogue
     */
    @Override
    public List<Livre> getListe()
    {
        return em.createNamedQuery("Livre.findAll").getResultList();
    }

    /**
     * Retourne les dix livres les plus vendus
     * @return liste des dix livres les plus vendus
     */
    @Override
    public List<Livre> getTop10()
    {
        Query query = em.createNativeQuery("SELECT L.*, SUM(C.QUANTITE_COMMANDER) AS qu "
                + "FROM LIVRE L "
                + "INNER JOIN CONTENIR C ON L.ID_LIVRE = C.ID_LIVRE "
                + "GROUP BY L.ID_LIVRE "
                + "ORDER BY qu DESC");
        query.setMaxResults(10);
        return query.getResultList();
    }

    /**
     * Obtient le livre dont l'identifiant correspond à celui spécifié
     * @param idLivre Identifiant du livre recherché
     * @return Livre recherché
     */
    @Override
    public Livre getLivre(int idLivre)
    {
        Query query = em.createNamedQuery("Livre.findByIdLivre", Livre.class);
        query.setParameter("idLivre", idLivre);
        return (Livre) query.getSingleResult();
    }

    /**
     * Permet d'ajouter un livre en base de données
     * @param titre Titre du livre
     * @param date Date de parution du livre
     * @param resume Résume du livre
     * @param sommaire Sommaire du livre
     * @param quantite Quantité initiale du livre
     * @param auteur Auteur du livre
     * @param editeur Editeur du livre
     * @param prix Prix du livre
     * @param idEtat Etat actuel du livre
     * @param idCategorie Categorie du livre
     * @return Livre crée
     */
    @Override
    public Livre ajouterLivre(String titre, Date date, String resume, String sommaire, int quantite, String auteur, String editeur, BigDecimal prix, String idEtat, int idCategorie)
    {
        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setDateParution(date);
        livre.setResume(resume);
        livre.setSommaire(sommaire);
        livre.setQuantiterDisponible(quantite);
        livre.setAuteur(auteur);
        livre.setEditeur(editeur);
        livre.setPrix(prix);
        livre.setEtatLivre(em.find(EtatLivre.class, idEtat));
        livre.setCategorie(em.find(Categorie.class, idCategorie));
        em.persist(livre);
        return livre;
    }

    /**
     * Permet de modifier un livre en base de données
     * @param livre Livre à modifier
     */
    @Override
    public void modifierLivre(Livre livre)
    {
        em.merge(livre);
    }

    /**
     * Permet de supprimer un livre en base de données
     * @param livre Livre à supprimer
     */
    @Override
    public void supprimerLivre(Livre livre)
    {
        livre = em.merge(livre);
        em.remove(livre);
    }

    /**
     * Permet d'ajouter une catégorie en base de données
     * @param nom Nom de la catégorie
     * @return Catégorie crée
     */
    @Override
    public Categorie ajouterCategorie(String nom)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Permet de modifier une catégorie en base de données
     * @param categorie Catégorie à modifier
     */
    @Override
    public void modifierCategorie(Categorie categorie)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Permet de supprimer une catégorie en base de données
     * @param categorie Catégorie à supprimer
     */
    @Override
    public void supprimerCategorie(Categorie categorie)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}