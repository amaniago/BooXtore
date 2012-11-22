package Jpa.Dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Jpa.Classes.EtatLivre;
import Jpa.Classes.Categorie;
import Jpa.Classes.Contenir;
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

public class LivreJpaController implements Serializable
{
    public LivreJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Livre livre)
    {
        if (livre.getContenirList() == null)
        {
            livre.setContenirList(new ArrayList<Contenir>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            EtatLivre idEtatLivre = livre.getIdEtatLivre();
            if (idEtatLivre != null) {
                idEtatLivre = em.getReference(idEtatLivre.getClass(), idEtatLivre.getIdEtatLivre());
                livre.setIdEtatLivre(idEtatLivre);
            }
            Categorie idCategorie = livre.getIdCategorie();
            if (idCategorie != null) {
                idCategorie = em.getReference(idCategorie.getClass(), idCategorie.getIdCategorie());
                livre.setIdCategorie(idCategorie);
            }
            List<Contenir> attachedContenirList = new ArrayList<Contenir>();
            for (Contenir contenirListContenirToAttach : livre.getContenirList()) {
                contenirListContenirToAttach = em.getReference(contenirListContenirToAttach.getClass(), contenirListContenirToAttach.getContenirPK());
                attachedContenirList.add(contenirListContenirToAttach);
            }
            livre.setContenirList(attachedContenirList);
            em.persist(livre);
            if (idEtatLivre != null) {
                idEtatLivre.getLivreList().add(livre);
                idEtatLivre = em.merge(idEtatLivre);
            }
            if (idCategorie != null) {
                idCategorie.getLivreList().add(livre);
                idCategorie = em.merge(idCategorie);
            }
            for (Contenir contenirListContenir : livre.getContenirList())
            {
                Livre oldLivreOfContenirListContenir = contenirListContenir.getLivre();
                contenirListContenir.setLivre(livre);
                contenirListContenir = em.merge(contenirListContenir);
                if (oldLivreOfContenirListContenir != null)
                {
                    oldLivreOfContenirListContenir.getContenirList().remove(contenirListContenir);
                    oldLivreOfContenirListContenir = em.merge(oldLivreOfContenirListContenir);
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

    public void edit(Livre livre) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Livre persistentLivre = em.find(Livre.class, livre.getIdLivre());
            Categorie idCategorieOld = persistentLivre.getIdCategorie();
            Categorie idCategorieNew = livre.getIdCategorie();
            List<Contenir> contenirListOld = persistentLivre.getContenirList();
            List<Contenir> contenirListNew = livre.getContenirList();
            List<String> illegalOrphanMessages = null;
            for (Contenir contenirListOldContenir : contenirListOld)
            {
                if (!contenirListNew.contains(contenirListOldContenir))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Contenir " + contenirListOldContenir + " since its livre field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEtatLivreNew != null) {
                idEtatLivreNew = em.getReference(idEtatLivreNew.getClass(), idEtatLivreNew.getIdEtatLivre());
                livre.setIdEtatLivre(idEtatLivreNew);
            }
            if (idCategorieNew != null) {
                idCategorieNew = em.getReference(idCategorieNew.getClass(), idCategorieNew.getIdCategorie());
                livre.setIdCategorie(idCategorieNew);
            }
            List<Contenir> attachedContenirListNew = new ArrayList<>();
            for (Contenir contenirListNewContenirToAttach : contenirListNew)
            {
                contenirListNewContenirToAttach = em.getReference(contenirListNewContenirToAttach.getClass(), contenirListNewContenirToAttach.getContenirPK());
                attachedContenirListNew.add(contenirListNewContenirToAttach);
            }
            contenirListNew = attachedContenirListNew;
            livre.setContenirList(contenirListNew);
            livre = em.merge(livre);
            if (idEtatLivreOld != null && !idEtatLivreOld.equals(idEtatLivreNew)) {
                idEtatLivreOld.getLivreList().remove(livre);
                idEtatLivreOld = em.merge(idEtatLivreOld);
            }
            if (idEtatLivreNew != null && !idEtatLivreNew.equals(idEtatLivreOld)) {
                idEtatLivreNew.getLivreList().add(livre);
                idEtatLivreNew = em.merge(idEtatLivreNew);
            }
            if (idCategorieOld != null && !idCategorieOld.equals(idCategorieNew)) {
                idCategorieOld.getLivreList().remove(livre);
                idCategorieOld = em.merge(idCategorieOld);
            }
            if (categorieNew != null && !categorieNew.equals(categorieOld))
            {
                categorieNew.getLivreList().add(livre);
                categorieNew = em.merge(categorieNew);
            }
            for (Contenir contenirListNewContenir : contenirListNew)
            {
                if (!contenirListOld.contains(contenirListNewContenir))
                {
                    Livre oldLivreOfContenirListNewContenir = contenirListNewContenir.getLivre();
                    contenirListNewContenir.setLivre(livre);
                    contenirListNewContenir = em.merge(contenirListNewContenir);
                    if (oldLivreOfContenirListNewContenir != null && !oldLivreOfContenirListNewContenir.equals(livre))
                    {
                        oldLivreOfContenirListNewContenir.getContenirList().remove(contenirListNewContenir);
                        oldLivreOfContenirListNewContenir = em.merge(oldLivreOfContenirListNewContenir);
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
                Integer id = livre.getIdLivre();
                if (findLivre(id) == null)
                {
                    throw new NonexistentEntityException("The livre with id " + id + " no longer exists.");
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
            Livre livre;
            try
            {
                livre = em.getReference(Livre.class, id);
                livre.getIdLivre();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The livre with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contenir> contenirListOrphanCheck = livre.getContenirList();
            for (Contenir contenirListOrphanCheckContenir : contenirListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Livre (" + livre + ") cannot be destroyed since the Contenir " + contenirListOrphanCheckContenir + " in its contenirList field has a non-nullable livre field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EtatLivre idEtatLivre = livre.getIdEtatLivre();
            if (idEtatLivre != null) {
                idEtatLivre.getLivreList().remove(livre);
                idEtatLivre = em.merge(idEtatLivre);
            }
            Categorie idCategorie = livre.getIdCategorie();
            if (idCategorie != null) {
                idCategorie.getLivreList().remove(livre);
                idCategorie = em.merge(idCategorie);
            }
            em.remove(livre);
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

    public List<Livre> findLivreEntities()
    {
        return findLivreEntities(true, -1, -1);
    }

    public List<Livre> findLivreEntities(int maxResults, int firstResult)
    {
        return findLivreEntities(false, maxResults, firstResult);
    }

    private List<Livre> findLivreEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Livre.class));
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

    public Livre findLivre(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Livre.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getLivreCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Livre> rt = cq.from(Livre.class);
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
