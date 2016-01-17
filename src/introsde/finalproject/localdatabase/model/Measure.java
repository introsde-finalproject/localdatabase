package introsde.finalproject.localdatabase.model;

import introsde.finalproject.localdatabase.dao.UserDao;
import introsde.finalproject.localdatabase.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import javax.xml.bind.annotation.*;


@Entity
@Table(name = "Measure")
@NamedQuery(name="Measure.findAll", query="SELECT m FROM Measure m")
@XmlType(propOrder={"measureId", "dateRegistered", "measureType", "measureValue"})
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mid")
    @TableGenerator(name="sqlite_mid", pkColumnName="name", valueColumnName="seq",
        pkColumnValue="measure_id",initialValue=1, allocationSize=1)
	@Column(name = "mid")
	private long mid;

	@Temporal(TemporalType.DATE)
	@Column(name = "dateRegistered")
	private Date dateRegistered;

	@Column(name = "measureType")
	private String measureType;
	
	@Column(name = "measureValue")
	private String measureValue;


	public Measure() {}

	@XmlElement(name="mid")
	public long getMeasureId() {
		return mid;
	}

	public String getDateRegistered() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return (dateRegistered!=null) ? sdf.format(dateRegistered) : "";
	}

	public String getMeasureType() {
		return measureType;
	}

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureId(long mid) {
		this.mid = mid;
	}

	public void setDateRegistered(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.dateRegistered = sdf.parse(date);
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	
	public static Measure getMeasureById(long mid) {
		EntityManager em = UserDao.instance.createEntityManager();
		Measure m = em.find(Measure.class, mid);
		UserDao.instance.closeConnections(em);
		return m;
	}
	
	public static List<Measure> getAll() {
		EntityManager em = UserDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    UserDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Measure save(Measure m) {
		EntityManager em = UserDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(m);
		tx.commit();
	    UserDao.instance.closeConnections(em);
	    return m;
	}
	
	public static Measure update(Measure m) {
		EntityManager em = UserDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		m=em.merge(m);
		tx.commit();
	    UserDao.instance.closeConnections(em);
	    return m;
	}
	
	public static void remove(Measure m) {
		EntityManager em = UserDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    m=em.merge(m);
	    em.remove(m);
	    tx.commit();
	    UserDao.instance.closeConnections(em);
	}
}
