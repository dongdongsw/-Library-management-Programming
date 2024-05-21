package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteBookController {

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

	    
	 public void setBook(Book book) {	        
		 // Set Book details if needed
	    
	 }
}
