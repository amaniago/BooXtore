/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa.Dao;

import Jpa.Classes.Client;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Jpa.Classes.Compte;
import Jpa.Classes.Commande;
import Jpa.Dao.exceptions.IllegalOrphanException;
import Jpa.Dao.exceptions.NonexistentEntityException;
import Jpa.Dao.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author MANIAGO
 */
public class ClientJpaController implements Serializable {

    public ClientJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) throws PreexistingEntityException, Exception {
        if (client.getCommandeList() == null) {
            client.setCommandeList(new ArrayList<Commande>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compte idCompte = client.getIdCompte();
            if (idCompte != null) {
                idCompte = em.getReference(idCompte.getClass(), idCompte.getIdCompte());
                client.setIdCompte(idCompte);
            }
            List<Commande> attachedCommandeList = new ArrayList<Commande>();
            for (Commande commandeListCommandeToAttach : client.getCommandeList()) {
                commandeListCommandeToAttach = em.getReference(commandeListCommandeToAttach.getClass(), commandeListCommandeToAttach.getIdCommande());
                attachedCommandeList.add(commandeListCommandeToAttach);
            }
            client.setCommandeList(attachedCommandeList);
            em.persist(client);
            if (idCompte != null) {
                idCompte.getClientList().add(client);
                idCompte = em.merge(idCompte);
            }
            for (Commande commandeListCommande : client.getCommandeList()) {
                Client oldLoginOfCommandeListCommande = commandeListCommande.getLogin();
                commandeListCommande.setLogin(client);
                commandeListCommande = em.merge(commandeListCommande);
                if (oldLoginOfCommandeListCommande != null) {
                    oldLoginOfCommandeListCommande.getCommandeList().remove(commandeListCommande);
                    oldLoginOfCommandeListCommande = em.merge(oldLoginOfCommandeListCommande);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClient(client.getLogin()) != null) {
                throw new PreexistingEntityException("Client " + client + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client persistentClient = em.find(Client.class, client.getLogin());
            Compte idCompteOld = persistentClient.getIdCompte();
            Compte idCompteNew = client.getIdCompte();
            List<Commande> commandeListOld = persistentClient.getCommandeList();
            List<Commande> commandeListNew = client.getCommandeList();
            List<String> illegalOrphanMessages = null;
            for (Commande commandeListOldCommande : commandeListOld) {
                if (!commandeListNew.contains(commandeListOldCommande)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Commande " + commandeListOldCommande + " since its login field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCompteNew != null) {
                idCompteNew = em.getReference(idCompteNew.getClass(), idCompteNew.getIdCompte());
                client.setIdCompte(idCompteNew);
            }
            List<Commande> attachedCommandeListNew = new ArrayList<Commande>();
            for (Commande commandeListNewCommandeToAttach : commandeListNew) {
                commandeListNewCommandeToAttach = em.getReference(commandeListNewCommandeToAttach.getClass(), commandeListNewCommandeToAttach.getIdCommande());
                attachedCommandeListNew.add(commandeListNewCommandeToAttach);
            }
            commandeListNew = attachedCommandeListNew;
            client.setCommandeList(commandeListNew);
            client = em.merge(client);
            if (idCompteOld != null && !idCompteOld.equals(idCompteNew)) {
                idCompteOld.getClientList().remove(client);
                idCompteOld = em.merge(idCompteOld);
            }
            if (idCompteNew != null && !idCompteNew.equals(idCompteOld)) {
                idCompteNew.getClientList().add(client);
                idCompteNew = em.merge(idCompteNew);
            }
            for (Commande commandeListNewCommande : commandeListNew) {
                if (!commandeListOld.contains(commandeListNewCommande)) {
                    Client oldLoginOfCommandeListNewCommande = commandeListNewCommande.getLogin();
                    commandeListNewCommande.setLogin(client);
                    commandeListNewCommande = em.merge(commandeListNewCommande);
                    if (oldLoginOfCommandeListNewCommande != null && !oldLoginOfCommandeListNewCommande.equals(client)) {
                        oldLoginOfCommandeListNewCommande.getCommandeList().remove(commandeListNewCommande);
                        oldLoginOfCommandeListNewCommande = em.merge(oldLoginOfCommandeListNewCommande);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = client.getLogin();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getLogin();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Commande> commandeListOrphanCheck = client.getCommandeList();
            for (Commande commandeListOrphanCheckCommande : commandeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Commande " + commandeListOrphanCheckCommande + " in its commandeList field has a non-nullable login field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Compte idCompte = client.getIdCompte();
            if (idCompte != null) {
                idCompte.getClientList().remove(client);
                idCompte = em.merge(idCompte);
            }
            em.remove(client);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
