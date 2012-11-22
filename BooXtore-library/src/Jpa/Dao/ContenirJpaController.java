/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa.Dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Jpa.Classes.Livre;
import Jpa.Classes.Commande;
import Jpa.Classes.Contenir;
import Jpa.Classes.ContenirPK;
import Jpa.Dao.exceptions.NonexistentEntityException;
import Jpa.Dao.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author MANIAGO
 */
public class ContenirJpaController implements Serializable {

    public ContenirJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contenir contenir) throws PreexistingEntityException, Exception {
        if (contenir.getContenirPK() == null) {
            contenir.setContenirPK(new ContenirPK());
        }
        contenir.getContenirPK().setIdLivre(contenir.getLivre().getIdLivre());
        contenir.getContenirPK().setIdCommande(contenir.getCommande().getIdCommande());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Livre livre = contenir.getLivre();
            if (livre != null) {
                livre = em.getReference(livre.getClass(), livre.getIdLivre());
                contenir.setLivre(livre);
            }
            Commande commande = contenir.getCommande();
            if (commande != null) {
                commande = em.getReference(commande.getClass(), commande.getIdCommande());
                contenir.setCommande(commande);
            }
            em.persist(contenir);
            if (livre != null) {
                livre.getContenirList().add(contenir);
                livre = em.merge(livre);
            }
            if (commande != null) {
                commande.getContenirList().add(contenir);
                commande = em.merge(commande);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContenir(contenir.getContenirPK()) != null) {
                throw new PreexistingEntityException("Contenir " + contenir + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contenir contenir) throws NonexistentEntityException, Exception {
        contenir.getContenirPK().setIdLivre(contenir.getLivre().getIdLivre());
        contenir.getContenirPK().setIdCommande(contenir.getCommande().getIdCommande());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenir persistentContenir = em.find(Contenir.class, contenir.getContenirPK());
            Livre livreOld = persistentContenir.getLivre();
            Livre livreNew = contenir.getLivre();
            Commande commandeOld = persistentContenir.getCommande();
            Commande commandeNew = contenir.getCommande();
            if (livreNew != null) {
                livreNew = em.getReference(livreNew.getClass(), livreNew.getIdLivre());
                contenir.setLivre(livreNew);
            }
            if (commandeNew != null) {
                commandeNew = em.getReference(commandeNew.getClass(), commandeNew.getIdCommande());
                contenir.setCommande(commandeNew);
            }
            contenir = em.merge(contenir);
            if (livreOld != null && !livreOld.equals(livreNew)) {
                livreOld.getContenirList().remove(contenir);
                livreOld = em.merge(livreOld);
            }
            if (livreNew != null && !livreNew.equals(livreOld)) {
                livreNew.getContenirList().add(contenir);
                livreNew = em.merge(livreNew);
            }
            if (commandeOld != null && !commandeOld.equals(commandeNew)) {
                commandeOld.getContenirList().remove(contenir);
                commandeOld = em.merge(commandeOld);
            }
            if (commandeNew != null && !commandeNew.equals(commandeOld)) {
                commandeNew.getContenirList().add(contenir);
                commandeNew = em.merge(commandeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ContenirPK id = contenir.getContenirPK();
                if (findContenir(id) == null) {
                    throw new NonexistentEntityException("The contenir with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ContenirPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenir contenir;
            try {
                contenir = em.getReference(Contenir.class, id);
                contenir.getContenirPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenir with id " + id + " no longer exists.", enfe);
            }
            Livre livre = contenir.getLivre();
            if (livre != null) {
                livre.getContenirList().remove(contenir);
                livre = em.merge(livre);
            }
            Commande commande = contenir.getCommande();
            if (commande != null) {
                commande.getContenirList().remove(contenir);
                commande = em.merge(commande);
            }
            em.remove(contenir);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contenir> findContenirEntities() {
        return findContenirEntities(true, -1, -1);
    }

    public List<Contenir> findContenirEntities(int maxResults, int firstResult) {
        return findContenirEntities(false, maxResults, firstResult);
    }

    private List<Contenir> findContenirEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contenir.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Contenir findContenir(ContenirPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contenir.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenirCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contenir> rt = cq.from(Contenir.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
