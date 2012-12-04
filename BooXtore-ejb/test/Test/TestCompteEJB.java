package Test;

import Ejb.CompteEJBRemote;
import Jpa.Classes.Client;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Classe regroupant les tests unitaires de l'EJB CompteEJB
 */
public class TestCompteEJB
{
    private CompteEJBRemote ejb;
    private static EntityManagerFactory emf;
    private Client clientInsert;

    public TestCompteEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws Exception
    {
        ejb = (CompteEJBRemote) TestSuite.container.getContext().lookup("java:global/classes/CompteEJB");
        emf = Persistence.createEntityManagerFactory("BooXtorePU");
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
        EntityManager em = emf.createEntityManager();
        Client client = em.find(Client.class, "test");
        if (client != null)
        {
            em.getTransaction().begin();
            em.remove(client);
            em.getTransaction().commit();
        }
        em.close();
        emf.close();
    }

    /**
     * Test de la méthode authentification, de l'EJB CompteEJB.
     */
    @Test(dependsOnMethods = { "inscriptionTest" })
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
     * Test de la méthode getLogin, de l'EJB CompteEJB.
     */
    @Test(dependsOnMethods = { "inscriptionTest" })
    public void getLoginTest()
    {
        Client client = ejb.getLogin("test");
        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getLogin());
    }

    /**
    * Test de la méthode getLogin, de l'EJB CompteEJB.
    */
    @Test
    public void getLoginFailTest()
    {
        Assert.assertNull(ejb.getLogin("_"));
    }

    /**
     * Test de la méthode modifierCompte, de l'EJB CompteEJB.
     */
    @Test(dependsOnMethods = { "inscriptionTest" })
    public void modifierCompteTest()
    {
        EntityManager em = emf.createEntityManager();
        clientInsert.setNom("modif");
        ejb.modifierCompte(clientInsert);
        clientInsert = em.merge(clientInsert);
        em.refresh(clientInsert);
        em.close();
        Assert.assertEquals(clientInsert.getNom(), "modif");
    }
}