package application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class MainController {

    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;

    public void Login(ActionEvent event) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        if (LoginController.checkLogin(username, password)) {
            lblStatus.setText("Login Success");
            try {
                // 현재 스테이지(로그인 창)를 가져옵니다.
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                // 새로운 창(메인 메뉴)을 로드합니다.
                Parent root = FXMLLoader.load(getClass().getResource("/application/Ui/Main/MainMenu.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                
                // 메인 메뉴를 위한 새로운 스테이지를 만듭니다.
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                primaryStage.show();
                
                // 현재 스테이지(로그인 창)를 닫습니다.
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblStatus.setText("Login Failed");
        }
    }
}
