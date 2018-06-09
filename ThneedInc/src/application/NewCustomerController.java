package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
//Resources: 
//	https://stackoverflow.com/questions/18361195/javafx-how-to-load-populate-values-at-start-up
//https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewCustomerController implements Initializable{
	@FXML
	private Label lID;
	@FXML
	private TextField tfName;
	@FXML
	private ChoiceBox<String> choiceBoxState;// = new ChoiceBox<String>();
	@FXML
	private TextField tfStreet;
	@FXML
	private TextField tfCity;
	@FXML
	private TextField tfZip;
	@FXML
	private TextField tfPhone1;
	@FXML
	private TextField tfPhone2;
	@FXML
	private TextField tfPhone3;
	@FXML
	private Button bAdd;
	@FXML
	private Button bCancel;
	@FXML
	private AnchorPane newCustomerPane;
	
	ArrayList<Customer> customers = new ArrayList<>();


	//Initialize sets up newcustomer window with populated dropdown
	@FXML
	public void initialize(URL url, ResourceBundle rb) {
		List<String> statesList = new ArrayList<String>();
		//All 50 States
		statesList.add("Alabama");statesList.add("Alaska");statesList.add("Arizona");statesList.add("Arkansas");statesList.add("California");statesList.add("Colorado");statesList.add("Conneticut");statesList.add("Deleware");statesList.add("Florida");statesList.add("Georgia");statesList.add("Hawaii");statesList.add("Idaho");statesList.add("Illinois");statesList.add("Indiana");statesList.add("Iowa");statesList.add("Kansas");statesList.add("Kentucky");statesList.add("Louisiana");statesList.add("Maine");statesList.add("Maryland");statesList.add("Massachusetts");statesList.add("Micigan");statesList.add("Minnesota");statesList.add("Mississippi");statesList.add("Missouri");statesList.add("Montana");statesList.add("Nebraska");statesList.add("Nevada");statesList.add("New Hampshire");statesList.add("New Jersey");statesList.add("New Mexico");statesList.add("New York");statesList.add("North Carolina");statesList.add("North Dakota");statesList.add("Ohio");statesList.add("Oklahoma");statesList.add("Oregon");statesList.add("Pennsylvania");statesList.add("Rhode Island");statesList.add("South Carolina");statesList.add("Tennessee");statesList.add("Texas");statesList.add("Utah");statesList.add("Vermont");statesList.add("Virginia");statesList.add("Washington");statesList.add("West Virginia");statesList.add("Wisconsin");statesList.add("Wyoming");
		ObservableList obList = FXCollections.observableList(statesList);
		choiceBoxState.getItems().clear();
		choiceBoxState.setItems(obList);
		choiceBoxState.getSelectionModel().select("Indiana");
		
		try {
	         FileInputStream fileIn = new FileInputStream("./customerData.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         customers = (ArrayList<Customer>) in.readObject();
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
	}
	
	
	//controller for new Order
	private NewOrderController newOrderController;
	
	//accessor method for controller
	public void setController(NewOrderController c) {
		newOrderController = c;
	}
	
	private Stage warningDialogStage;
	private WarningDialogController warningDialogController;
	
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
	
	public void clearFields() {
		tfName.clear();
		tfStreet.clear();
		tfCity.clear();
		tfZip.clear();
		tfPhone1.clear();
		tfPhone2.clear();
		tfPhone3.clear();
		choiceBoxState.getSelectionModel().select("Indiana");
	}
	
	// Event Listener on Button[#bAdd].onAction
	@FXML
	public void bAddClick(ActionEvent event) {
		//If they dont provide valid information, show warning dialog message
		if (tfName.getText().equals("") || tfStreet.getText().equals("") || tfCity.getText().equals("") || tfZip.getText().equals("") || tfPhone1.getText().equals("") || tfPhone2.getText().equals("") || tfPhone3.getText().equals("")) 
			launchWarningDialog("Please Enter infomation in all fields.");
		//Check for invalid phone num length
		else if (tfPhone1.getText().length() != 3 || tfPhone2.getText().length() != 3 || tfPhone3.getText().length() != 4) 
			launchWarningDialog("Invalid Phone Number Length");
		// check for invalid phone num characters
		else if (!tfPhone1.getText().matches("\\d*") || !tfPhone2.getText().matches("\\d*") || !tfPhone3.getText().matches("\\d*"))
			launchWarningDialog("Please convert phone number to digits");
		//check for invalid zipcode
		else if (!tfZip.getText().matches("\\d*"))
			launchWarningDialog("Zip codes are comprised of digits");
		//Otherwise add them to customer list 
		else {
			int id = Integer.parseInt(lID.getText());
			String name = tfName.getText();
			String street = tfStreet.getText();
			String city = tfCity.getText();
			String state = choiceBoxState.getSelectionModel().getSelectedItem(); //get selected state
			String state2= ""; //empty holder for Abbreviation
			//Obtain selected state's Abbreviation
			if (state.equals("Arizona")){state2 = "AZ";}else if(state.equals("Alaska")) {state2="AK";}else if(state.equals("Arizona")) {state2="AZ";}else if(state.equals("Arkansas")) {state2="AR";}else if(state.equals("California")) {state2="CA";}else if(state.equals("Colorado")) {state2="CO";}else if(state.equals("Conneticut")) {state2="CT";}else if(state.equals("Deleware")) {state2="DE";}else if(state.equals("Florida")) {state2="FL";}else if(state.equals("Georgia")) {state2="GA";}else if(state.equals("Hawaii")) {state2="HI";}else if(state.equals("Idaho")) {state2="ID";}else if(state.equals("Illinois")) {state2="IL";}else if(state.equals("Indiana")) {state2="IN";}else if(state.equals("Iowa")) {state2="IA";}else if(state.equals("Kansas")) {state2="KS";}else if(state.equals("Kentucky")) {state2="KY";}else if(state.equals("Louisiana")) {state2="LA";}else if(state.equals("MaryLand")) {state2="MD";}else if(state.equals("Massachusetts")) {state2="MA";}else if(state.equals("Michigan")) {state2="MI";}else if(state.equals("Minnesota")) {state2="MN";}else if(state.equals("Mississippi")) {state2="MS";}else if(state.equals("Missouri")) {state2="MO";}else if(state.equals("Montana")) {state2="MT";}else if(state.equals("Nebraska")) {state2="NE";}else if(state.equals("New Mexico")) {state2="NM";}else if(state.equals("New York")) {state2="NY";}else if(state.equals("North Carolina")) {state2="NC";}else if(state.equals("North Dakota")) {state2="ND";}else if(state.equals("Ohio")) {state2="OH";}else if(state.equals("Oklahoma")) {state2="OK";}else if(state.equals("Oregon")) {state2="OR";}else if(state.equals("Pennsylvania")) {state2="PA";}else if(state.equals("Rhode Island")) {state2="RI";}else if(state.equals("South Carolina")) {state2="SC";}else if(state.equals("Tennessee")) {state2="TN";}else if(state.equals("Texas")) {state2="TX";}else if(state.equals("Utah")) {state2="UT";}else if(state.equals("Vermont")) {state2="VT";}else if(state.equals("Washington")) {state2="WA";}else if(state.equals("West Virginia")) {state2="WV";}else if(state.equals("Wisconsin")) {state2="WI";}else if(state.equals("Wyoming")) {state2="WY";}
			String zip = tfZip.getText();
			String phone = "("+ tfPhone1.getText() +")-"+ tfPhone2.getText() +"-"+ tfPhone3.getText();
			String address = street + "\n" + city +" "+ state2 +", "+ zip ;
			Customer newCust = new Customer(id, name, address, phone, null);
			customers.add(newCust);
			//Add customer to ListView<Customer> lvCustomers on NewOrderController.java
			newOrderController.addCustomer(newCust);
			//Add 1 to customerID field for next new customer
			//lID.setText(String.valueOf(Integer.parseInt(lID.getText())+1));
			clearFields(); //clear fields method call
			newCustomerPane.getScene().getWindow().hide(); //hide window
			
			try { //write customer data to a file
		        FileOutputStream fileOut =
		        new FileOutputStream("./customerData.ser"); //save the data in src/customerData.ser
		        ObjectOutputStream out = new ObjectOutputStream(fileOut);
		        out.writeObject(customers);
		        out.close();
		        fileOut.close();
		        System.out.printf("Serialized data is saved in ./customerData.ser\n");
		     } 
			catch (IOException i) {
		        i.printStackTrace();
		     }
		}
	}
	// Event Listener on Button[#bCancel].onAction
	@FXML
	public void bCancelClick(ActionEvent event) {
		clearFields();
		newCustomerPane.getScene().getWindow().hide();
	}
	
	public ArrayList<Customer> getCustomers(){
		return customers;
	}
	
	public void setID(int id) {
		lID.setText(String.valueOf(id));
	}
	
}
