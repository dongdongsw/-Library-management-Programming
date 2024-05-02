package application;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Book {
	private String Category;
	private String Title;
	private String Author;
	private String Publisher;
	private String IsCheckOuted;
	
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
    public String getAuthor() {
    	return Author;
    }
    public String Publisher() {
    	return Publisher;
    }
    public String IsCheckOuted() {
    	return IsCheckOuted;
    }


}
