package Test;

import Ejb.LibrairieEJBRemote;
import Jpa.Classes.Categorie;
import Jpa.Classes.EtatLivre;
import Jpa.Classes.Livre;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
    private LibrairieEJBRemote ejb;
    private final int idLivreTest = 130;
    private static EntityManagerFactory emf;
    private Livre livreInsert;
    private Categorie categorieInsert;

    public TestLibrairieEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws Exception
    {
        ejb = (LibrairieEJBRemote) TestSuite.container.getContext().lookup("java:global/classes/LibrairieEJB");
        emf = Persistence.createEntityManagerFactory("BooXtorePU");
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
        emf.close();
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
     * Test de la méthode getLivres, de l'EJB LibrairieEJB.
     */
    @Test
    public void getLivresTest()
    {
        List<Livre> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getLivres());
        Assert.assertFalse(ejb.getLivres().isEmpty());
        Assert.assertEquals(ejb.getLivres().getClass(), lst.getClass());
    }

    /**
     * Test de la méthode getTop10, de l'EJB LibrairieEJB.
     */
    @Test
    public void getTop10Test()
    {
        List<Livre> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getTop10());
        Assert.assertEquals(ejb.getLivres().getClass(), lst.getClass());
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
    @Test(dependsOnMethods = { "ajouterLivreTest" })
    public void modifierLivreTest()
    {
        EntityManager em = emf.createEntityManager();
        livreInsert.setEtatLivre(em.find(EtatLivre.class, "S"));
        ejb.modifierLivre(livreInsert);
        livreInsert = em.merge(livreInsert);
        em.refresh(livreInsert);
        em.close();
        Assert.assertEquals(livreInsert.getEtatLivre().getIdEtatLivre(), "S");
    }

    /**
     * Test de la méthode supprimerLivre, de l'EJB LibrairieEJB.
     */
    @Test(dependsOnMethods = { "ajouterLivreTest" })
    public void supprimerLivreTest()
    {
        ejb.supprimerLivre(livreInsert);
        EntityManager em = emf.createEntityManager();
        boolean manage = em.contains(livreInsert);
        em.close();
        Assert.assertFalse(manage);
    }

    /**
     * Test de la méthode getCategories, de l'EJB LibrairieEJB.
     */
    @Test
    public void getCategoriesTest()
    {
        List<Livre> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getCategories());
        Assert.assertFalse(ejb.getCategories().isEmpty());
        Assert.assertEquals(ejb.getCategories().getClass(), lst.getClass());
    }

    /**
     * Test de la méthode getEtatsLivre, de l'EJB LibrairieEJB.
     */
    @Test
    public void getEtatsLivreTest()
    {
        List<Livre> lst = new ArrayList<>();
        Assert.assertNotNull(ejb.getEtatsLivre());
        Assert.assertFalse(ejb.getEtatsLivre().isEmpty());
        Assert.assertEquals(ejb.getEtatsLivre().getClass(), lst.getClass());
    }

    /**
     * Test de la méthode ajouterCategorie, de l'EJB LibrairieEJB.
     */
    @Test
    public void ajouterCategorieTest()
    {
        categorieInsert = ejb.ajouterCategorie("Test");
        Assert.assertNotNull(categorieInsert);
        Assert.assertEquals(categorieInsert.getNomCategorie(), "Test");
        Assert.assertNotNull(categorieInsert.getIdCategorie());
    }

    /**
     * Test de la méthode modifierCategorie, de l'EJB LibrairieEJB.
     */
    @Test(dependsOnMethods = { "ajouterCategorieTest" })
    public void modifierCategorieTest()
    {
        EntityManager em = emf.createEntityManager();
        categorieInsert.setNomCategorie("TestModif");
        ejb.modifierCategorie(categorieInsert);
        categorieInsert = em.merge(categorieInsert);
        em.refresh(categorieInsert);
        em.close();
        Assert.assertEquals(categorieInsert.getNomCategorie(), "TestModif");
    }

    /**
     * Test de la méthode supprimerCategorie, de l'EJB LibrairieEJB.
     */
    @Test(dependsOnMethods = { "ajouterCategorieTest" })
    public void supprimerCategorieTest()
    {
        ejb.supprimerCategorie(categorieInsert);
        EntityManager em = emf.createEntityManager();
        boolean manage = em.contains(categorieInsert);
        em.close();
        Assert.assertFalse(manage);
    }
}