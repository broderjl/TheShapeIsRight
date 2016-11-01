package project1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
			
			
			
			
			// -----------------
			// GAME SCREEN - TIM
			// -----------------
			
			
			// SET UP VISUALS
			
			//Jack, you should create these lists based on what's selected in the main menu
			ObservableList<String> color_options = 
				    FXCollections.observableArrayList(
				        "Color 1",
				        "Color 2",
				        "Color 3"
				    );
			ObservableList<String> shape_options = 
				    FXCollections.observableArrayList(
				        "Shape 1",
				        "Shape 2",
				        "Shape 3"
				    );
		
			VBox game_screen = new VBox();
			scene2 = new Scene(game_screen, 800, 600);
			
			HBox row1 = new HBox();
				Label score = new Label("Score: ");	
				Button menu_button = new Button("Main Menu");
				row1.getChildren().addAll(score, menu_button);
			HBox row2 = new HBox();
				VBox card1 = new VBox();
					final ComboBox color_choice1 = new ComboBox(color_options);
					final ComboBox shape_choice1 = new ComboBox(shape_options);
					card1.getChildren().addAll(color_choice1, shape_choice1);
				VBox card2 = new VBox();
					final ComboBox color_choice2 = new ComboBox(color_options);
					final ComboBox shape_choice2 = new ComboBox(shape_options);
					card2.getChildren().addAll(color_choice2, shape_choice2);
				row2.getChildren().addAll(card1, card2);
			HBox row3 = new HBox();
				VBox card3 = new VBox();
					final ComboBox color_choice3 = new ComboBox(color_options);
					final ComboBox shape_choice3 = new ComboBox(shape_options);
					card3.getChildren().addAll(color_choice3, shape_choice3);
				VBox card4 = new VBox();
					final ComboBox color_choice4 = new ComboBox(color_options);
					final ComboBox shape_choice4 = new ComboBox(shape_options);
					card4.getChildren().addAll(color_choice4, shape_choice4);
				VBox card5 = new VBox();
					final ComboBox color_choice5 = new ComboBox(color_options);
					final ComboBox shape_choice5 = new ComboBox(shape_options);
					card5.getChildren().addAll(color_choice5, shape_choice5);
				row3.getChildren().addAll(card3, card4, card5);
			HBox row4 = new HBox();
					VBox card6 = new VBox();
					final ComboBox color_choice6 = new ComboBox(color_options);
					final ComboBox shape_choice6 = new ComboBox(shape_options);
					card6.getChildren().addAll(color_choice6, shape_choice6);
				VBox card7 = new VBox();
					final ComboBox color_choice7 = new ComboBox(color_options);
					final ComboBox shape_choice7 = new ComboBox(shape_options);
					card7.getChildren().addAll(color_choice7, shape_choice7);
				row4.getChildren().addAll(card6, card7);
			HBox row5 = new HBox();
				Button flip_next = new Button("Next Flip");
				Label game_number = new Label("Game Number: ");
				row5.getChildren().addAll(flip_next, game_number);
			game_screen.getChildren().addAll(row1, row2, row3, row4, row5);

			
			
			// ADD EVENT HANDLING
			menu_button.setOnAction(e -> window.setScene(scene1));			
			
			
			
			// DISPLAY RESULTS
			
			window.setScene(scene1); //default
			window.setTitle("The Shape is Right!");
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
