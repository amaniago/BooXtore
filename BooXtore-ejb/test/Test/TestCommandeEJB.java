package Test;

import Ejb.CommandeEJBRemote;
import Jpa.Classes.Client;
import Jpa.Classes.Commande;
import Jpa.Classes.EtatCommande;
import Jpa.Classes.Livre;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Classe regroupant les tests unitaires de l'EJB CommandeEJB
 */
public class TestCommandeEJB
{
    private CommandeEJBRemote ejb;
    private static EntityManagerFactory emf;
    private Commande commandeInsert;

    public TestCommandeEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws Exception
    {
        ejb = (CommandeEJBRemote) TestSuite.container.getContext().lookup("java:global/classes/CommandeEJB");
        emf = Persistence.createEntityManagerFactory("BooXtorePU");
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
        emf.close();
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
    @Test(dependsOnMethods = { "creationCommandeTest" })
    public void getCommandeTest()
    {
        Commande commande = ejb.getCommande(commandeInsert.getIdCommande());
        Assert.assertNotNull(commande);
    }

    /**
     * Test de la méthode setEtatCommande, de l'EJB CommandeEJB.
     */
    @Test(dependsOnMethods = { "creationCommandeTest" })
    public void setEtatCommandeTest()
    {
        ejb.setEtatCommande(commandeInsert, "A");
        EntityManager em = emf.createEntityManager();
        commandeInsert = em.merge(commandeInsert);
        em.refresh(commandeInsert);
        em.close();
        Assert.assertEquals(commandeInsert.getEtatCommande().getIdEtatCommande(), "A");
    }

    /**
     * Test de la méthode getCommandes, de l'EJB CommandeEJB.
     */
    @Test
    public void getCommandesTest()
    {
        List<Commande> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getCommandes());
        Assert.assertFalse(ejb.getCommandes().isEmpty());
        Assert.assertEquals(ejb.getCommandes().getClass(), lst.getClass());
    }

    /**
     * Test de la méthode getEtats, de l'EJB CommandeEJB.
     */
    @Test
    public void getEtatsTest()
    {
        List<EtatCommande> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getEtats());
        Assert.assertFalse(ejb.getEtats().isEmpty());
        Assert.assertEquals(ejb.getEtats().getClass(), lst.getClass());
    }

    /**
     * Test de la méthode getHisto, de l'EJB CommandeEJB.
     */
    @Test
    public void getHistoTest()
    {
        List<Commande> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getHisto("Bafur"));
        Assert.assertFalse(ejb.getHisto("Bafur").isEmpty());
        Assert.assertEquals(ejb.getHisto("Bafur").getClass(), lst.getClass());
    }
}