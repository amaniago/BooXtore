package Test;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
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
 * Classe regroupant les tests unitaires de l'EJB CompteEJB
 */
public class TestCompteEJB
{
    private EJBContainer container;
    private CompteEJBRemote ejb;
    private static EntityManagerFactory emf;
    private Client clientInsert;

    public TestCompteEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws Exception
    {
        container = EJBContainer.createEJBContainer();
        ejb = (CompteEJBRemote) container.getContext().lookup("java:global/classes/CompteEJB");
        emf = Persistence.createEntityManagerFactory("BooXtorePU");
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
        EntityManager em = emf.createEntityManager();
        Client client = em.find(Client.class, "test");
        if (client != null)
            em.remove(client);
        em.close();
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
     * Test de la méthode authentification, de l'EJB CompteEJB.
     */
    @Test (dependsOnMethods= { "inscriptionTest" })
    public void authentificationTest()
    {
        Assert.assertTrue(ejb.authentification("test", "mdp"));
    }

    /**
     * Test de la méthode inscription, de l'EJB CompteEJB.
     */
    @Test
    public void inscriptionTest()
    {
        clientInsert = ejb.inscription("test", "mdp", "nom", "prenom", "mail@mail.fr", "adr", "00000", "ville");
        Assert.assertNotNull(clientInsert);
        Assert.assertNotNull(clientInsert.getLogin());
    }

    /**
     * Test de la méthode modifierCompte, de l'EJB CompteEJB.
     */
    @Test (dependsOnMethods= { "inscriptionTest" })
    public void modifierCompteTest()
    {
        EntityManager em = emf.createEntityManager();
        clientInsert.setNom("modif");
        ejb.modifierCompte(clientInsert);
        emf.getCache().evictAll();  //Synchro du contexte
        Client client = em.find(Client.class, clientInsert.getLogin());
        em.close();
        Assert.assertEquals(clientInsert, client);
    }
}