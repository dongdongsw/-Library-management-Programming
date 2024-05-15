package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class BookList {
	
	private static BookList instance;
	private ObservableList<Book> Books = FXCollections.observableArrayList();
	
	public BookList() {
        // 초기 멤버 데이터 추가
        Books.add(new Book("1", "1", "Jihye01", "이지혜", "111", "대출가능"));
        Books.add(new Book("2", "2", "Jihye01", "이혜", "1411", "대출가능"));
        Books.add(new Book("3", "3", "Jie01", "지혜", "11651", "대출가능"));
        
        
        
	}
	
	public static synchronized BookList getInstance() {
		if(instance == null) {
			instance = new BookList();
		}
		return instance;
	}
	
    public ObservableList<Book> getAllBook() {
        return Books;
    }
    
    public void updateBook(Book book) {
    	//도서 상태를 업데이트 합니다.
    	for(int i = 0; i < Books.size(); i++) {
    		if(Books.get(i).getTitle().equals(book.getTitle())) {
    			Books.set(i, book);
    			break;
    		}
    	}
    }


}
