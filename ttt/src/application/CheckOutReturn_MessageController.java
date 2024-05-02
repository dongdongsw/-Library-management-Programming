package application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;



public class CheckOutReturn_MessageController {
	@FXML
	private Button OKButton;
	@FXML
	private void handleOKButtonAction(ActionEvent event) {
		// 현재 이벤트가 발생한 버튼이 속한 창을 찾아서 닫음
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
  		
	}
	
	@FXML
	private Text messageText;

	public void setMessage(String message) {
	    messageText.setText(message);
	}

}
