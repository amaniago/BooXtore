package Test;

import javax.ejb.embeddable.EJBContainer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Classe gérant le container de test
 */
public class TestSuite
{
    public static EJBContainer container;

    public TestSuite()
    {
    }

    /**
     * Initialise le container d'EJB
     */
    @BeforeSuite
    public void getContainer()
    {
        container = EJBContainer.createEJBContainer();
    }

    /**
     * Ferme le container d'EJB
     */
    @AfterSuite
    public void closeContainer()
    {
        container.close();
    }
}