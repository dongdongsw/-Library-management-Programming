package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML
	private Button BookListInfo, MembeListInfo, CheckOutReturn, Exit;
	
	@FXML
	private void handleBookListButtonAction(ActionEvent event) {
		//도서 관리 버튼을 눌렀을 때 메인메뉴가 꺼짐과 동시에 도서 목록 화면으로 전환
		try {
	
			Parent Root = FXMLLoader.load(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_BookListInfo.fxml"));
            Scene scene = new Scene(Root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            
         // 현재 스테이지 가져오기 (현재 이벤트가 발생한 Window를 기반으로)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 씬 설정하고 보이기
            currentStage.setScene(scene);
            currentStage.show();
        } 
		catch (IOException e) {
        	e.printStackTrace();
        }
  		
	}
	
	@FXML
	private void handleMemberListButtonAction(ActionEvent event) {
		//회원 관리 버튼을 눌렀을 때 메인메뉴가 꺼짐과 동시에 회원 목록 화면으로 전환
		try {
	
			Parent Root = FXMLLoader.load(getClass().getResource("/application/Ui/BookUserManagement/Book_User_Search_Management.fxml"));
            Scene scene = new Scene(Root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            
         // 현재 스테이지 가져오기 (현재 이벤트가 발생한 Window를 기반으로)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 씬 설정하고 보이기
            currentStage.setScene(scene);
            currentStage.show();
        } 
		catch (IOException e) {
        	e.printStackTrace();
        }
  		
	}
	@FXML
	private void handleCheckOutReturnButtonAction(ActionEvent event) {
		//대출&반납 버튼을 눌렀을 때 메인메뉴가 꺼짐과 동시에 대출&반납화면으로 전환
		try {
	
			Parent Root = FXMLLoader.load(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_Management.fxml"));
            Scene scene = new Scene(Root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            
         // 현재 스테이지 가져오기 (현재 이벤트가 발생한 Window를 기반으로)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 씬 설정하고 보이기
            currentStage.setScene(scene);
            currentStage.show();
        } 
		catch (IOException e) {
        	e.printStackTrace();
        }
  		
	}
	

	
	@FXML
	private void handleExitButtonAction(ActionEvent event) {
		try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/Main/ExitMessage.fxml"));
    		Parent root = loader.load();
    		ExitMessageController controller = loader.getController();

    		showNewStage(root);
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
  		
	}
	
	// 새 창에서 메시지 보여주는 메소드
    private void showNewStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
