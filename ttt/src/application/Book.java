package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

import java.util.*;

public class Book {
	private String Book_No;
	private String Category;
	private String Title;
	private String Author;
	private String Publisher;
	private String IsCheckOuted;
	private String CheckOutedName;
    private String CheckOutedId;
	
	
	private BooleanProperty selected = new SimpleBooleanProperty();

	/*
    public final BooleanProperty selectedProperty() {
        return this.selected;
    }

    public final boolean isSelected() {
        return this.selectedProperty().get();
    }

    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }
	*/
	// 생성자
    public Book(String Book_No, String Category, String Title, String Author, String Publisher, String IsCheckOuted) {
    	this.Book_No = Book_No;
        this.Category = Category;
    	this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.IsCheckOuted = IsCheckOuted;
        this.CheckOutedName = "";
        this.CheckOutedId = "";
    }
    public String getBook_No() {
    	return Book_No;
    }
    public void setBook_No(String Book_No) {
    	this.Book_No = Book_No;
    }
    public String getCategory() {
    	return Category;
    }
    public void setCategory(String Category) {
    	this.Category = Category;
    }
    public String getTitle() {
    	return Title;
    }
    public void setTitle(String Title) {
    	this.Title = Title;
    }
    public String getAuthor() {
    	return Author;
    }
    public void setAuthor(String Author) {
    	this.Author = Author;
    }
    public String getPublisher() {
    	return Publisher;
    }
    public void setPublisher(String Publisher) {
    	this.Publisher = Publisher;
    }
    public String getIsCheckOuted() {
    	return IsCheckOuted;
    }
    public void setIsCheckOuted(String IsCheckOuted) {
    	this.IsCheckOuted = IsCheckOuted;
    }
    
    public String getCheckOutedName() {
        return CheckOutedName;
    }

    public void setCheckOutedName(String CheckOutedName) {
        this.CheckOutedName = CheckOutedName;
    }

    public String getCheckOutedId() {
        return CheckOutedId;
    }

    public void setCheckOutedId(String CheckOutedId) {
        this.CheckOutedId = CheckOutedId;
    }
    
    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    public boolean isSelected() {
        return this.selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
    
    public void checkOutBook(String memberName, String memberId) {
    	this.IsCheckOuted = "대출중";
    	this.CheckOutedName = memberName;
        this.CheckOutedId = memberId;
    }

	


}
