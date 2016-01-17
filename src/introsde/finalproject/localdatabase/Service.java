package introsde.finalproject.localdatabase;

import introsde.finalproject.localdatabase.model.*;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import java.text.ParseException;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface Service {

    @WebMethod(operationName="getUser")
    @WebResult(name="user") 
    public User getUser(@WebParam(name="userId") Long id);

    @WebMethod(operationName="saveUser")
    @WebResult(name="user") 
    public void saveUser(@WebParam(name="user") User user);
    
}

