package Jpa.Dao;

import Jpa.Classes.Client;
import Jpa.Classes.Compte;
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

public class CompteJpaController implements Serializable
{
    public CompteJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Compte compte)
    {
        if (compte.getClientList() == null)
        {
            compte.setClientList(new ArrayList<Client>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Client> attachedClientList = new ArrayList<>();
            for (Client clientListClientToAttach : compte.getClientList())
            {
                clientListClientToAttach = em.getReference(clientListClientToAttach.getClass(), clientListClientToAttach.getLogin());
                attachedClientList.add(clientListClientToAttach);
            }
            compte.setClientList(attachedClientList);
            em.persist(compte);
            for (Client clientListClient : compte.getClientList())
            {
                Compte oldIdCompteOfClientListClient = clientListClient.getCompte();
                clientListClient.setCompte(compte);
                clientListClient = em.merge(clientListClient);
                if (oldIdCompteOfClientListClient != null)
                {
                    oldIdCompteOfClientListClient.getClientList().remove(clientListClient);
                    oldIdCompteOfClientListClient = em.merge(oldIdCompteOfClientListClient);
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

    public void edit(Compte compte) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Compte persistentCompte = em.find(Compte.class, compte.getIdCompte());
            List<Client> clientListOld = persistentCompte.getClientList();
            List<Client> clientListNew = compte.getClientList();
            List<Client> attachedClientListNew = new ArrayList<>();
            for (Client clientListNewClientToAttach : clientListNew)
            {
                clientListNewClientToAttach = em.getReference(clientListNewClientToAttach.getClass(), clientListNewClientToAttach.getLogin());
                attachedClientListNew.add(clientListNewClientToAttach);
            }
            clientListNew = attachedClientListNew;
            compte.setClientList(clientListNew);
            compte = em.merge(compte);
            for (Client clientListOldClient : clientListOld)
            {
                if (!clientListNew.contains(clientListOldClient))
                {
                    clientListOldClient.setCompte(null);
                    clientListOldClient = em.merge(clientListOldClient);
                }
            }
            for (Client clientListNewClient : clientListNew)
            {
                if (!clientListOld.contains(clientListNewClient))
                {
                    Compte oldIdCompteOfClientListNewClient = clientListNewClient.getCompte();
                    clientListNewClient.setCompte(compte);
                    clientListNewClient = em.merge(clientListNewClient);
                    if (oldIdCompteOfClientListNewClient != null && !oldIdCompteOfClientListNewClient.equals(compte))
                    {
                        oldIdCompteOfClientListNewClient.getClientList().remove(clientListNewClient);
                        oldIdCompteOfClientListNewClient = em.merge(oldIdCompteOfClientListNewClient);
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
                Integer id = compte.getIdCompte();
                if (findCompte(id) == null)
                {
                    throw new NonexistentEntityException("The compte with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Compte compte;
            try
            {
                compte = em.getReference(Compte.class, id);
                compte.getIdCompte();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The compte with id " + id + " no longer exists.", enfe);
            }
            List<Client> clientList = compte.getClientList();
            for (Client clientListClient : clientList)
            {
                clientListClient.setCompte(null);
                clientListClient = em.merge(clientListClient);
            }
            em.remove(compte);
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

    public List<Compte> findCompteEntities()
    {
        return findCompteEntities(true, -1, -1);
    }

    public List<Compte> findCompteEntities(int maxResults, int firstResult)
    {
        return findCompteEntities(false, maxResults, firstResult);
    }

    private List<Compte> findCompteEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compte.class));
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

    public Compte findCompte(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Compte.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCompteCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compte> rt = cq.from(Compte.class);
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