package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;



public class CheckOutReturn_ListController {
    
    @FXML
    private TableView<Book> BookTableView;
    private Member currentMember;
   
    @FXML
    private Button CheckOut, Exit;
    
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
    private TableColumn<Book, Boolean> CheckBoxColumn;
    @FXML
    private CheckBox CheckBox;
    
    @FXML //메시지를 보이게 만드는 기능 제공
    private Text messageText;
    
    private BookList BookList = new BookList(); // BookList 인스턴스 생성
    
    public void setMember(Member member) {
        this.currentMember = member;
        // 추가적인 회원 정보 설정 또는 초기화 작업
    }
    
    public void setBookList(BookList Books) {
        this.BookList = Books;
       
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
    	CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
    	TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
    	AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
    	PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
    	IsCheckOutedColumn.setCellValueFactory(new PropertyValueFactory<>("IsCheckOuted"));
    	
        // BookList의 데이터를 TableView에 설정
        BookTableView.setItems(BookList.getAllBook());
        
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
	private void handleExitButtonAction(ActionEvent event) {
		//종료 버튼을 눌렀을 때 해당 프로그램이 종료 된다.
		System.exit(0);
  		
	}

    
}
    
 
