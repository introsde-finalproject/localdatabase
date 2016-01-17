package introsde.finalproject.localdatabase.model;

import introsde.finalproject.localdatabase.dao.UserDao;
import introsde.finalproject.localdatabase.model.Measure;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.*;


@Entity
@Table(name = "User")
@NamedQuery(name="User.findAll", query="SELECT p FROM User p")
@XmlType(propOrder={"userId", "firstname", "lastname", "birthdate", "currentHealth", "healthHistory", "goals"})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator="sqlite_user")
    @TableGenerator(name="sqlite_user", pkColumnName="name", valueColumnName="seq",
        pkColumnValue="user_id",initialValue=1, allocationSize=1)
    @Column(name = "userId")
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Temporal(TemporalType.DATE)
    @Column(name="birthdate")
    private Date birthdate; 

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CurrentHealth")
    List<Measure> currentHealth;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "HealthMeasureHistory")
    List<Measure> healthHistory;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName="userId")
    private List<Goal> goals;

    
    public long getUserId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return (birthdate!=null) ? sdf.format(birthdate) : null;
    }

    public List<Measure> getCurrentHealth() {
        return currentHealth;
    }

    public List<Measure> getHealthHistory() {
        return healthHistory;
    }

    public List<Goal> getGoals() {
        return goals;
    }
    
    
    public void setUserId(long id){
        this.id = id;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setBirthdate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.birthdate = sdf.parse(date);
    }

    public void setCurrentHealth(List<Measure> currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setHealthHistory(List<Measure> healthHistory) {
        this.healthHistory = healthHistory;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
    

    public static User getUserById(long id) {
        EntityManager em = UserDao.instance.createEntityManager();
        User u = em.find(User.class, id);
        UserDao.instance.closeConnections(em);
        return u;
    }

    public static List<User> getAll() {
        EntityManager em = UserDao.instance.createEntityManager();
        List<User> list = em.createNamedQuery("User.findAll", User.class).getResultList();
        UserDao.instance.closeConnections(em);
        return list;
    }

    public static User save(User u) {
        EntityManager em = UserDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(u);
        tx.commit();
        UserDao.instance.closeConnections(em);
        return u;
    } 

    public static User update(User u) {
        EntityManager em = UserDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        u=em.merge(u);
        tx.commit();
        UserDao.instance.closeConnections(em);
        return u;
    }

    public static void remove(User u) {
        EntityManager em = UserDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        u=em.merge(u);
        em.remove(u);
        tx.commit();
        UserDao.instance.closeConnections(em);
    }
}
