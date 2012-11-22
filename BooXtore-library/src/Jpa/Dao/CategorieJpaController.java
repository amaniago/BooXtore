package Jpa.Dao;

import Jpa.Classes.Categorie;
import Jpa.Classes.Livre;
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

public class CategorieJpaController implements Serializable
{
    public CategorieJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Categorie categorie)
    {
        if (categorie.getLivreList() == null)
        {
            categorie.setLivreList(new ArrayList<Livre>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Livre> attachedLivreList = new ArrayList<>();
            for (Livre livreListLivreToAttach : categorie.getLivreList())
            {
                livreListLivreToAttach = em.getReference(livreListLivreToAttach.getClass(), livreListLivreToAttach.getIdLivre());
                attachedLivreList.add(livreListLivreToAttach);
            }
            categorie.setLivreList(attachedLivreList);
            em.persist(categorie);
            for (Livre livreListLivre : categorie.getLivreList())
            {
                Categorie oldCategorieOfLivreListLivre = livreListLivre.getCategorie();
                livreListLivre.setCategorie(categorie);
                livreListLivre = em.merge(livreListLivre);
                if (oldCategorieOfLivreListLivre != null)
                {
                    oldCategorieOfLivreListLivre.getLivreList().remove(livreListLivre);
                    oldCategorieOfLivreListLivre = em.merge(oldCategorieOfLivreListLivre);
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

    public void edit(Categorie categorie) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorie persistentCategorie = em.find(Categorie.class, categorie.getIdCategorie());
            List<Livre> livreListOld = persistentCategorie.getLivreList();
            List<Livre> livreListNew = categorie.getLivreList();
            List<String> illegalOrphanMessages = null;
            for (Livre livreListOldLivre : livreListOld)
            {
                if (!livreListNew.contains(livreListOldLivre))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Livre " + livreListOldLivre + " since its idCategorie field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Livre> attachedLivreListNew = new ArrayList<>();
            for (Livre livreListNewLivreToAttach : livreListNew)
            {
                livreListNewLivreToAttach = em.getReference(livreListNewLivreToAttach.getClass(), livreListNewLivreToAttach.getIdLivre());
                attachedLivreListNew.add(livreListNewLivreToAttach);
            }
            livreListNew = attachedLivreListNew;
            categorie.setLivreList(livreListNew);
            categorie = em.merge(categorie);
            for (Livre livreListNewLivre : livreListNew)
            {
                if (!livreListOld.contains(livreListNewLivre))
                {
                    Categorie oldCategorieOfLivreListNewLivre = livreListNewLivre.getCategorie();
                    livreListNewLivre.setCategorie(categorie);
                    livreListNewLivre = em.merge(livreListNewLivre);
                    if (oldCategorieOfLivreListNewLivre != null && !oldCategorieOfLivreListNewLivre.equals(categorie))
                    {
                        oldCategorieOfLivreListNewLivre.getLivreList().remove(livreListNewLivre);
                        oldCategorieOfLivreListNewLivre = em.merge(oldCategorieOfLivreListNewLivre);
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
                Integer id = categorie.getIdCategorie();
                if (findCategorie(id) == null)
                {
                    throw new NonexistentEntityException("The categorie with id " + id + " no longer exists.");
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
            Categorie categorie;
            try
            {
                categorie = em.getReference(Categorie.class, id);
                categorie.getIdCategorie();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The categorie with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Livre> livreListOrphanCheck = categorie.getLivreList();
            for (Livre livreListOrphanCheckLivre : livreListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Categorie (" + categorie + ") cannot be destroyed since the Livre " + livreListOrphanCheckLivre + " in its livreList field has a non-nullable idCategorie field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categorie);
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

    public List<Categorie> findCategorieEntities()
    {
        return findCategorieEntities(true, -1, -1);
    }

    public List<Categorie> findCategorieEntities(int maxResults, int firstResult)
    {
        return findCategorieEntities(false, maxResults, firstResult);
    }

    private List<Categorie> findCategorieEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorie.class));
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

    public Categorie findCategorie(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Categorie.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCategorieCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorie> rt = cq.from(Categorie.class);
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