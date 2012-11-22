package Test;

import Ejb.LibrairieEJBRemote;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestLibrairieEJB
{
//    LibrairieEJBRemote librairieEJB = lookupLibrairieEJBRemote();
    private EJBContainer container;
    private Context context;
    private LibrairieEJBRemote ejb;

    public TestLibrairieEJB()
    {
    }

    @BeforeClass
    public void setUpClass() throws NamingException
    {
        container = EJBContainer.createEJBContainer();
        context = container.getContext();
        ejb = (LibrairieEJBRemote) context.lookup("java:global/BooXtore/BooXtore-ejb/LibrairieEJB!Ejb.LibrairieEJBRemote");
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

    @Test
    public void getListeTest()
    {
        Assert.assertNotNull(ejb.getListe());
        Assert.assertNotEquals(ejb.getListe().size(), 0);
    }

    @Test
    public void getTop10Test()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Test
    public void getLivreTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Test
    public void setStockTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Test
    public void ajouterLivreTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Test
    public void modifierLivreTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Test
    public void supprimerLivreTest()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    private LibrairieEJBRemote lookupLibrairieEJBRemote()
//    {
//        try
//        {
//            Context c = new InitialContext();
//            return (LibrairieEJBRemote) c.lookup("java:global/BooXtore/BooXtore-ejb/LibrairieEJB!Ejb.LibrairieEJBRemote");
//        }
//        catch (NamingException ne)
//        {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
}