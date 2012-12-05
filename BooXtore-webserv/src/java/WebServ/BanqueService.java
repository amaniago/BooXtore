package WebServ;

import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Web service �mulant un service banquaire
 */
@WebService(serviceName = "BanqueService")
@Stateless()
public class BanqueService
{
    /**
     * Op�ration �mulant la validation de la transaction
     */
    @WebMethod(operationName = "transaction")
    public Boolean transaction(@WebParam(name = "numCb") String numCb)
    {
        return numCb.length() == 19 && Pattern.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", numCb);
    }
}