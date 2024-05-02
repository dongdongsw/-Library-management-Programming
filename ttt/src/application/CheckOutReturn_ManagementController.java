package application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CheckOutReturn_ManagementController {
	@FXML
	private Button Home, LogOut, Exit;
	
	@FXML
    private TableView<Member> MemberTableView;
	
    @FXML
    private TableColumn<Member, String> memberNoColumn;
    @FXML
    private TableColumn<Member, String> memberIdColumn;
    @FXML
    private TableColumn<Member, String> memberNameColumn;
    @FXML
    private TableColumn<Member, String> eligibleColumn;
    @FXML
    private TableColumn<Member, Integer> checkOutLimitColumn;
    @FXML
    private TableColumn<Member, String> birthdayColumn;
    @FXML
    private TableColumn<Member, String> phoneNumberColumn;
    @FXML
    private TableColumn<Member, Boolean> checkBoxColumn;
    @FXML
    private CheckBox membercheckbox;
    
    @FXML //메시지를 보이게 만드는 기능 제공
    private Text messageText;

    public void setMessage(String message) {
        messageText.setText(message);
    }
    
    private MemberList MemberList = new MemberList(); // MemberList 인스턴스 생성
    
    @FXML
    private void initialize() {
    	memberNoColumn.setCellValueFactory(new PropertyValueFactory<>("Member_No"));
    	memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("Member_Id"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("Member_Name"));
        eligibleColumn.setCellValueFactory(new PropertyValueFactory<>("CheckOutEligible"));
        checkOutLimitColumn.setCellValueFactory(new PropertyValueFactory<>("CheckOutLimit"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
    	
        // MemberList의 데이터를 TableView에 설정
        MemberTableView.setItems(MemberList.getMembers());
        
        
     // 체크박스 칼럼 설정
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
        MemberTableView.setEditable(true);

        // 체크박스 선택 시 다른 체크박스 선택 해제 (옵션)
        MemberTableView.getItems().forEach(member -> 
            member.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    MemberTableView.getItems().stream()
                        .filter(otherMember -> otherMember != member)
                        .forEach(otherMember -> otherMember.setSelected(false));
                }
            })
        );
    }
   
    
    
    /* 체크박스 선택관련된 함수(예비용)
    private void addSelectedPropertyListener(Member member) {
        member.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                MemberTableView.getItems().stream()
                    .filter(otherMember -> otherMember != member)
                    .forEach(otherMember -> otherMember.setSelected(false));
            }
        });
    }
    
    */
 
    
 // 대출 버튼 이벤트 핸들러 예시
    public void handleCheckOutButtonAction(ActionEvent event) {
        for (Member member : MemberList.getMembers()) {
            if (member.isSelected()) {
                try {
                    // 메시지 창 로드
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                    Parent root = loader.load();
                    CheckOutReturn_MessageController controller = loader.getController();

                    // 상황에 따른 메시지 설정
                    if ("X".equals(member.getCheckOutEligible())) {
                        controller.setMessage("대출이 불가능한 상태입니다.");
                        showNewStage(root);
                        return;
                    }

                    if (member.getCheckOutLimit() == 0) {
                        controller.setMessage("대출 가능한 도서의 수가 없습니다.");
                        showNewStage(root);
                        return;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 새 창에서 메시지 보여주는 메소드
    private void showNewStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
 
	@FXML 
	private void handleHomeButtonAction(ActionEvent event) {
		//홈 버튼을 눌렀을 때 대출&반납 메뉴가 꺼짐과 동시에 메인화면으로 전환
		try {
	
			Parent Root = FXMLLoader.load(getClass().getResource("/application/Ui/Main/MainMenu.fxml"));
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
	private void handleLogOutButtonAction(ActionEvent event) {
		//로그아웃 버튼을 눌렀을 때 대출&반납 메뉴가 꺼짐과 동시에 로그인화면으로 전환
		try {
	
			Parent Root = FXMLLoader.load(getClass().getResource("/application/Ui/Main/Login.fxml"));
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
		//종료 버튼을 눌렀을 때 해당 프로그램이 종료 된다.
		System.exit(0);
  		
	}
	
}
