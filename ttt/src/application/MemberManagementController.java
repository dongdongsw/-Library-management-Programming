package application;

import javafx.stage.Modality;
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
	private Button AddMember, DeleteMember, MainMenu, LogOut, Exit;
	
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
    private BookList bookList = BookList.getInstance(); // 사용 가능한 책 목록
    private ObservableList<Member> members = Member.getMembers();
    
    
   
    @FXML
    private void initialize() {
    	memberNoColumn.setCellValueFactory(new PropertyValueFactory<>("Member_No"));
    	memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("Member_Id"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("Member_Name"));
        eligibleColumn.setCellValueFactory(new PropertyValueFactory<>("CheckOutEligible"));
        checkOutLimitColumn.setCellValueFactory(new PropertyValueFactory<>("CheckOutLimit"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        
        
        MemberTableView.setItems(Member.getMembers());
    	
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
    private void handleAddMemberButtonAction(ActionEvent event) {
    	//추가 버튼을 누르면 추가할 회원 정보를 입력 후 추가를 누르면 회원 목록에 추가된
    	// 추가 버튼을 누르면 새로운 창에서 AddMemberController를 로드
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/BookUserManagement/AddMember.fxml"));
            Parent root = loader.load();
         // AddMemberController 인스턴스를 가져와서 MemberManagementController 인스턴스를 설정
            AddMemberController addMemberController = loader.getController();
            addMemberController.setMemberManagementController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait(); // 새로운 창을 모달로 표시
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    @FXML
    private void handleDeleteMemberButtonAction(ActionEvent event) {
    	//ObservableList<Member> selectedMembers = members.filtered(member -> member.isSelected());
    	ObservableList<Member> selectedMembers = FXCollections.observableArrayList(members.filtered(member -> member.isSelected()));

        for (Member member : selectedMembers) {
            boolean isCheckedOut = bookList.getBookList().stream().anyMatch(book -> 
                book.getCheckOutedName().equals(member.getMember_Name()) && 
                book.getCheckOutedId().equals(member.getMember_Id())
            );

            if (isCheckedOut) {
                showMessage("도서 대출중인 회원은 삭제가 불가능 합니다.");
                return;
            } else {
                showDeleteConfirmationDialog(member);
            }
        }
    	
    }
    
    
    // 테이블 새로고침 매서드
    void refreshTable() {
    	MemberTableView.setItems(FXCollections.observableArrayList(Member.getMembers()));
        MemberTableView.refresh();
       
    }
    
    @FXML 
	private void handleMainMenuButtonAction(ActionEvent event) {
		//메인메뉴 버튼을 눌렀을 때 회원목록 화면 꺼짐과 동시에 메인화면으로 전환
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
		//로그아웃 버튼을 눌렀을 때 회원목록 화면 꺼짐과 동시에 로그인화면으로 전환
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
    
    private void showDeleteConfirmationDialog(Member member) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/BookUserManagement/Member_Delete_Message.fxml"));
            Parent root = loader.load();
            
            DeleteMemberController controller = loader.getController();
            controller.setMember(member);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            if (controller.isConfirmed()) {
            	showMessage("회원 삭제가 완료되었습니다.");
                Member.removeMember(member);
                refreshTable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
 // Setters for memberList and bookList
    public void setMemberList(MemberList memberList) {
        this.Member = memberList;
    }

    public void setBookList(BookList bookList) {
        this.bookList = bookList;
    }
    
    public void refreshMemberList() {
        MemberTableView.refresh();
    }

    public void removeMember(Member member) {
        members.remove(member);
        refreshMemberList();
    }

	

}
