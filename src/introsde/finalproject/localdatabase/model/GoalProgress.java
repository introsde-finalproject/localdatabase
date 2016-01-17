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
@Table(name = "GoalProgress")
@NamedQuery(name="GoalProgress.findAll", query="SELECT gp FROM GoalProgress gp")
@XmlType(propOrder={"goalProgressId", "goalProgressValue", "goalProgressDate"})
public class GoalProgress implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator="sqlite_goalProgress")
    @TableGenerator(name="sqlite_goalProgress", pkColumnName="name", valueColumnName="seq",
        pkColumnValue="goalprogress_id",initialValue=1, allocationSize=1)
    @Column(name = "goalProgressId")
    private long gpid;

    @Column(name = "goalProgressValue")
    private Double value;

    @Temporal(TemporalType.DATE)
    @Column(name="goalProgressDate")
    private Date date; 

    public long getGoalProgressId() {
        return gpid;
    }

    public Double getGoalProgressValue() {
        return value;
    }

    public String getGoalProgressDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return (date!=null) ? sdf.format(date) : null;
    }

    public void setGoalProgressId(long id){
        this.gpid = gpid;
    }

    public void setGoalProgressValue(Double value){
        this.value = value;
    }

    public void setGoalProgressDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.date = sdf.parse(date);
    }    

    public static GoalProgress getGoalProgressById(long gpid) {
        EntityManager em = UserDao.instance.createEntityManager();
        GoalProgress gp = em.find(GoalProgress.class, gpid);
        UserDao.instance.closeConnections(em);
        return gp;
    }

    public static List<GoalProgress> getAll() {
        EntityManager em = UserDao.instance.createEntityManager();
        List<GoalProgress> list = em.createNamedQuery("GoalProgress.findAll", GoalProgress.class).getResultList();
        UserDao.instance.closeConnections(em);
        return list;
    }

    public static GoalProgress save(GoalProgress gp) {
        EntityManager em = UserDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(gp);
        tx.commit();
        UserDao.instance.closeConnections(em);
        return gp;
    } 

    public static GoalProgress update(GoalProgress gp) {
        EntityManager em = UserDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        gp=em.merge(gp);
        tx.commit();
        UserDao.instance.closeConnections(em);
        return gp;
    }

    public static void remove(GoalProgress gp) {
        EntityManager em = UserDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        gp=em.merge(gp);
        em.remove(gp);
        tx.commit();
        UserDao.instance.closeConnections(em);
    }
}
