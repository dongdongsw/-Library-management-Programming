package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

import java.util.*;

public class Book {
	private String Category;
	private String Title;
	private String Author;
	private String Publisher;
	private String IsCheckOuted;
	
	private BooleanProperty selected = new SimpleBooleanProperty();

    public final BooleanProperty selectedProperty() {
        return this.selected;
    }

    public final boolean isSelected() {
        return this.selectedProperty().get();
    }

    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }
	
	// 생성자
    public Book(String Category, String Title, String Author, String Publisher, String IsCheckOuted) {
        this.Category = Category;
    	this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.IsCheckOuted = IsCheckOuted;
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

	


}
