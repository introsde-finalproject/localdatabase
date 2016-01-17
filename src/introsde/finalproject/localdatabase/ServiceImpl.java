package introsde.finalproject.localdatabase;

import introsde.finalproject.localdatabase.model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet; 
import java.util.Set;
import java.text.SimpleDateFormat;
import javax.jws.WebService;
import java.text.ParseException;

@WebService(endpointInterface = "introsde.finalproject.localdatabase.Service", serviceName="LocalDatabaseService")
public class ServiceImpl implements Service {

    @Override
    public User getUser(Long id) {
    	User u = User.getUserById(id);
    	System.out.println(u);
        return User.getUserById(id);
    }

    @Override
    public void saveUser(User user) {
        User.update(user);
    }
}
