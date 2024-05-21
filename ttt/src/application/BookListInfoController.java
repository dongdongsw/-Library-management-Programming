package application;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class BookListInfoController {
	
	@FXML
    private TableView<Book> BookTableView;
    private Member currentMember;
   
    @FXML
    private Button AddBook, DeleteBook, MainMenu, LogOut, Exit;
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
    private TableColumn<Book, String> IsCheckOutedColumn;
    @FXML
    private TableColumn<Book, String> MemberColumn;
    @FXML
    private TableColumn<Book, Boolean> CheckBoxColumn;
    @FXML
    private CheckBox CheckBox;
    
    @FXML //메시지를 보이게 만드는 기능 제공
    private Text messageText;
    
    private BookList bookList = BookList.getInstance(); // BookList 인스턴스 생성
    
    public void setMember(Member member) {
        this.currentMember = member;
        // 추가적인 회원 정보 설정 또는 초기화 작업
    }
    
    public void setBookList(BookList Books) {
        this.bookList = Books;
       
        if (Books != null) {
            ObservableList<Book> observableBooks = FXCollections.observableArrayList(Books.getAllBook());
            BookTableView.setItems(observableBooks);
        } else {
            // 적절한 처리 또는 로그 출력
            System.out.println("No books available or BookList is null");
        }
    }
    
    
    
    @FXML
    private void initialize() {
    	BookNoColumn.setCellValueFactory(new PropertyValueFactory<>("Book_No"));
    	CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
    	TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
    	IsCheckOutedColumn.setCellValueFactory(new PropertyValueFactory<>("IsCheckOuted"));
    	MemberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckOutedName() + " (" + cellData.getValue().getCheckOutedId() + ")"));

    	
        // BookList의 데이터를 TableView에 설정
        BookTableView.setItems(bookList.getAllBook());
        
     // 체크박스 칼럼 설정
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        CheckBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(CheckBoxColumn));
        BookTableView.setEditable(true);

     // 체크박스 선택 시 다른 체크박스 선택 해제 (단일 선택 기능)
        BookTableView.getItems().forEach(book -> 
            book.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    BookTableView.getItems().stream()
                        .filter(otherBook -> otherBook != book)
                        .forEach(otherBook -> otherBook.setSelected(false));
                }
            })
        );
        
        BookTableView.itemsProperty().addListener((obs, oldBooks, newBooks) -> {
            if (newBooks != null) {
                newBooks.forEach(book -> {
                    book.selectedProperty().addListener((obsInner, wasSelected, isSelected) -> {
                        if (isSelected) {
                            newBooks.stream()
                                .filter(otherBook -> otherBook != book)
                                .forEach(otherBook -> otherBook.setSelected(false));
                        }
                    });
                });
            }
        });
    }
    
    
    
    
    private void refreshMemberView() {
        BookTableView.refresh();
    }
    
    @FXML 
    private void handleAddBookButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/BookUserManagement/AddBook.fxml"));
            Parent root = loader.load();
            
         // AddBookController를 가져와서 bookListInfoController를 설정
            AddBookController addBookController = loader.getController();
            addBookController.setBookListInfoController(this); // 현재 BookListInfoController를 전달
            
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait(); // 새로운 창을 모달로 표시
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    @FXML
    private void handleDeleteBookButtonAction(ActionEvent event) {
    	
    	Book selectedBook = null;
        for (Book book : BookTableView.getItems()) {
            if (book.isSelected()) {
                selectedBook = book;
                break;
            }
           
        }  
        
        if(selectedBook != null) {
        	if("대출중".equals(selectedBook.getIsCheckOuted())) {
            	try {
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
            		Parent root = loader.load();
            		CheckOutReturn_MessageController controller = loader.getController();
            		controller.setMessage("대출중인 도서는 삭제가 불가능 합니다.");
            		showNewStage(root);
            	} catch(IOException e) {
            		e.printStackTrace();
            	}
            }
            
            else if("대출가능".equals(selectedBook.getIsCheckOuted())){
            	showDeleteConfirmationDialog(selectedBook);
            }
        }
        else {
            // 선택된 책이 없는 경우 처리
            System.out.println("선택된 책이 없습니다.");
            showMessage("선택된 책이 없습니다.");
        }
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
    
 // 새 창에서 메시지 보여주는 메소드
    private void showNewStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    
 // 테이블 새로고침 매서드
    void refreshTable() {
    	BookTableView.setItems(FXCollections.observableArrayList(bookList.getAllBook()));
        BookTableView.refresh();
       
    }
    
    private void showDeleteConfirmationDialog(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/BookUserManagement/DeleteBook_Message.fxml"));
            Parent root = loader.load();
            
            DeleteBookController controller = loader.getController();
            controller.setBook(book);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            if (controller.isConfirmed()) {
            	showMessage("도서 삭제가 완료되었습니다.");
                bookList.removeBook(book);
                refreshTable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
