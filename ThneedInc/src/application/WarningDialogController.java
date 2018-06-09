package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WarningDialogController {
	@FXML
	private Label warningLabel;
	@FXML
	private Button bOkay;
	@FXML
	private AnchorPane warningPane;
	
	//controller for new Order
	private NewCustomerController newCustomerController;
	private NewColorController newColorController;
	private NewOrderController newOrderController;
	private DisplayOrdersController displayOrdersController;
	private EditOrdersController editOrdersController;
	
	//accessor method for controller
	public void setController(NewCustomerController c) {
		newCustomerController = c;
	}
	public void setController(NewColorController c) {
		newColorController = c;
	}
	public void setController(NewOrderController c) {
		newOrderController = c;
	}
	public void setController(DisplayOrdersController c) {
		displayOrdersController = c;
	}
	public void setController(EditOrdersController c) {
		editOrdersController = c;
	}
	
	public void setLabel(String s) {
		warningLabel.setText(s);
	}
	

	// Event Listener on Button[#bOkay].onAction
	@FXML
	public void bOkayClick(ActionEvent event) {
		warningPane.getScene().getWindow().hide();
	}
}
