package introsde.finalproject.localdatabase.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum UserDao {
    instance;
    private EntityManagerFactory emf;

    private UserDao() {
        if (emf!=null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("localdatabase-service");
    }

    public EntityManager createEntityManager() {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;    
    }

    public void closeConnections(EntityManager em) {
        em.close();
    }

    public EntityTransaction getTransaction(EntityManager em) {
        return em.getTransaction();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }  
}
