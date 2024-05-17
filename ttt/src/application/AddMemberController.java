package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddMemberController {
	@FXML
	private Button AddMember, Cancel;
	@FXML
	private TextField Member_No, Member_Id, Member_Name, Birthday, PhoneNumber;
	@FXML
	private ComboBox<String> CheckOutEligible;
	@FXML
    private Text messageText;  // 메시지를 표시할 Text 컨트롤 추가
	
	private MemberList memberList;
	private MemberManagementController memberManagementController;
	
	public AddMemberController() {
        memberList = MemberList.getInstance();  // Singleton 인스턴스 가져오기
    }
	
	public void setMemberManagementController(MemberManagementController controller) {
		this.memberManagementController = controller;
	    }
	
	
	@FXML
    public void initialize() {
        CheckOutEligible.setItems(FXCollections.observableArrayList("O", "X"));
    }
	
	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		// 현재 이벤트가 발생한 버튼이 속한 창을 찾아서 닫음
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
  		
	}
	
	@FXML
	private void handleAddButton(ActionEvent event) {
		String No = Member_No.getText();
		String Id = Member_Id.getText();
		String Name = Member_Name.getText();
		String birthday = Birthday.getText();
		String phoneNumber = PhoneNumber.getText();
		String checkOutChoice = CheckOutEligible.getValue();
		
		// ComboBox 값이  null 일 때(nothing selected)
	    if (checkOutChoice == null) {
	        showMessage("도서대출 가능 여부를 선택하세요.");
	        return;
	    }
		// CheckOutLimit 설정
        int checkOutLimit = "O".equals(checkOutChoice) ? 3 : 0;

		Member newMember = new Member(No, Id, Name, checkOutChoice, checkOutLimit, birthday, phoneNumber);
		// 회원 추가 함수 호출
        addMember(newMember);
		
	}
		
	public void addMember(Member member) {
		// 회원을 추가할 때 회원번호 또는 아이디가 중복될 경우에 대한 함수
        for (Member m : memberList.getMembers()) {
            if (m.getMember_No().equals(member.getMember_No())) {
                showMessage("회원번호가 이미 있습니다.");
                return; // 중복되면 추가하지 않고 함수 종료
            }
            if (m.getMember_Id().equals(member.getMember_Id())) {
                showMessage("이미 존재하는 아이디입니다.");
                return; // 중복되면 추가하지 않고 함수 종료
            }
        }

        memberList.add(member);
        showMessage("회원이 성공적으로 추가되었습니다.");
        
    	// 회원 추가 후 테이블 새로고침
        memberManagementController.refreshTable();

        // 창 닫기
        Stage stage = (Stage) AddMember.getScene().getWindow();
        stage.close();
		
    }
	// 새 창에서 메시지 보여주는 메소드
    private void showMessage(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
            Parent root = loader.load();
            CheckOutReturn_MessageController controller = loader.getController();
            controller.setMessage(message);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 // 회원 목록 화면으로 돌아가는 메소드
    private void goToMemberManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/BookUserManagement/Book_User_Search_Management.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AddMember.getScene().getWindow(); // 현재 창 가져오기
            stage.setScene(new Scene(root));
            stage.show(); // 새로운 씬 설정하고 보이기
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
