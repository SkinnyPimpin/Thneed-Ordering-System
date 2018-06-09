package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ViewCustomerController {
	@FXML
	private Button bClose;
	@FXML
	private Label lId;
	@FXML
	private Label lName;
	@FXML
	private Label lAddress;
	@FXML
	private Label lphone;
	@FXML
	private AnchorPane viewCustomerPane;
	@FXML
	private ListView<Thneed> lvThneedsOrdered;
	
	ArrayList<Thneed> thneeds = new ArrayList<Thneed>();
	
	//controller for window1 (so we can set its label)
	private DisplayOrdersController displayOrdersController;
	
	//accessor method for controller
	public void setController(DisplayOrdersController c) {
		displayOrdersController = c;
	}

	// Event Listener on Button[#bClose].onAction
	@FXML
	public void bCloseClick(ActionEvent event) {
		lvThneedsOrdered.getItems().clear();
		viewCustomerPane.getScene().getWindow().hide();
	}

	public void setlId(String newLId) {
		lId.setText(newLId);
	}
	public void setlName(String newLName) {
		lName.setText(newLName);
	}
	public void setAddress(String newLAddress) {
		lAddress.setText(newLAddress);
	}
	public void setLphone(String newLPhone) {
		lphone.setText(newLPhone);
	}	
	
	public void addTnneed(Thneed thneed) {
		//System.out.println(thneed);
		lvThneedsOrdered.getItems().add(thneed);
	}
}



