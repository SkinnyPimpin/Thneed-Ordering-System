package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.ListView;

public class Order implements Serializable{
	private int orderNum = 1;
	private ArrayList<Thneed> thneeds = new ArrayList();
	private Customer customer = new Customer();
	private java.util.Date dateOrdered = new java.util.Date();
	private java.util.Date dateFilled = null;
	
	public Order(){
		dateOrdered = new java.util.Date();
	}
	
	public Order(int orderNum, ArrayList<Thneed> thneeds ,java.util.Date dateOrdered, java.util.Date dateFilled, Customer customer) {
		this.orderNum = orderNum;
		this.thneeds = thneeds;
		this.dateOrdered = dateOrdered;
		this.dateFilled = dateFilled;
		this.customer = customer;
		this.orderNum = orderNum+1;
	}
	
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public ArrayList<Thneed> getThneeds() {
		return thneeds;
	}

	public void setThneeds(ArrayList<Thneed> thneeds) {
		this.thneeds = thneeds;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public java.util.Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(java.util.Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public java.util.Date getDateFilled() {
		return dateFilled;
	}

	public void setDateFilled(java.util.Date dateFilled) {
		this.dateFilled = dateFilled;
	}
	
	public String toString() {
		return "Order: " + orderNum + "\nThneeds: " + thneeds + "\ndateOrdered: " + dateOrdered + "\ndateFilled: " + dateFilled + "\n";
	}

}