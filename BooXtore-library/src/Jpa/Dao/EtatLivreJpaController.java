package Jpa.Dao;

import Jpa.Classes.EtatLivre;
import Jpa.Classes.Livre;
import Jpa.Dao.exceptions.NonexistentEntityException;
import Jpa.Dao.exceptions.PreexistingEntityException;
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

public class EtatLivreJpaController implements Serializable
{
    public EtatLivreJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(EtatLivre etatLivre) throws PreexistingEntityException, Exception
    {
        if (etatLivre.getLivreList() == null)
        {
            etatLivre.setLivreList(new ArrayList<Livre>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Livre> attachedLivreList = new ArrayList<>();
            for (Livre livreListLivreToAttach : etatLivre.getLivreList())
            {
                livreListLivreToAttach = em.getReference(livreListLivreToAttach.getClass(), livreListLivreToAttach.getIdLivre());
                attachedLivreList.add(livreListLivreToAttach);
            }
            etatLivre.setLivreList(attachedLivreList);
            em.persist(etatLivre);
            for (Livre livreListLivre : etatLivre.getLivreList())
            {
                EtatLivre oldIdEtatLivreOfLivreListLivre = livreListLivre.getEtatLivre();
                livreListLivre.setEtatLivre(etatLivre);
                livreListLivre = em.merge(livreListLivre);
                if (oldIdEtatLivreOfLivreListLivre != null)
                {
                    oldIdEtatLivreOfLivreListLivre.getLivreList().remove(livreListLivre);
                    oldIdEtatLivreOfLivreListLivre = em.merge(oldIdEtatLivreOfLivreListLivre);
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            if (findEtatLivre(etatLivre.getIdEtatLivre()) != null)
            {
                throw new PreexistingEntityException("EtatLivre " + etatLivre + " already exists.", ex);
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

    public void edit(EtatLivre etatLivre) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            EtatLivre persistentEtatLivre = em.find(EtatLivre.class, etatLivre.getIdEtatLivre());
            List<Livre> livreListOld = persistentEtatLivre.getLivreList();
            List<Livre> livreListNew = etatLivre.getLivreList();
            List<Livre> attachedLivreListNew = new ArrayList<>();
            for (Livre livreListNewLivreToAttach : livreListNew)
            {
                livreListNewLivreToAttach = em.getReference(livreListNewLivreToAttach.getClass(), livreListNewLivreToAttach.getIdLivre());
                attachedLivreListNew.add(livreListNewLivreToAttach);
            }
            livreListNew = attachedLivreListNew;
            etatLivre.setLivreList(livreListNew);
            etatLivre = em.merge(etatLivre);
            for (Livre livreListOldLivre : livreListOld)
            {
                if (!livreListNew.contains(livreListOldLivre))
                {
                    livreListOldLivre.setEtatLivre(null);
                    livreListOldLivre = em.merge(livreListOldLivre);
                }
            }
            for (Livre livreListNewLivre : livreListNew)
            {
                if (!livreListOld.contains(livreListNewLivre))
                {
                    EtatLivre oldIdEtatLivreOfLivreListNewLivre = livreListNewLivre.getEtatLivre();
                    livreListNewLivre.setEtatLivre(etatLivre);
                    livreListNewLivre = em.merge(livreListNewLivre);
                    if (oldIdEtatLivreOfLivreListNewLivre != null && !oldIdEtatLivreOfLivreListNewLivre.equals(etatLivre))
                    {
                        oldIdEtatLivreOfLivreListNewLivre.getLivreList().remove(livreListNewLivre);
                        oldIdEtatLivreOfLivreListNewLivre = em.merge(oldIdEtatLivreOfLivreListNewLivre);
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
                String id = etatLivre.getIdEtatLivre();
                if (findEtatLivre(id) == null)
                {
                    throw new NonexistentEntityException("The etatLivre with id " + id + " no longer exists.");
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
            EtatLivre etatLivre;
            try
            {
                etatLivre = em.getReference(EtatLivre.class, id);
                etatLivre.getIdEtatLivre();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The etatLivre with id " + id + " no longer exists.", enfe);
            }
            List<Livre> livreList = etatLivre.getLivreList();
            for (Livre livreListLivre : livreList)
            {
                livreListLivre.setEtatLivre(null);
                livreListLivre = em.merge(livreListLivre);
            }
            em.remove(etatLivre);
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

    public List<EtatLivre> findEtatLivreEntities()
    {
        return findEtatLivreEntities(true, -1, -1);
    }

    public List<EtatLivre> findEtatLivreEntities(int maxResults, int firstResult)
    {
        return findEtatLivreEntities(false, maxResults, firstResult);
    }

    private List<EtatLivre> findEtatLivreEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EtatLivre.class));
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

    public EtatLivre findEtatLivre(String id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(EtatLivre.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getEtatLivreCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EtatLivre> rt = cq.from(EtatLivre.class);
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