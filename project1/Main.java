package project1;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.*;
import javafx.geometry.*;


public class Main extends Application {
	
	Stage window;
	Scene scene1, scene2;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			
			
			// main screen (Jack)
			Label label1 = new Label("Scene number 1:");
			Button button1 = new Button("Go to scene 2");
			button1.setOnAction(e -> window.setScene(scene2));
			
			VBox layout1 = new VBox(20);
			layout1.getChildren().addAll(label1, button1);
			scene1 = new Scene(layout1, 200, 200);
			
			
			//game screen (Tim)
			Button button2 = new Button("Go back to scene 1");
			button2.setOnAction(e -> window.setScene(scene1));
			
			StackPane layout2 = new StackPane();
			layout2.getChildren().add(button2);
			scene2 = new Scene(layout2, 400, 300);
			
			window.setScene(scene1); //default
			window.setTitle("Title here");
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
