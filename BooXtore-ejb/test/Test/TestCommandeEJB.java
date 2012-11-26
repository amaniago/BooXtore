package Test;

import Ejb.CommandeEJBRemote;
import Jpa.Classes.Client;
import Jpa.Classes.Commande;
import Jpa.Classes.Livre;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Classe regroupant les tests unitaires de l'EJB CommandeEJB
 */
public class TestCommandeEJB
{
    private EJBContainer container;
    private CommandeEJBRemote ejb;
    private static EntityManagerFactory emf;
    private Commande commandeInsert;

    public TestCommandeEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws Exception
    {
        container = EJBContainer.createEJBContainer();
        ejb = (CommandeEJBRemote) container.getContext().lookup("java:global/classes/CommandeEJB");
        emf = Persistence.createEntityManagerFactory("BooXtorePU");
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception
    {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception
    {
    }

    /**
     * Test de la méthode creationCommande, de l'EJB CommandeEJB.
     */
    @Test
    public void creationCommandeTest()
    {
        EntityManager em = emf.createEntityManager();
        Client client = em.find(Client.class, "Bafur");
        Map<Livre, Integer> panier = new HashMap<>();
        panier.put(em.find(Livre.class, 1), 1);
        panier.put(em.find(Livre.class, 3), 1);
        em.close();
        commandeInsert = ejb.creationCommande(client, panier);
        Assert.assertNotNull(commandeInsert);
        Assert.assertNotNull(commandeInsert.getIdCommande());
    }

    /**
     * Test de la méthode getCommande, de l'EJB CommandeEJB.
     */
    @Test (dependsOnMethods= { "creationCommandeTest" })
    public void getCommandeTest()
    {
        Commande commande = ejb.getCommande(commandeInsert.getIdCommande());
        Assert.assertNotNull(commande);
    }

    /**
     * Test de la méthode setEtatCommande, de l'EJB CommandeEJB.
     */
    @Test (dependsOnMethods= { "creationCommandeTest" })
    public void setEtatCommandeTest()
    {
        ejb.setEtatCommande(commandeInsert, "A");
        emf.getCache().evictAll();  //Synchro du contexte
        EntityManager em = emf.createEntityManager();
        Commande commande = em.find(Commande.class, commandeInsert.getIdCommande());
        em.close();
        Assert.assertEquals(commande.getEtatCommande().getIdEtatCommande(), "A");
    }
}