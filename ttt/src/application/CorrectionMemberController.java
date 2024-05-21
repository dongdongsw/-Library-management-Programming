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

public class CorrectionMemberController {
	
	@FXML
	private Button CorrectionMember, Cancel;
	@FXML
	private TextField Member_Id, Member_Name, PhoneNumber;
	@FXML
	private Text Member_No, Birthday;
	@FXML
	private ComboBox<String> CheckOutEligible;
	@FXML
    private Text messageText;  // 메시지를 표시할 Text 컨트롤 추가
	
	private MemberList memberList;
	private MemberManagementController memberManagementController;
	private Member selectedMember;
	
	
	// 선택된 회원의 데이터를 설정하는 메서드
    public void setMemberData(Member member) {
        this.selectedMember = member;
        Member_No.setText(member.getMember_No());
        Member_Id.setText(member.getMember_Id());
        Member_Name.setText(member.getMember_Name());
        PhoneNumber.setText(member.getPhoneNumber());
        Birthday.setText(member.getBirthday());
        CheckOutEligible.setValue(member.getCheckOutEligible());
    }
    
	public CorrectionMemberController() {
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
	private void handleCorrectionButton(ActionEvent event) {
		if (selectedMember != null) {
            selectedMember.setMember_Id(Member_Id.getText());
            selectedMember.setMember_Name(Member_Name.getText());
            selectedMember.setPhoneNumber(PhoneNumber.getText());
            selectedMember.setCheckOutEligible(CheckOutEligible.getValue());

            // 대출 가능 여부에 따라 CheckOutLimit 업데이트 (선택 사항)
            if ("X".equals(selectedMember.getCheckOutEligible())) {
                selectedMember.setCheckOutLimit(0);
            } else {
                selectedMember.setCheckOutLimit(3); // 기본 값 설정
            }
            
            showMessage("회원 정보 수정이 완료 되었습니다");

            // 창 닫기
            Stage stage = (Stage) CorrectionMember.getScene().getWindow();
            stage.close();
        }
	}
	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		// 현재 이벤트가 발생한 버튼이 속한 창을 찾아서 닫음
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
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
            Stage stage = (Stage) CorrectionMember.getScene().getWindow(); // 현재 창 가져오기
            stage.setScene(new Scene(root));
            stage.show(); // 새로운 씬 설정하고 보이기
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
