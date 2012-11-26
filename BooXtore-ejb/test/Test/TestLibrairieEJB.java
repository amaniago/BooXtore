package Test;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.EtatLivre;
import Jpa.Classes.Livre;
import java.math.BigDecimal;
import java.sql.Date;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
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
 * Classe regroupant les tests unitaires de l'EJB LibrairieEJB
 */
public class TestLibrairieEJB
{
    private EJBContainer container;
    private LibrairieEJBRemote ejb;
    private final int idLivreTest = 130;
    private static EntityManagerFactory emf;
    private Livre livreInsert;

    public TestLibrairieEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws NamingException
    {
        container = EJBContainer.createEJBContainer();
        ejb = (LibrairieEJBRemote) container.getContext().lookup("java:global/classes/LibrairieEJB");
        emf = Persistence.createEntityManagerFactory("BooXtorePU");
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
        container.close();
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
     * Test de la méthode getListe, de l'EJB LibrairieEJB.
     */
    @Test
    public void getListeTest()
    {
        Assert.assertNotNull(ejb.getListe());
        Assert.assertNotEquals(ejb.getListe().size(), 0);
    }

    /**
     * Test de la méthode getTop10, de l'EJB LibrairieEJB.
     */
    @Test
    public void getTop10Test()
    {
        Assert.assertNotNull(ejb.getTop10());
    }

    /**
     * Test de la méthode getLivre, de l'EJB LibrairieEJB.
     */
    @Test
    public void getLivreTest()
    {
        Assert.assertEquals(ejb.getLivre(idLivreTest).getTitre(), "Test");
    }

    /**
     * Test de la méthode ajouterLivre, de l'EJB LibrairieEJB.
     */
    @Test
    public void ajouterLivreTest()
    {
        livreInsert = ejb.ajouterLivre("Test", new Date(System.currentTimeMillis()), null, null, 5, null, null, BigDecimal.ZERO, "N", 1);
        Assert.assertNotNull(livreInsert);
        Assert.assertEquals(livreInsert.getTitre(), "Test");
        Assert.assertNotNull(livreInsert.getIdLivre());
    }

    /**
     * Test de la méthode modifierLivre, de l'EJB LibrairieEJB.
     */
    @Test (dependsOnMethods= { "ajouterLivreTest" })
    public void modifierLivreTest()
    {
        EntityManager em = emf.createEntityManager();
        livreInsert.setEtatLivre(em.find(EtatLivre.class, "S"));
        ejb.modifierLivre(livreInsert);
        emf.getCache().evictAll();  //Synchro du contexte
        Livre livre = em.find(Livre.class, livreInsert.getIdLivre());
        em.close();
        Assert.assertEquals(livreInsert, livre);
    }

    /**
     * Test de la méthode supprimerLivre, de l'EJB LibrairieEJB.
     */
    @Test (dependsOnMethods= { "ajouterLivreTest" })
    public void supprimerLivreTest()
    {
        int id = livreInsert.getIdLivre();
        ejb.supprimerLivre(livreInsert);
        emf.getCache().evictAll();  //Synchro du contexte
        EntityManager em = emf.createEntityManager();
        Livre livre = em.find(Livre.class, id);
        em.close();
        Assert.assertNull(livre);
    }

    /**
     * Test de la méthode ajouterCategorie, de l'EJB LibrairieEJB.
     */
    @Test
    public void ajouterCategorieTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Test de la méthode modifierCategorie, de l'EJB LibrairieEJB.
     */
    @Test (dependsOnMethods= { "ajouterCategorieTest" })
    public void modifierCategorieTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Test de la méthode supprimerCategorie, de l'EJB LibrairieEJB.
     */
    @Test (dependsOnMethods= { "ajouterCategorieTest" })
    public void supprimerCategorieTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}