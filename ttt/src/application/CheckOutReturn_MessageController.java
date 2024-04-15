package application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;


public class CheckOutReturn_MessageController {
	@FXML
	private Button OKButton;
	@FXML
	private void handleOKButtonAction(ActionEvent event) {
		System.exit(0);
  		
	}

}
