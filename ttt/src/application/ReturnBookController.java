package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReturnBookController {
   
	@FXML 
	private TableView<Book> ReturnBookTableView;
	@FXML
	private Button ReturnButton, CloseButton;
	@FXML
    private TableColumn<Book, String> BookNoColumn;
    @FXML
    private TableColumn<Book, String> CategoryColumn;
    @FXML
    private TableColumn<Book, String> TitleColumn;
    @FXML
    private TableColumn<Book, String> AuthorColumn;
    @FXML
    private TableColumn<Book, String> PublisherColumn;
    @FXML
    private TableColumn<Book, Boolean> CheckBoxColumn;
    @FXML
    private CheckBox SelectBookCheckBox;
    @FXML //메시지를 보이게 만드는 기능 제공
    private Text messageText;
    
    private BookList bookList; 
    private Member selectedMember; 
    private CheckOutReturn_ManagementController managementController; // ManagementController의 참조
    
    @FXML
    private void initialize() {
    	BookNoColumn.setCellValueFactory(cellData -> cellData.getValue().BookNoProperty());
    	CategoryColumn.setCellValueFactory(cellData -> cellData.getValue().CategoryProperty());
        TitleColumn.setCellValueFactory(cellData -> cellData.getValue().TitleProperty());
        AuthorColumn.setCellValueFactory(cellData -> cellData.getValue().AuthorProperty());
        PublisherColumn.setCellValueFactory(cellData -> cellData.getValue().PublisherProperty());
        
     // 체크박스 칼럼 설정
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        CheckBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(CheckBoxColumn));
        ReturnBookTableView.setEditable(true);

        // 체크박스 선택 시 다른 체크박스 선택 해제 (옵션)
        ReturnBookTableView.getItems().forEach(member -> 
            member.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    ReturnBookTableView.getItems().stream()
                        .filter(otherMember -> otherMember != member)
                        .forEach(otherMember -> otherMember.setSelected(false));
                }
            })
        );
    }
    
 // 선택된 회원을 설정하는 메서드
    public void setMember(Member member) {
        this.selectedMember = member;
    }
    
 // 선택된 회원의 대출 도서 목록을 표시하는 메서드
    public void showBorrowedBooks() {
        if (selectedMember != null) {
            ReturnBookTableView.getItems().setAll(selectedMember.getBorrowedBooks());
        }
    }
 // ManagementController의 참조 설정 메서드
    public void setManagementController(CheckOutReturn_ManagementController controller) {
        this.managementController = controller;
    }
    // 닫기 버튼을 누를시 해당 화면만 사라짐
    @FXML
	private void handleCancelButtonAction(ActionEvent event) {
		// 현재 이벤트가 발생한 버튼이 속한 창을 찾아서 닫음
	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
  		
	}
    
    // 도서 선택 후 반납 클릭시 도서 반납이 이루어짐 
    @FXML
	private void handleReturnBookButtonAction(ActionEvent event) {
    	// 선택된 도서 가져오기
        Book selectedBook = ReturnBookTableView.getItems().stream()
            .filter(Book::isSelected)
            .findFirst()
            .orElse(null);

        if (selectedBook != null) {
            // 도서 반납 처리
            selectedMember.removeBorrowedBook(selectedBook);
            selectedBook.setIsCheckOuted("대출가능");
            selectedBook.setCheckOutedName("");
            selectedBook.setCheckOutedId("");
            
         // CheckOutLimit 증가
            selectedMember.increaseCheckOutLimit(); // 이 메서드는 selectedMember 클래스에 정의되어야 합니다.          
            selectedBook.increaseCheckOutLimit(); // 이 메서드는 Book 클래스에 정의되어야 합니다.

           
            // UI 업데이트
            ReturnBookTableView.getItems().remove(selectedBook);
     
                       
        	try {
                // 메시지 창 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                Parent root = loader.load();
                CheckOutReturn_MessageController controller = loader.getController();
                controller.setMessage("반납이 완료되었습니다.");
                showNewStage(root);
                if (managementController != null) {
                    Platform.runLater(() -> managementController.refreshMemberAndBookData());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        	
        	

            // 창 닫기
            Stage stage = (Stage) ReturnButton.getScene().getWindow();
            stage.close();
        }
        
        else if(selectedBook == null) {
        	showMessage("도서를 선택하지 않았습니다.");
        	return;
        }
    }

 // 새 창에서 메시지 보여주는 메소드
    private void showNewStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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
 
}