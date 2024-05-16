package application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MemberManagementController {
	
	@FXML
	private Button MainMenu, LogOut, Exit;
	
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
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    
    @FXML //메시지를 보이게 만드는 기능 제공
    private Text messageText;

    public void setMessage(String message) {
        messageText.setText(message);
    }
    
    
    private MemberList Member = MemberList.getInstance(); // MemberList 인스턴스 생성
    private BookList availableBooks = BookList.getInstance(); // 사용 가능한 책 목록

    
    
    

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
        MemberTableView.setItems(Member.getInstance().getMembers());
           
        
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
    
    @FXML 
	private void handleMainMenuButtonAction(ActionEvent event) {
		//메인메뉴 버튼을 눌렀을 때 대출&반납 메뉴가 꺼짐과 동시에 메인화면으로 전환
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
	
	@FXML
	private void handleSearchButtonAction(ActionEvent event) {
	    search();
	}

	@FXML
	private void handleSearch(KeyEvent event) {
	    if (event.getCode() == KeyCode.ENTER) {
	        search();
	    }
	}

	private void search() {
	    String query = searchField.getText().toLowerCase();
	    ObservableList<Member> filteredList = FXCollections.observableArrayList();

	    for (Member member : Member.getMembers()) {
	        if (member.getMember_Id().toLowerCase().contains(query) ||
	            member.getMember_Name().toLowerCase().contains(query) ||
	            member.getPhoneNumber().toLowerCase().contains(query)) {
	            filteredList.add(member);
	        }
	    }

	    MemberTableView.setItems(filteredList);
	}

}
