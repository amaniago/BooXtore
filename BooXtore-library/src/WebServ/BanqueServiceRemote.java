package WebServ;

import javax.jws.WebMethod;
import javax.jws.WebParam;

public interface BanqueServiceRemote
{
    @WebMethod(operationName = "transaction")
    Boolean transaction(@WebParam(name = "numCb") String numCb);
}