package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factories.SessionFactoryBuilder;
@Entity
@Table(name="equipment")
public class Equipment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EquipID")
	private int id;
	
	@Column(name="Name")
	private String Name;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Available")
	private boolean available;
	
	@Column(name="Cost")
	private double cost;
	
	public Equipment()
	{
		
	}
	
	public Equipment(String Name, String category, boolean available, double cost)
	{
		this.Name = Name;
		this.category = category;
		this.available = available;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Equipment ID#: " + id + "\nName: " + Name + "\tCategory: " + category + "\tAvailable:" + available +"\tCost: " + cost;
	}
	
	public void create() 
	{
		Session session = SessionFactoryBuilder
						  .getSessionFactory()
						  .getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(this);
		transaction.commit();
		session.close();
	}
	
	public void update()
	{
		Session session = SessionFactoryBuilder
				  .getSessionFactory()
				  .getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Equipment eq = (Equipment) session.get(Equipment.class, this.id);
		eq.setName(this.Name);
		eq.setCategory(this.category);
		eq.setAvailable(this.available);
		eq.setCost(this.cost);
		session.update(eq);
		transaction.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipment> readAll()
	{
		List<Equipment> EquipmentList = new ArrayList<>();
		Session session = SessionFactoryBuilder
						  .getSessionFactory()
						  .getCurrentSession();
		Transaction transaction = session.beginTransaction();
		EquipmentList = (List<Equipment>) session.createQuery("FROM Student")
														.getResultList();
		transaction.commit();
		session.close();
		return EquipmentList;
	}
	
	public void delete()
	{
		Session session = SessionFactoryBuilder
				  .getSessionFactory()
				  .getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Equipment eq = (Equipment) session.get(Equipment.class, this.id);
		session.delete(eq);
		transaction.commit();
		session.close();
	}
	

	
}
