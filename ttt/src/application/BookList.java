package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class BookList {
	
	private ObservableList<Book> Book = FXCollections.observableArrayList();
	
	public BookList() {
        // 초기 멤버 데이터 추가
        Book.add(new Book("1", "Jihye01", "이지혜", "111", "대출가능"));
        Book.add(new Book("2", "Jihye01", "이혜", "1411", "대출가능"));
        Book.add(new Book("3", "Jie01", "지혜", "11651", "대출가능"));
        
        
        
        
	}
	
    public ObservableList<Book> getAllBook() {
        return Book;
    }


}
