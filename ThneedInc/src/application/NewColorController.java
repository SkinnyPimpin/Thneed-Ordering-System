package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class NewColorController {
	@FXML
	private Button bAddColor;
	@FXML
	private Button bCancel;
	@FXML
	private TextField tfNewColor;
	@FXML
	private AnchorPane newColorPane;
	
	private WarningDialogController warningDialogController;
	private NewOrderController newOrderController;
	private Stage warningDialogStage;
	
	//accessor method for controller
	public void setController(NewOrderController c) {
		newOrderController = c;
	}

	// Event Listener on Button[#bAddColor].onAction
	@FXML
	public void bAddColorClick(ActionEvent event) {
		if(tfNewColor.getText().equals("")) {
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
						System.out.println("Hello");
						e.printStackTrace();
					}
				}
				warningDialogController.setLabel("Please a new Color");
				warningDialogStage.show();
		}
		else {
			newOrderController.insertColor(tfNewColor.getText());
			tfNewColor.clear();
			newColorPane.getScene().getWindow().hide();
		}
	}
	// Event Listener on Button[#bCancel].onAction
	@FXML
	public void bCancelClick(ActionEvent event) {
		newColorPane.getScene().getWindow().hide();
	}
}
