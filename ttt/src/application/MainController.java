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
            	Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/application/Ui/Main/MainMenu.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        } else {
            lblStatus.setText("Login Failed");
        }
    }
}

			
	