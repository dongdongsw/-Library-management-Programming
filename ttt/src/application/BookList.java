package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class BookList {
	
	private static BookList instance;
	private ObservableList<Book> Books = FXCollections.observableArrayList();
	
	public BookList() {
        // 초기 멤버 데이터 추가
        Books.add(new Book("1", "사회", "정의란 무엇인가", "마이클 샌델", "미래엔", "대출가능"));
        Books.add(new Book("2", "철학", "존재와 시간", "마르틴 하이데거", "동서문화사", "대출가능"));
        Books.add(new Book("3", "문학", "탁류", "채만식", "현대문학", "대출가능"));
        Books.add(new Book("4", "사회", "침묵의 봄", "레이첼 카슨", "에코리브르", "대출가능"));
        Books.add(new Book("5", "문학", "패스트", "알베르 카뮈", "문학동네", "대출가능"));
        Books.add(new Book("6", "사회", "호모 데우스 : 미래의 역사", "유발 하리리", "김영사", "대출가능"));
        Books.add(new Book("7", "문학", "변신", "프란츠 카프카", "문학동네", "대출가능"));
        Books.add(new Book("8", "역사", "역사란 무엇인가", "E.H.카", "까치", "대출가능"));
        Books.add(new Book("9", "사회", "자유론", "존 스튜어트 밀", "책세상", "대출가능"));
        Books.add(new Book("10", "과학", "(위대한 수학자의) 수학의 즐거움", "레이먼드 플러드", "베이직북스", "대출가능"));
        Books.add(new Book("11", "역사", "혁명의 시대", "에릭 홉스봄", "한길사", "대출가능"));
        Books.add(new Book("12", "과학", "화학으로 이루어진 세상", "K.메데페셀헤르만", "에코리브르", "대출가능"));
        Books.add(new Book("13", "역사", "사피엔스", "유발 하리리", "김영사", "대출가능"));
        Books.add(new Book("14", "예술", "서양미술사", "E.H.곰브리치", "예경", "대출가능"));
        Books.add(new Book("15", "문학", "위대한 개츠비", "F.스콧 피츠제럴드", "문학동네", "대출가능"));
        Books.add(new Book("16", "사회", "소유의 종말", "제레미 리프킨", "민음사", "대출가능"));
        Books.add(new Book("17", "문학", "아큐정전", "루쉰", "마리북스", "대출가능"));
        Books.add(new Book("18", "기술", "이중나선", "제임스 D 왓슨", "궁리", "대출가능"));
        Books.add(new Book("19", "문학", "장미의 이름", "움베르토 에코", "열린책들", "대출가능"));
        Books.add(new Book("20", "철학", "국가", "플라톤", "아름다운날", "대출가능"));
        
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
