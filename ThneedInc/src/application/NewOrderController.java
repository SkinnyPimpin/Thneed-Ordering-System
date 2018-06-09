package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

public class NewOrderController implements Initializable{
	@FXML
	private AnchorPane newOrderPane;
	@FXML
	private Button bNewCustomer;
	@FXML
	private ListView<Customer> lvCustomers;
	@FXML
	private Button bPlaceOrder;
	@FXML
	private Button bCancel;
	@FXML
	private Button bAddOrder;
	@FXML
	private TextField tfQuantity;
	@FXML
	private ListView<Thneed> lvThneeds;
	@FXML
	private RadioButton rbSmall;
	@FXML
	private ToggleGroup tgSize;
	@FXML
	private RadioButton rbMedium;
	@FXML
	private RadioButton rbLarge;
	@FXML
	private Button bReset;
	@FXML
	private Label customerLabel;
	@FXML
	private ChoiceBox<String> choiceBoxColor;
	@FXML
	private Button bNewColor;
	@FXML
	private Label custThneedsLabel;
	
	int numOrders = 0;
	int numCustomers = 0;
	ArrayList<Customer> customers = new ArrayList<>();
	
	
	//Initialize sets up newcustomer window with populated dropdown
	@FXML
	public void initialize(URL url, ResourceBundle rb) {
		List<String> colorsList = new ArrayList<String>();
		colorsList.add("Amber");colorsList.add("Beige");colorsList.add("Brown");colorsList.add("Blue");colorsList.add("Green");colorsList.add("Golden");colorsList.add("Indigo");colorsList.add("Maroon");colorsList.add("Orange");colorsList.add("Purple");colorsList.add("Purple Mountian Majesty");colorsList.add("Red");colorsList.add("Ruby Red");colorsList.add("Silver");colorsList.add("Teal");colorsList.add("JackPot Green");colorsList.add("Violet");colorsList.add("Yellow");colorsList.add("Zebra Stripped");colorsList.add("Candy Stripped");
		ObservableList obList = FXCollections.observableList(colorsList);
		choiceBoxColor.getItems().clear();
		choiceBoxColor.setItems(obList);
		choiceBoxColor.getSelectionModel().select("Amber");
		
		try {
	         FileInputStream fileIn = new FileInputStream("./customerData.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         customers = (ArrayList<Customer>) in.readObject();
	         //System.out.println(customers);
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Customer class not found");
	         c.printStackTrace();
	         return;
	      }
		finally {
			for(Customer c: customers) {
				lvCustomers.getItems().add(c);
				numCustomers += 1;
			}
		
		}
	}
	

	public void insertColor(String color) {
		List<String> colorsList = new ArrayList<String>();
		colorsList.add("Amber");colorsList.add("Beige");colorsList.add("Golden");colorsList.add("Brown");colorsList.add("Blue");colorsList.add("Green");colorsList.add("Indigo");colorsList.add("Maroon");colorsList.add("Orange");colorsList.add("Purple");colorsList.add("Purple Mountian Majesty");colorsList.add("Red");colorsList.add("Ruby Red");colorsList.add("Silver");colorsList.add("Teal");colorsList.add("JackPot Green");colorsList.add("Violet");colorsList.add("Yellow");colorsList.add("Zebra Stripped");colorsList.add("Candy Stripped");
		//add custom color
		colorsList.add(color);
		ObservableList obList = FXCollections.observableList(colorsList);
		choiceBoxColor.getItems().clear();
		choiceBoxColor.setItems(obList);
		choiceBoxColor.getSelectionModel().select(color);
	}
	
	public void resetFields() {
		choiceBoxColor.getSelectionModel().select("Candy Stripped");
		tfQuantity.setText("1");
		tgSize.selectToggle(rbMedium);
		customerLabel.setText("\n\nPlease Select/Create a Customer");
		custThneedsLabel.setText("Thneeds: ");
		lvCustomers.getSelectionModel().clearSelection();
	}
	
	/////////////////////////////////////////////////////////
	private DisplayOrdersController displayOrdersController;
	
	private NewOrderController newOrderController;
	
	public void setController(DisplayOrdersController c) {
		displayOrdersController = c;
	}

	private Stage displayOrdersStage;
	
	String size = "";
	String errorMessage = "";
	Customer customer4Order;
	//https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
	Date today = new Date();
	LocalDate dateOrdered = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	java.util.Date orderedToday = java.sql.Date.valueOf(dateOrdered);

	@FXML
	private ArrayList<Thneed> alThneeds = new ArrayList<Thneed>();
	
	//NewCustomer Stage and controller, start with null
	private Stage newCustomerStage;
	private NewCustomerController newCustomerController;
	
	//NewColor Stage and Controller, start with null
	private Stage newColorStage;
	private NewColorController newColorController;
	
	private Stage warningDialogStage;
	private WarningDialogController warningDialogController;
	
	void addCustomer(Customer c) {
		lvCustomers.getItems().add(c);
	}
	void addThneed(Thneed t) {
		lvThneeds.getItems().add(t);
		alThneeds.add(t);
	}
		
	public void launchWarningDialog(String message) {
		if (warningDialogStage == null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/WarningDialog.fxml"));
			AnchorPane warningDialogPane;
				try {
					warningDialogPane = (AnchorPane)loader.load();
					Scene warningDialogScene = new Scene(warningDialogPane);
					warningDialogStage = new Stage();
					warningDialogStage.setScene(warningDialogScene);
					warningDialogStage.setTitle("Warning:");
					warningDialogController  = (WarningDialogController) loader.getController();
					warningDialogController.setController(this);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			warningDialogController.setLabel(message);
			warningDialogStage.show();	
	}
	
	////////////////////////////////////////////////////////////
	// Event Listener on Button[#bNewCustomer].onAction
	@FXML
	public void bNewCustomerClick(ActionEvent event) {
		//if first time in the buttonClick, load window 2
		if (newCustomerStage == null) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewCustomer.fxml"));
		AnchorPane newCustomerPane;
			try {
				newCustomerPane = (AnchorPane)loader.load();
				Scene newCustomerScene = new Scene(newCustomerPane);
				newCustomerStage = new Stage();
				newCustomerStage.setScene(newCustomerScene);
				newCustomerStage.setTitle("Enter New Customer");
				newCustomerController = (NewCustomerController) loader.getController();
				newCustomerController.setController(this);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
//		int numCustomers = alCustomers.size();
//		System.out.println(numCustomers);
		newCustomerController.setID(numCustomers + 1);
		newCustomerStage.show();
	}
	// Event Listener on Button[#bPlaceOrder].onAction
	@FXML
	public void bPlaceOrderClick(ActionEvent event) {
		if (customerLabel.getText().equals("") || lvCustomers.getSelectionModel().getSelectedItem() == null) {
			errorMessage = "Please select a customer Label ";
			launchWarningDialog(errorMessage);
		}
		else if (alThneeds.isEmpty()) {
			errorMessage = "You thneed to order at least one thneed";
			launchWarningDialog(errorMessage);
		}
		else {
			numOrders = displayOrdersController.getNumOrders();
			Order newOrder = new Order(numOrders, alThneeds, orderedToday, null, customer4Order );
			numOrders = displayOrdersController.setNumOrders(numOrders + 1);
			displayOrdersController.addOrder(newOrder);
			resetFields();
			lvThneeds.getItems().clear();
			alThneeds = new ArrayList<Thneed>();
			newOrderPane.getScene().getWindow().hide();
		}
	}
	// Event Listener on Button[#bCancel].onAction
	@FXML
	public void bCancelClick(ActionEvent event) {
		//when dey hit cancel, hide current window (no need to make new DisplayOrdersWindow)
		resetFields();
		lvThneeds.getItems().clear();
		alThneeds = new ArrayList<Thneed>();
		newOrderPane.getScene().getWindow().hide();

	}
	// Event Listener on Button[#bAddOrder].onAction
	@FXML
	public void bAddOrderClick(ActionEvent event) {
		//prepare input for creating thneed to add to listView
		if (!tfQuantity.getText().matches("\\d*") || tfQuantity.getText().equals("")) {
			launchWarningDialog("Please enter a valid quantity");
		}
		else if (tfQuantity.getText().equals("0")) {
			launchWarningDialog("You thneed to order atleast 1 Thneed.");
		}
		else {
			int quantity = Integer.parseInt(tfQuantity.getText());
			RadioButton size = (RadioButton)tgSize.getSelectedToggle();
			String stringSize = size.getText();
			String color = choiceBoxColor.getSelectionModel().getSelectedItem();
			Thneed newThneed = new Thneed(quantity, stringSize, color);
			addThneed(newThneed);
		}
	}
	//Mouse event listener on customers List view
	@FXML
	public void lvCustomersOnMouseClick(MouseEvent event) {
		//Dont allow error when null is selected
		if(lvCustomers.getSelectionModel().getSelectedItem() == null) {     //"Please Create a New Customer"
			launchWarningDialog("Please create a new customer");
		}
		else {
			customer4Order = lvCustomers.getSelectionModel().getSelectedItem();
			customerLabel.setText(lvCustomers.getSelectionModel().getSelectedItem().getName());
			custThneedsLabel.setText(lvCustomers.getSelectionModel().getSelectedItem().getName()+ "'s Thneeds:");
		}
	}
	// Event Listener on RadioButton[#rbSmall].onAction
	@FXML
	public void rbSmallClick(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on RadioButton[#rbMedium].onAction
	@FXML
	public void rbMediumClick(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on RadioButton[#rbLarge].onAction
	@FXML
	public void rbLargeClick(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#bReset].onAction
	@FXML
	public void bResetClick(ActionEvent event) {
		resetFields();
		lvThneeds.getItems().clear();
		alThneeds = new ArrayList<Thneed>();
	}
	// Event Listener on Button[#bNewColor].onAction
	@FXML
	public void bNewColorClick(ActionEvent event) {
			if (newColorStage == null) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewColor.fxml"));
				AnchorPane newColorPane;
					try {
						newColorPane = (AnchorPane)loader.load();
						Scene newColorScene = new Scene(newColorPane);
						newColorStage = new Stage();
						newColorStage.setScene(newColorScene);
						newColorStage.setTitle("Enter New Color:");
						newColorController  = (NewColorController) loader.getController();
						newColorController.setController(this);
					}catch(IOException e) {
						//System.out.println("Hello");
						e.printStackTrace();
					}
				}
				newColorStage.show();
	}
}
