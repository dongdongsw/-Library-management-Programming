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

public class AddBookController {
	
	@FXML
	private Button AddBook, Cancel;
	@FXML
	private TextField Book_No, Category, Title, Author, Publisher;
	@FXML
    private Text messageText;  // 메시지를 표시할 Text 컨트롤 추가
	
	private BookList bookList;
	private BookListInfoController bookListInfoController;
	
	public AddBookController() {
        bookList = bookList.getInstance();  // Singleton 인스턴스 가져오기
    }
	
	public void setBookListInfoController(BookListInfoController controller) {
		this.bookListInfoController = controller;
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
		String No = Book_No.getText();
		String category = Category.getText();
		String title = Title.getText();
		String author = Author.getText();
		String publisher = Publisher.getText();
		String isCheckOuted = "대출가능";
		String checkOutedName = "";
		String checkOutedId = "";

		Book newBook = new Book(No, category, title, author, publisher, isCheckOuted);
		// 도서 추가 함수 호출
        addBook(newBook);
		
	}
	
	public void addBook(Book book) {
		// 도서를 추가할 때 도서 번호가 중복될 경우에 대한 함수
        for (Book B : bookList.getAllBook()) {
            if (B.getBook_No().equals(book.getBook_No())) {
                showMessage("해당 도서 번호는 이미 있습니다.");
                return; // 중복되면 추가하지 않고 함수 종료
            }
        }
        bookList.add(book);
        showMessage("도서가 성공적으로 추가되었습니다.");
        
    	// 회원 추가 후 테이블 새로고침
        bookListInfoController.refreshTable();

        // 창 닫기
        Stage stage = (Stage) AddBook.getScene().getWindow();
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

}
