package application;

import java.io.Serializable;

public class Thneed implements Serializable{
	private int quantity = 0;
	private String size = "Small";
	private String color = "default";
	
	public Thneed() {
		//no args constructor
	}
	
	public Thneed(int quantity, String size, String color) {
		this.quantity = quantity;
		this.size = size;
		this.color = color;
	}
	
	//GETTERS
	public int getQuantity(){
		return quantity;
	}
	public String getSize() {
		return size;
	}
	public String getColor() {
		return color;
	}
	
	//SETTERS
	public void setQuantity(int newQuantity) {
		if (newQuantity > 0) 
			quantity = newQuantity;
		else
			System.out.println("You gotta order at least 1 Thneed");
	}
	public void setSize(String newSize) {
		if(!newSize.equals("")) 
			size = newSize;
		else
			System.out.println("You need to select a size");
	}
	public void setColor(String newColor) {
		if(!newColor.equals(""))
			color = newColor;
		else 
			System.out.println("You should select a color");
	}
	
	
	public String toString() {
		return ("| Quantity: " + quantity + " || Size: " + size + " || Color: " + color+" |");
	}
}
