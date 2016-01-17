package introsde.finalproject.localdatabase.model;

import introsde.finalproject.localdatabase.dao.UserDao;
import introsde.finalproject.localdatabase.model.GoalProgress;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.*;


@Entity
@Table(name = "Goal")
@NamedQuery(name="Goal.findAll", query="SELECT g FROM Goal g")
@XmlType(propOrder={"goalId", "goalType", "goalValue", "goalProgresses"})
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_gid")
    @TableGenerator(name="sqlite_gid", pkColumnName="name", valueColumnName="seq",
        pkColumnValue="goal_id",initialValue=1, allocationSize=1)
	@Column(name = "gid")
	private long gid;

	@Column(name = "goalType")
	private String goalType;
	
	@Column(name = "goalValue")
	private Double goalValue;


	@OneToMany(cascade = CascadeType.ALL)
  	@JoinColumn(name="goal_id", referencedColumnName="gid")
  	private List<GoalProgress> progress;

	public Goal() {}

	public long getGoalId() {
		return gid;
	}

	public String getGoalType() {
		return goalType;
	}

	public Double getGoalValue() {
		return goalValue;
	}

	public List<GoalProgress> getGoalProgresses() {
		return progress;
	}

	public void setGoalId(long gid) {
		this.gid = gid;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	public void setGoalValue(Double goalValue) {
		this.goalValue = goalValue;
	}

	public void setGoalProgresses(List<GoalProgress> progress) {
		this.progress = progress;
	}
	
	public static Goal getGoalById(long gid) {
		EntityManager em = UserDao.instance.createEntityManager();
		Goal g = em.find(Goal.class, gid);
		UserDao.instance.closeConnections(em);
		return g;
	}
	
	public static List<Goal> getAll() {
		EntityManager em = UserDao.instance.createEntityManager();
	    List<Goal> list = em.createNamedQuery("Goal.findAll", Goal.class).getResultList();
	    UserDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Goal save(Goal g) {
		EntityManager em = UserDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(g);
		tx.commit();
	    UserDao.instance.closeConnections(em);
	    return g;
	}
	
	public static Goal update(Goal g) {
		EntityManager em = UserDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		g=em.merge(g);
		tx.commit();
	    UserDao.instance.closeConnections(em);
	    return g;
	}
	
	public static void remove(Goal g) {
		EntityManager em = UserDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    g=em.merge(g);
	    em.remove(g);
	    tx.commit();
	    UserDao.instance.closeConnections(em);
	}
}
