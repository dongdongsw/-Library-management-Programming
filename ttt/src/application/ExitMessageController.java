package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExitMessageController {

	@FXML
	private Button OKButton, CancelButton;
	
	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		// 현재 이벤트가 발생한 버튼이 속한 창을 찾아서 닫음
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
  		
	}
	
	@FXML
	private void handleOKButtonAction(ActionEvent event) {
		// 프로그램 종료
		System.exit(0);
  		
	}
	
	@FXML
	private Text messageText;

	public void setMessage(String message) {
	    messageText.setText(message);
	}
}
