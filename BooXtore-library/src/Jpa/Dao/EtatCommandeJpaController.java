package Jpa.Dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Jpa.Classes.Commande;
import Jpa.Classes.EtatCommande;
import Jpa.Dao.exceptions.NonexistentEntityException;
import Jpa.Dao.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

public class EtatCommandeJpaController implements Serializable
{
    public EtatCommandeJpaController(UserTransaction utx, EntityManagerFactory emf)
    {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(EtatCommande etatCommande) throws PreexistingEntityException, Exception
    {
        if (etatCommande.getCommandeList() == null)
        {
            etatCommande.setCommandeList(new ArrayList<Commande>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Commande> attachedCommandeList = new ArrayList<>();
            for (Commande commandeListCommandeToAttach : etatCommande.getCommandeList())
            {
                commandeListCommandeToAttach = em.getReference(commandeListCommandeToAttach.getClass(), commandeListCommandeToAttach.getIdCommande());
                attachedCommandeList.add(commandeListCommandeToAttach);
            }
            etatCommande.setCommandeList(attachedCommandeList);
            em.persist(etatCommande);
            for (Commande commandeListCommande : etatCommande.getCommandeList())
            {
                EtatCommande oldIdEtatCommandeOfCommandeListCommande = commandeListCommande.getEtatCommande();
                commandeListCommande.setEtatCommande(etatCommande);
                commandeListCommande = em.merge(commandeListCommande);
                if (oldIdEtatCommandeOfCommandeListCommande != null)
                {
                    oldIdEtatCommandeOfCommandeListCommande.getCommandeList().remove(commandeListCommande);
                    oldIdEtatCommandeOfCommandeListCommande = em.merge(oldIdEtatCommandeOfCommandeListCommande);
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            if (findEtatCommande(etatCommande.getIdEtatCommande()) != null)
            {
                throw new PreexistingEntityException("EtatCommande " + etatCommande + " already exists.", ex);
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(EtatCommande etatCommande) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            EtatCommande persistentEtatCommande = em.find(EtatCommande.class, etatCommande.getIdEtatCommande());
            List<Commande> commandeListOld = persistentEtatCommande.getCommandeList();
            List<Commande> commandeListNew = etatCommande.getCommandeList();
            List<Commande> attachedCommandeListNew = new ArrayList<>();
            for (Commande commandeListNewCommandeToAttach : commandeListNew)
            {
                commandeListNewCommandeToAttach = em.getReference(commandeListNewCommandeToAttach.getClass(), commandeListNewCommandeToAttach.getIdCommande());
                attachedCommandeListNew.add(commandeListNewCommandeToAttach);
            }
            commandeListNew = attachedCommandeListNew;
            etatCommande.setCommandeList(commandeListNew);
            etatCommande = em.merge(etatCommande);
            for (Commande commandeListOldCommande : commandeListOld)
            {
                if (!commandeListNew.contains(commandeListOldCommande))
                {
                    commandeListOldCommande.setEtatCommande(null);
                    commandeListOldCommande = em.merge(commandeListOldCommande);
                }
            }
            for (Commande commandeListNewCommande : commandeListNew)
            {
                if (!commandeListOld.contains(commandeListNewCommande))
                {
                    EtatCommande oldIdEtatCommandeOfCommandeListNewCommande = commandeListNewCommande.getEtatCommande();
                    commandeListNewCommande.setEtatCommande(etatCommande);
                    commandeListNewCommande = em.merge(commandeListNewCommande);
                    if (oldIdEtatCommandeOfCommandeListNewCommande != null && !oldIdEtatCommandeOfCommandeListNewCommande.equals(etatCommande))
                    {
                        oldIdEtatCommandeOfCommandeListNewCommande.getCommandeList().remove(commandeListNewCommande);
                        oldIdEtatCommandeOfCommandeListNewCommande = em.merge(oldIdEtatCommandeOfCommandeListNewCommande);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                String id = etatCommande.getIdEtatCommande();
                if (findEtatCommande(id) == null)
                {
                    throw new NonexistentEntityException("The etatCommande with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            EtatCommande etatCommande;
            try
            {
                etatCommande = em.getReference(EtatCommande.class, id);
                etatCommande.getIdEtatCommande();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The etatCommande with id " + id + " no longer exists.", enfe);
            }
            List<Commande> commandeList = etatCommande.getCommandeList();
            for (Commande commandeListCommande : commandeList)
            {
                commandeListCommande.setEtatCommande(null);
                commandeListCommande = em.merge(commandeListCommande);
            }
            em.remove(etatCommande);
            em.getTransaction().commit();
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<EtatCommande> findEtatCommandeEntities()
    {
        return findEtatCommandeEntities(true, -1, -1);
    }

    public List<EtatCommande> findEtatCommandeEntities(int maxResults, int firstResult)
    {
        return findEtatCommandeEntities(false, maxResults, firstResult);
    }

    private List<EtatCommande> findEtatCommandeEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EtatCommande.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public EtatCommande findEtatCommande(String id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(EtatCommande.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getEtatCommandeCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EtatCommande> rt = cq.from(EtatCommande.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally
        {
            em.close();
        }
    }
}