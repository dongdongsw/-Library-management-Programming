package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeleteMemberController {
	
	 @FXML
	 private Button Delete, Cancel;

	 private boolean confirmed = false;

	 @FXML
	 private void handleConfirmDeleteButtonAction() {	        
		 confirmed = true;	        
		 closeWindow();	    
	 }

	    
	 @FXML	    
	 private void handleCancelButtonAction() {	        
		 confirmed = false;	        
		 closeWindow();	    
	 }

	    
	 public boolean isConfirmed() {	        
		 return confirmed;	    
	 }

	    
	 private void closeWindow() {	        
		 Stage stage = (Stage) Delete.getScene().getWindow();	        
		 stage.close();	    
	 }

	    
	 public void setMember(Member member) {	        
		 // Set member details if needed
	    
	 }
  

}
