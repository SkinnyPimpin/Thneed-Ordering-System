package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditOrdersController {
	@FXML
	private Button bCancel;
	@FXML
	private Button bSave;
	@FXML
	private DatePicker dpFilled;
	@FXML
	private AnchorPane editOrdersPane;
	@FXML
	private Label lSelectedOrder;
	
	private Order order;
	
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
	
	//controller for window1 (so we can set its label)
	private DisplayOrdersController displayOrdersController;
	
	//accessor method for controller
	public void setController(DisplayOrdersController c) {
		displayOrdersController = c;
	}

	//order label setter
	public void setOrderLabel(Order order) {
		this.order = order;
		lSelectedOrder.setText(String.valueOf(order));
	}
	// Event Listener on Button[#bCancel].onAction
	@FXML
	public void bCancelClick(ActionEvent event) {
		lSelectedOrder.setText("");
		dpFilled.getEditor().clear(); //https://stackoverflow.com/questions/25962676/how-to-clear-a-datepicker
		dpFilled.setValue(null); 
		editOrdersPane.getScene().getWindow().hide();
	}
	// Event Listener on Button[#bSave].onAction
	@FXML
	public void bSaveClick(ActionEvent event) {
		LocalDate dateFilled = dpFilled.getValue();    //https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
		java.util.Date date = java.sql.Date.valueOf(dateFilled);
		if (date.compareTo(order.getDateOrdered()) > 0) { //https://www.mkyong.com/java/how-to-compare-dates-in-java/
			order.setDateFilled(date);
			//System.out.println(order);
			displayOrdersController.removeOrder(order); //Removes from unfilled list
			displayOrdersController.addOrder(order);    // adds to filled list
			lSelectedOrder.setText("");    //reset selected order label
			editOrdersPane.getScene().getWindow().hide();
		}
		else {
			launchWarningDialog("Unable to fill order before order existed");
		}
	}
}
