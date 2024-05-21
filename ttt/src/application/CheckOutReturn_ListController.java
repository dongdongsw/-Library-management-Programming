package application;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class CheckOutReturn_ListController {
    
    @FXML
    private TableView<Book> BookTableView;
    private Member currentMember;
   
    @FXML
    private TextField SearchField;
    @FXML
    private Button CheckOutButton, SearchButton, PreviousPage, Exit;
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
	    String query = SearchField.getText().toLowerCase();
	    ObservableList<Book> filteredList = FXCollections.observableArrayList();

	    for (Book book : bookList.getBookList()) {
	        if (book.getTitle().toLowerCase().contains(query) ||
	            book.getAuthor().toLowerCase().contains(query) ||
	            book.getPublisher().toLowerCase().contains(query)||
	            book.getCategory().toLowerCase().contains(query)) {
	            filteredList.add(book);
	        }
	    }

	    BookTableView.setItems(filteredList);
	}
    
    @FXML
    private void handleCheckOutButtonAction(ActionEvent event) throws IOException {
        //Book selectedBook = BookTableView.getSelectionModel().getSelectedItem();
        
        Book selectedBook = null;
        for (Book book : BookTableView.getItems()) {
            if (book.isSelected()) {
                selectedBook = book;
                break;
            }
        }
        
        if (selectedBook != null && "대출가능".equals(selectedBook.getIsCheckOuted())) {
        	try {
        	
                
             // 현재 선택된 책을 "대출중"으로 업데이트
                selectedBook.checkOutBook(currentMember.getMember_Name(), currentMember.getMember_Id());
                // 현재 회원의 대출 목록에 추가하고 대출 가능 수를 감소
                currentMember.addBookToCheckedOut(selectedBook);
                
                bookList.updateBook(selectedBook); // BookList에 변경 사항 반영
                BookTableView.refresh();
                // UI 갱신
                //BookTableView.refresh();
                

                // 메시지 창 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
                Parent root = loader.load();
                CheckOutReturn_MessageController controller = loader.getController();
                controller.setMessage("대출이 완료되었습니다.");
                showNewStage(root);
                
                //BookTableView 및 BookList 갱신
                BookTableView.refresh();
                BookList.getInstance().getAllBook().forEach(book -> System.out.println(book.getTitle() + ": " + book.getIsCheckOuted()));


                // ManagementController로 돌아가기
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_Management.fxml"));
                Parent root1 = loader1.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("도서 대출");
                stage.show();

                ((Stage) CheckOutButton.getScene().getWindow()).close();
        	} catch (IOException e) {
                e.printStackTrace(); 
            }
        }
        else if("대출중".equals(selectedBook.getIsCheckOuted())) {
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_message.fxml"));
        		Parent root = loader.load();
        		CheckOutReturn_MessageController controller = loader.getController();
        		controller.setMessage("현재 대출중인 도서입니다.");
        		showNewStage(root);
        	} catch(IOException e) {
        		e.printStackTrace();
        	}
        	
        }
    }
    @FXML
	private void handlePreviousPageButtonAction(ActionEvent event) {
		//이전으로 버튼을 클릭시 도서&반납화면으로 전환함.
    	try {
    		// ManagementController로 돌아가기
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/application/Ui/CheckOutReturn/CheckOutReturn_Management.fxml"));
            Parent root1 = loader1.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("도서 대출");
            stage.show();

            ((Stage) CheckOutButton.getScene().getWindow()).close();
    	} catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    
    private void refreshMemberView() {
        BookTableView.refresh();
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
    
 // 새 창에서 메시지 보여주는 메소드
    private void showNewStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    
}
    
 
