package WebServ;

import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Web service émulant un service banquaire
 */
@WebService(serviceName = "BanqueService")
@Stateless()
public class BanqueService implements BanqueServiceRemote
{
    /**
     * Opération émulant la validation de la transaction
     */
    @WebMethod(operationName = "transaction")
    @Override
    public Boolean transaction(@WebParam(name = "numCb") String numCb)
    {
        return numCb.length() == 16 && Pattern.matches("\\d+", numCb);
    }
}