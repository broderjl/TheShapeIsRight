package project1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
			
			/*
			 * Variables !!!!! Not sure where they are going to need to go
			 * or what type (i.e. final? public?)
			 */
			
			int numberOfShapes;
			
			Label label1 = new Label("Main Menu:");
			Button play_button = new Button("Play Game");
			play_button.setOnAction(e -> window.setScene(scene2));
			
			/*
			 * Drop down menu for selecting an N
			 * at least 3 and at most 9
			 */
			
			// Label for selection
			Label selectionLabel = new Label("Number of Shapes You Would Like To Play With:");

			
			ObservableList<String> numberOfShapesList =
					FXCollections.observableArrayList(
							"3", "4", "5", "6", "7", "8", "9");
			
			ComboBox<String> numberOfShapesBox = new ComboBox<String>(numberOfShapesList);			
			VBox selectionBox = new VBox();
			selectionBox.getChildren().addAll(selectionLabel, numberOfShapesBox);
			
			/*
			 * Event Handling
			 */
			
			// Handling for the number of shapes selection
			
			/*
			 * Adding the nodes to the scene
			 */
			
			VBox main_screen = new VBox(20);
			main_screen.getChildren().addAll(label1, play_button, selectionBox);
			scene1 = new Scene(main_screen, 800, 600);
			
			
			//game screen (Tim)
			Button menu_button = new Button("Main Menu");
			Label label2 = new Label("Here is where we put the game.");
			menu_button.setOnAction(e -> window.setScene(scene1));
			
			VBox game_screen = new VBox();
			game_screen.getChildren().addAll(label2, menu_button);
			scene2 = new Scene(game_screen, 800, 600);
			
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
