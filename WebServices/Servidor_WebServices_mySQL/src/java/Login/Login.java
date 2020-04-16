/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Hibernate.NewHibernateUtil;
import Hibernate.User;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author PersonalCastro
 */
@WebService(serviceName = "Login")
public class Login {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public String login(@WebParam(name = "login") String login, @WebParam(name = "password") String password) {
        
        Session s = NewHibernateUtil.getInstance().getSessionFactory().openSession();
        Query q = s.createQuery("FROM User").setReadOnly(true);
        List<User> listaUsuarios = (List<User>) q.list();
        
        String encontrado = "no";
        for(User aux : listaUsuarios){
            if(aux.getIdUser().equals(login) && aux.getPassword().equals(password)){
                encontrado = "Accepted"; 
            }
        }
        return encontrado;
    }
}
