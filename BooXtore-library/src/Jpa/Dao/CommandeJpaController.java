package Jpa.Dao;

import Jpa.Classes.Client;
import Jpa.Classes.Commande;
import Jpa.Classes.Contenir;
import Jpa.Classes.EtatCommande;
import Jpa.Dao.exceptions.IllegalOrphanException;
import Jpa.Dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

public class CommandeJpaController implements Serializable
{
    public CommandeJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Commande commande)
    {
        if (commande.getContenirList() == null)
        {
            commande.setContenirList(new ArrayList<Contenir>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            EtatCommande idEtatCommande = commande.getEtatCommande();
            if (idEtatCommande != null)
            {
                idEtatCommande = em.getReference(idEtatCommande.getClass(), idEtatCommande.getIdEtatCommande());
                commande.setEtatCommande(idEtatCommande);
            }
            Client login = commande.getLogin();
            if (login != null)
            {
                login = em.getReference(login.getClass(), login.getLogin());
                commande.setLogin(login);
            }
            List<Contenir> attachedContenirList = new ArrayList<>();
            for (Contenir contenirListContenirToAttach : commande.getContenirList())
            {
                contenirListContenirToAttach = em.getReference(contenirListContenirToAttach.getClass(), contenirListContenirToAttach.getContenirPK());
                attachedContenirList.add(contenirListContenirToAttach);
            }
            commande.setContenirList(attachedContenirList);
            em.persist(commande);
            if (idEtatCommande != null)
            {
                idEtatCommande.getCommandeList().add(commande);
                idEtatCommande = em.merge(idEtatCommande);
            }
            if (login != null)
            {
                login.getCommandeList().add(commande);
                login = em.merge(login);
            }
            for (Contenir contenirListContenir : commande.getContenirList())
            {
                Commande oldCommandeOfContenirListContenir = contenirListContenir.getCommande();
                contenirListContenir.setCommande(commande);
                contenirListContenir = em.merge(contenirListContenir);
                if (oldCommandeOfContenirListContenir != null)
                {
                    oldCommandeOfContenirListContenir.getContenirList().remove(contenirListContenir);
                    oldCommandeOfContenirListContenir = em.merge(oldCommandeOfContenirListContenir);
                }
            }
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

    public void edit(Commande commande) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Commande persistentCommande = em.find(Commande.class, commande.getIdCommande());
            EtatCommande idEtatCommandeOld = persistentCommande.getEtatCommande();
            EtatCommande idEtatCommandeNew = commande.getEtatCommande();
            Client loginOld = persistentCommande.getLogin();
            Client loginNew = commande.getLogin();
            List<Contenir> contenirListOld = persistentCommande.getContenirList();
            List<Contenir> contenirListNew = commande.getContenirList();
            List<String> illegalOrphanMessages = null;
            for (Contenir contenirListOldContenir : contenirListOld)
            {
                if (!contenirListNew.contains(contenirListOldContenir))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Contenir " + contenirListOldContenir + " since its commande field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEtatCommandeNew != null)
            {
                idEtatCommandeNew = em.getReference(idEtatCommandeNew.getClass(), idEtatCommandeNew.getIdEtatCommande());
                commande.setEtatCommande(idEtatCommandeNew);
            }
            if (loginNew != null)
            {
                loginNew = em.getReference(loginNew.getClass(), loginNew.getLogin());
                commande.setLogin(loginNew);
            }
            List<Contenir> attachedContenirListNew = new ArrayList<>();
            for (Contenir contenirListNewContenirToAttach : contenirListNew)
            {
                contenirListNewContenirToAttach = em.getReference(contenirListNewContenirToAttach.getClass(), contenirListNewContenirToAttach.getContenirPK());
                attachedContenirListNew.add(contenirListNewContenirToAttach);
            }
            contenirListNew = attachedContenirListNew;
            commande.setContenirList(contenirListNew);
            commande = em.merge(commande);
            if (idEtatCommandeOld != null && !idEtatCommandeOld.equals(idEtatCommandeNew))
            {
                idEtatCommandeOld.getCommandeList().remove(commande);
                idEtatCommandeOld = em.merge(idEtatCommandeOld);
            }
            if (idEtatCommandeNew != null && !idEtatCommandeNew.equals(idEtatCommandeOld))
            {
                idEtatCommandeNew.getCommandeList().add(commande);
                idEtatCommandeNew = em.merge(idEtatCommandeNew);
            }
            if (loginOld != null && !loginOld.equals(loginNew))
            {
                loginOld.getCommandeList().remove(commande);
                loginOld = em.merge(loginOld);
            }
            if (loginNew != null && !loginNew.equals(loginOld))
            {
                loginNew.getCommandeList().add(commande);
                loginNew = em.merge(loginNew);
            }
            for (Contenir contenirListNewContenir : contenirListNew)
            {
                if (!contenirListOld.contains(contenirListNewContenir))
                {
                    Commande oldCommandeOfContenirListNewContenir = contenirListNewContenir.getCommande();
                    contenirListNewContenir.setCommande(commande);
                    contenirListNewContenir = em.merge(contenirListNewContenir);
                    if (oldCommandeOfContenirListNewContenir != null && !oldCommandeOfContenirListNewContenir.equals(commande))
                    {
                        oldCommandeOfContenirListNewContenir.getContenirList().remove(contenirListNewContenir);
                        oldCommandeOfContenirListNewContenir = em.merge(oldCommandeOfContenirListNewContenir);
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
                Integer id = commande.getIdCommande();
                if (findCommande(id) == null)
                {
                    throw new NonexistentEntityException("The commande with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Commande commande;
            try
            {
                commande = em.getReference(Commande.class, id);
                commande.getIdCommande();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The commande with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contenir> contenirListOrphanCheck = commande.getContenirList();
            for (Contenir contenirListOrphanCheckContenir : contenirListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Commande (" + commande + ") cannot be destroyed since the Contenir " + contenirListOrphanCheckContenir + " in its contenirList field has a non-nullable commande field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EtatCommande idEtatCommande = commande.getEtatCommande();
            if (idEtatCommande != null)
            {
                idEtatCommande.getCommandeList().remove(commande);
                idEtatCommande = em.merge(idEtatCommande);
            }
            Client login = commande.getLogin();
            if (login != null)
            {
                login.getCommandeList().remove(commande);
                login = em.merge(login);
            }
            em.remove(commande);
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

    public List<Commande> findCommandeEntities()
    {
        return findCommandeEntities(true, -1, -1);
    }

    public List<Commande> findCommandeEntities(int maxResults, int firstResult)
    {
        return findCommandeEntities(false, maxResults, firstResult);
    }

    private List<Commande> findCommandeEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Commande.class));
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

    public Commande findCommande(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Commande.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCommandeCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Commande> rt = cq.from(Commande.class);
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