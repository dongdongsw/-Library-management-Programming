package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.*;

public class Book {
	private StringProperty Book_No;
	private StringProperty Category;
	private StringProperty Title;
	private StringProperty Author;
	private StringProperty Publisher;
	private StringProperty IsCheckOuted;
	private StringProperty CheckOutedName;
    private StringProperty CheckOutedId;
    private int checkOutLimit;
	
	
	private BooleanProperty selected = new SimpleBooleanProperty();

	// 생성자
    public Book(String Book_No, String Category, String Title, String Author, String Publisher, String IsCheckOuted) {
    	this.Book_No = new SimpleStringProperty(Book_No);
        this.Category = new SimpleStringProperty(Category);
    	this.Title = new SimpleStringProperty(Title);
        this.Author = new SimpleStringProperty(Author);
        this.Publisher = new SimpleStringProperty(Publisher);
        this.IsCheckOuted = new SimpleStringProperty(IsCheckOuted);
        this.CheckOutedName = new SimpleStringProperty("");
        this.CheckOutedId = new SimpleStringProperty("");
    }
    public String getBook_No() {
    	return Book_No.get();
    }
    public void setBook_No(String Book_No) {
    	this.Book_No.set(Book_No);
    }
    public StringProperty BookNoProperty() {
        return Book_No;
    }
    
    public String getCategory() {
    	return Category.get();
    }
    public void setCategory(String Category) {
    	this.Category.set(Category);
    }
    public StringProperty CategoryProperty() {
        return Category;
    }
    
    public String getTitle() {
    	return Title.get();
    }
    public void setTitle(String Title) {
    	this.Title.set(Title);
    }
    public StringProperty TitleProperty() {
        return Title;
    }
    
    public String getAuthor() {
    	return Author.get();
    }
    public void setAuthor(String Author) {
    	this.Author.set(Author);
    }
    public StringProperty AuthorProperty() {
        return Author;
    }
    
    public String getPublisher() {
    	return Publisher.get();
    }
    public void setPublisher(String Publisher) {
    	this.Publisher.set(Publisher);
    }
    public StringProperty PublisherProperty() {
        return Publisher;
    }
    
    public String getIsCheckOuted() {
    	return IsCheckOuted.get();
    }
    public void setIsCheckOuted(String IsCheckOuted) {
    	this.IsCheckOuted.set(IsCheckOuted);
    }
    public StringProperty IsCheckOutedProperty() {
        return IsCheckOuted;
    }
    
    public String getCheckOutedName() {
        return CheckOutedName.get();
    }

    public void setCheckOutedName(String CheckOutedName) {
        this.CheckOutedName.set(CheckOutedName);
    }
    public StringProperty CheckOutedNameProperty() {
        return CheckOutedName;
    }

    public String getCheckOutedId() {
        return CheckOutedId.get();
    }

    public void setCheckOutedId(String CheckOutedId) {
        this.CheckOutedId.set(CheckOutedId);
    }
    public StringProperty CheckOutedIdProperty() {
        return CheckOutedId;
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
    	this.IsCheckOuted.set("대출중");
        this.CheckOutedName.set(memberName);
        this.CheckOutedId.set(memberId);
    }

    public void increaseCheckOutLimit() {
        this.checkOutLimit++;
    }


}
