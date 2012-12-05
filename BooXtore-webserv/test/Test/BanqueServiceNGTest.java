package Test;

import WebServ.BanqueService;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BanqueServiceNGTest
{
    private BanqueService instance;

    public BanqueServiceNGTest()
    {
    }

    @BeforeClass
    public void setUpClass() throws Exception
    {
        instance = new BanqueService();
    }

    @AfterClass
    public void tearDownClass() throws Exception
    {
    }

    /**
     * Test de la méthode transaction, du webservice BanqueService.
     */
    @Test
    public void transactionTest() throws Exception
    {
        String numCb = "1234-1234-1234-1234";
        assertTrue(instance.transaction(numCb));
    }

    /**
     * Test de la méthode transaction, du webservice BanqueService.
     */
    @Test
    public void transactionTestFail() throws Exception
    {
        String numCb = "_";
        assertFalse(instance.transaction(numCb));
    }
}