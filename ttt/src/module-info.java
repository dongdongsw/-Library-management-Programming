module ttt {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.base;
	requires javafx.graphics;
	opens application to javafx.graphics, javafx.fxml, javafx.base;

}
