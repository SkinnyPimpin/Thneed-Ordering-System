package application;

import java.io.Serializable;
import java.util.ArrayList; 

public class Customer implements Serializable {
	private int id;
	private String name;
	private String address;
	private String phone;
	private ArrayList<Order> orders = new ArrayList<>(); //array list to store order information based on user ID
	
	//no-arg constructor
	public Customer() {
		
	}
	
	//constructor
	public Customer(int id, String name, String address, String phone, ArrayList<Order> orders) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.orders = orders;
	}
	
	//id getter
	int getID() {
		return id;
	}
	
	//id setter
	void setID(int newID) {
		this.id = newID;
	}
	
	//name setter
	String getName() {
		return name;
	}
	
	//name getter
	void setName(String newName) {
		this.name = newName;
	}
	
	//address getter
	String getAddress() {
		return address;
	}
	
	//address setter
	void setAddress(String newAddress) {
		this.address = newAddress;
	}
	
	//phone getter
	String getPhone() {
		return phone;
	}
	
	
	//phone setter
	void setPhone(String newPhone) {
		this.phone = newPhone;
	}
	
	//Customers getter
	ArrayList<Order> getOrders() {
		return orders;
	}
	
	
	public String toString() {
		return(name + ",\n" + address );
	}

	
}
