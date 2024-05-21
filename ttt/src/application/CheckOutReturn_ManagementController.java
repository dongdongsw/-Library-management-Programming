package application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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





public class CheckOutReturn_ManagementController {
	@FXML
	private Button SearchButton, MainMenu, LogOut, Exit, CheckOutButton, ReturnButton;
	
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
   
    
    
 // 대출 버튼 클릭 이벤트 핸들러
    @FXML
    private void handleCheckOutButtonAction(ActionEvent event) {
        Member selectedMember = MemberTableView.getSelectionModel().getSelectedItem();
        ObservableList<Member> members = MemberTableView.getItems();
        for (Member member : members) {
            if (member.isSelected()) {
                // 체크된 멤버를 selectedMember로 처리
                selectedMember = member;
                break; // 또는 계속해서 모든 체크된 멤버를 처리
            }
        }
        
        if (selectedMember == null) {
        	try {
                // 메시지 창 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                Parent root = loader.load();
                CheckOutReturn_MessageController controller = loader.getController();
                controller.setMessage("회원이 선택되지 않았습니다.");
                showNewStage(root);
                return;
        	}
        	catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selectedMember != null && selectedMember.getCheckOutLimit() > 0 && "O".equals(selectedMember.getCheckOutEligible())) {
            try {
                // CheckOutReturn_List.fxml 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_List.fxml"));
                Parent root = loader.load();
                CheckOutReturn_ListController ListController = loader.getController();
                
             // 선택된 회원과 책 목록을 ListController에 전달
                Member selectedmember = selectedMember; // 선택된 회원 가져오는 메소드
                ListController.setMember(selectedMember);
                ListController.setBookList(availableBooks);
             
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("도서 대출");
                stage.show();

                // 현재 창 닫기
                ((Stage) CheckOutButton.getScene().getWindow()).close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
           
        } 
        else {
            // 에러 메시지 표시
        	try {
                // 메시지 창 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                Parent root = loader.load();
                CheckOutReturn_MessageController controller = loader.getController();

                // 상황에 따른 메시지 설정
                if ("X".equals(selectedMember.getCheckOutEligible())) {
                    controller.setMessage("대출이 불가능한 상태입니다.");
                    showNewStage(root);
                    return;
                }

                if (selectedMember.getCheckOutLimit() == 0) {
                    controller.setMessage("대출 가능한 도서의 수가 없습니다.");
                    showNewStage(root);
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        	
        }
    }
   
    //반납 버튼 클릭시 이벤트 핸들러
    @FXML
    private void handleReturnButtonAction(ActionEvent event) {
    	Member selectedMember = MemberTableView.getSelectionModel().getSelectedItem();
        ObservableList<Member> members = MemberTableView.getItems();
        for (Member member : members) {
            if (member.isSelected()) {
                // 체크된 멤버를 selectedMember로 처리
                selectedMember = member;
                break; // 또는 계속해서 모든 체크된 멤버를 처리
            }
        }
        
        if (selectedMember == null) {
        	try {
                // 메시지 창 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                Parent root = loader.load();
                CheckOutReturn_MessageController controller = loader.getController();
                controller.setMessage("회원이 선택되지 않았습니다.");
                showNewStage(root);
                return;
        	}
        	catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (selectedMember.getBorrowedBooks().isEmpty()) {
        	// 대출한 도서가 없는 경우
        	try {
                // 메시지 창 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                Parent root = loader.load();
                CheckOutReturn_MessageController controller = loader.getController();
                controller.setMessage("대출한 도서가 존재하지 않습니다.");
                showNewStage(root);
                return;
        	}
        	catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        else {
        	// 대출한 도서가 있는 경우
            try {
                // FXML 파일 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_Return_List.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));

                // ReturnBookController 설정
                ReturnBookController returnBookController = loader.getController();
                returnBookController.setMember(selectedMember);
                returnBookController.setManagementController(this);
                returnBookController.showBorrowedBooks();

                // 화면 표시
                stage.show();
                
            } catch (IOException e) {
                e.printStackTrace();
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

	public void refreshMemberAndBookData() {
		ObservableList<Member> updatedMemberList = FXCollections.observableArrayList(MemberList.getInstance().getMembers());
	    System.out.println("Updated Member List: " + updatedMemberList);
	    MemberTableView.setItems(updatedMemberList);
	    MemberTableView.refresh();
		
	}

	
	
 // 모든 회원을 가져오는 예시 메서드
    private List<Member> getAllMembers() {
        // 실제 구현에 맞게 데이터를 가져오는 로직을 추가
        return new ArrayList<>();
    }
}
