package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

import java.util.*;

public class Member {
	private String Member_No;
	private String Member_Id;
	private String Member_Name;
	public String CheckOutEligible;
	public int CheckOutLimit;
	private String Birthday;
	private String PhoneNumber;
	private CheckBox checkBox;
	private List<Book> CheckOut = new ArrayList<>();
	private final BooleanProperty selected; // 추가된 선택 여부 속성
	
	
	

	// 생성자
	public Member(String Member_No, String Member_Id, String Member_Name, String CheckOutEligible, int CheckOutLimit, String Birthday, String PhoneNumber) {
        this.Member_No = Member_No;
        this.Member_Id = Member_Id;
        this.Member_Name = Member_Name;
        this.CheckOutEligible = CheckOutEligible;
        this.CheckOutLimit = CheckOutLimit;
        this.Birthday = Birthday;
        this.PhoneNumber = PhoneNumber;
        this.CheckOut = new ArrayList<>();
        this.checkBox = new CheckBox();
        this.selected = new SimpleBooleanProperty(false); // 초기화
    }
	
	 public String getMember_No() {
	    	return Member_No;
	    }
	 public void setMember_No(String Member_No) {
		 this.Member_No = Member_No;
	    }
	 
	 public String getMember_Id() {
	    	return Member_Id;
	    }
	 public void setMember_Id(String Member_Id) {
		 this.Member_Id = Member_Id;
	    }
	 
	 public String getMember_Name() {
	    	return Member_Name;
	    }
	 public void setMember_Name(String Member_Name) {
		 this.Member_Name = Member_Name;
	    }
	 
	 public String getCheckOutEligible() {
	    	return CheckOutEligible;
	    }
	 public void setCheckOutEligible(String CheckOutEligible) {
		 this.CheckOutEligible = CheckOutEligible;
	    }
	 
	 public int getCheckOutLimit() {
	    	return CheckOutLimit;
	    }
	 public void setCheckOutLimit(int CheckOutLimit) {
		 this.CheckOutLimit = CheckOutLimit;
		 
	    }
	 
	 public String getBirthday() {
	    	return Birthday;
	    }
	 public void setBirthday(String Birthday) {
		 this.Birthday = Birthday;
	    }
	 
	 public String getPhoneNumber() {
	    	return PhoneNumber;
	    }
	 public void setPhoneNumber(String PhoneNumber) {
		 this.PhoneNumber = PhoneNumber;
	    }
	 public void addBookToCheckedOut(Book book) {
		 this.CheckOut.add(book);
		 this.CheckOutLimit--;
	 }
	 
	 public CheckBox getCheckBox() {
		 return checkBox;
	    }

	 public boolean isCheckBoxSelected() {
		 return checkBox.isSelected();
	    }
	 
	 public void setCheckBoxSelected(boolean selected) {
		 checkBox.setSelected(selected);
	    }
	// Getter and setter for selected
	
	 public boolean isSelected() {
		 return selected.get();
	    }

	 public void setSelected(boolean selected) {
		 this.selected.set(selected);
	    }

	 public BooleanProperty selectedProperty() {
		 return selected;
	    }

	// 대출한 도서 목록 반환 메서드
	    public List<Book> getBorrowedBooks() {
	        return CheckOut;
	    }

	    // 대출한 도서 추가 메서드
	    public void addBorrowedBook(Book book) {
	        CheckOut.add(book);
	    }

	    // 대출한 도서 제거 메서드
	    public void removeBorrowedBook(Book book) {
	        CheckOut.remove(book);
	    }
	    
	    public void increaseCheckOutLimit() {
	        this.CheckOutLimit++;
	    }
	

}
