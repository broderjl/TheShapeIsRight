package project1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;



public class Main extends Application {
	
	Stage window;
	Scene scene1, scene2;
	static int numberOfShapes;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			
			
			// main screen (Jack)
			
			/*
			 * Variables !!!!! Not sure where they are going to need to go
			 * or what type (i.e. final? public?)
			 */
			
			
			Label label1 = new Label("Main Menu:");
			Button play_button = new Button("Play Game");
			play_button.getStyleClass().add("button");
			play_button.setOnAction(e -> window.setScene(scene2));
			
			/*
			 * Drop down menu for selecting an N
			 * at least 3 and at most 9
			 */
			
			// Label for selection
			Label selectionLabel = new Label("Number of Shapes You Would Like To Play With:");
			selectionLabel.getStyleClass().add("label");

			// Observable list of possible side number selections
			ObservableList<String> numberOfShapesList =
					FXCollections.observableArrayList(
							"3", "5", "7", "9");
			
			// Create ComboBox for selecting the number of shapes
			ComboBox<String> numberOfShapesBox = new ComboBox<String>(numberOfShapesList);			
			VBox selectionBox = new VBox();
			selectionBox.getChildren().addAll(selectionLabel, numberOfShapesBox);
			
			/*
			 * 
			 * ListView for selecting colors and shapes to 
			 * be used for the playing of the game
			 * 
			 */
			// HBox to hold the list views 
			HBox listViewsHBox = new HBox();
			
			// Observable list and list view for selecting colors
			ObservableList<String> colorSelectionList = FXCollections.observableArrayList(
					"Color1", "Color2", "Color3");
			ListView<String> colorSelectionListView = new ListView<String>(colorSelectionList);
			colorSelectionListView.setPrefHeight(80);
			colorSelectionListView.setPrefWidth(100);
			
			// Observable list and list view for selecting shapes
			ObservableList<String> shapeSelectionList = FXCollections.observableArrayList(
					"Shape1", "Shape2", "Shape3");
			ListView<String> shapeSelectionListView = new ListView<String>(shapeSelectionList);
			shapeSelectionListView.setPrefHeight(80);
			shapeSelectionListView.setPrefWidth(100);
			
			listViewsHBox.getChildren().add(colorSelectionListView);
			listViewsHBox.getChildren().add(shapeSelectionListView);
			listViewsHBox.getStyleClass().add("listViewsHBox");
			
			
			
			
			
			/*
			 * Event Handling
			 */
			
			// Handling for the number of shapes selection
			numberOfShapesBox.setOnAction((event) -> {
				String selectedNumber = numberOfShapesBox.getSelectionModel().getSelectedItem();
				numberOfShapes = Integer.parseInt(selectedNumber);
				System.out.println(numberOfShapes);
			});
			
			/*
			 * Adding the nodes to the scene
			 */
			
			VBox main_screen = new VBox(20);
			main_screen.getStyleClass().add("background");
			main_screen.getChildren().addAll(label1, play_button, listViewsHBox, selectionBox);
			scene1 = new Scene(main_screen, 900, 700);
			
			
			
			
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
			game_screen.getStyleClass().add("background");
			scene2 = new Scene(game_screen, 900, 700);
			
			HBox row1 = new HBox();
				
				Label score = new Label("Score: ");
				Label title = new Label("The Shape is Right!");
				title.getStyleClass().add("title");
				Button menu_button = new Button("Main Menu");
				row1.getChildren().addAll(score, title, menu_button);
				
			HBox row2 = new HBox();
				
				VBox card1 = new VBox(50);
					
					final ComboBox<String> color_choice1 = new ComboBox<String>(color_options);
					color_choice1.getStyleClass().add("choice");
					color_choice1.setMinWidth(110);
					color_choice1.setPrefWidth(110);
					color_choice1.setMaxWidth(110);
					final ComboBox<String> shape_choice1 = new ComboBox<String>(shape_options);
					shape_choice1.getStyleClass().add("choice");
					shape_choice1.setMinWidth(110);
					shape_choice1.setPrefWidth(110);
					shape_choice1.setMaxWidth(110);
					card1.setPrefHeight(160);
					card1.setMinHeight(160);
					card1.setMaxHeight(160);
					card1.setMinWidth(120);
					card1.setPrefWidth(120);
					card1.setMaxWidth(120);
					card1.setPadding(new Insets(20, 0, 20, 0));
					card1.getChildren().addAll(color_choice1, shape_choice1);
					card1.getStyleClass().add("card");
				
				VBox card2 = new VBox(50);
					
					final ComboBox<String> color_choice2 = new ComboBox<String>(color_options);
					color_choice2.getStyleClass().add("choice");
					color_choice2.setMinWidth(110);
					color_choice2.setPrefWidth(110);
					color_choice2.setMaxWidth(110);
					final ComboBox<String> shape_choice2 = new ComboBox<String>(shape_options);
					shape_choice2.getStyleClass().add("choice");
					shape_choice2.setMinWidth(110);
					shape_choice2.setPrefWidth(110);
					shape_choice2.setMaxWidth(110);
					card2.setPrefHeight(160);
					card2.setMinHeight(160);
					card2.setMaxHeight(160);
					card2.setMinWidth(120);
					card2.setPrefWidth(120);
					card2.setMaxWidth(120);
					card2.setPadding(new Insets(20, 0, 20, 0));
					card2.getStyleClass().add("card");
					card2.getChildren().addAll(color_choice2, shape_choice2);

					
				row2.getChildren().addAll(card1, card2);
				row2.getStyleClass().add("card_row");
			
				
			HBox row3 = new HBox();
				
				VBox card3 = new VBox(50);
					
					final ComboBox<String> color_choice3 = new ComboBox<String>(color_options);
					color_choice3.getStyleClass().add("choice");
					color_choice3.setMinWidth(110);
					color_choice3.setPrefWidth(110);
					color_choice3.setMaxWidth(110);
					final ComboBox<String> shape_choice3 = new ComboBox<String>(shape_options);
					shape_choice3.getStyleClass().add("choice");
					shape_choice3.setMinWidth(110);
					shape_choice3.setPrefWidth(110);
					shape_choice3.setMaxWidth(110);
					card3.setPrefHeight(160);
					card3.setMinHeight(160);
					card3.setMaxHeight(160);
					card3.setMinWidth(120);
					card3.setPrefWidth(120);
					card3.setMaxWidth(120);
					card3.setPadding(new Insets(20, 0, 20, 0));
					card3.getStyleClass().add("card");
					card3.getChildren().addAll(color_choice3, shape_choice3);
				
				VBox card4 = new VBox(50);
					
					final ComboBox<String> color_choice4 = new ComboBox<String>(color_options);
					color_choice4.getStyleClass().add("choice");
					color_choice4.setMinWidth(110);
					color_choice4.setPrefWidth(110);
					color_choice4.setMaxWidth(110);
					final ComboBox<String> shape_choice4 = new ComboBox<String>(shape_options);
					shape_choice4.getStyleClass().add("choice");
					shape_choice4.setMinWidth(110);
					shape_choice4.setPrefWidth(110);
					shape_choice4.setMaxWidth(110);
					card4.setPrefHeight(160);
					card4.setMinHeight(160);
					card4.setMaxHeight(160);
					card4.setMinWidth(120);
					card4.setPrefWidth(120);
					card4.setMaxWidth(120);
					card4.setPadding(new Insets(20, 0, 20, 0));
					card4.getStyleClass().add("card");
					card4.getChildren().addAll(color_choice4, shape_choice4);
				
				VBox card5 = new VBox(50);
					
					final ComboBox<String> color_choice5 = new ComboBox<String>(color_options);
					color_choice5.getStyleClass().add("choice");
					color_choice5.setMinWidth(110);
					color_choice5.setPrefWidth(110);
					color_choice5.setMaxWidth(110);
					final ComboBox<String> shape_choice5 = new ComboBox<String>(shape_options);
					shape_choice5.getStyleClass().add("choice");
					shape_choice5.setMinWidth(110);
					shape_choice5.setPrefWidth(110);
					shape_choice5.setMaxWidth(110);
					card5.setPrefHeight(160);
					card5.setMinHeight(160);
					card5.setMaxHeight(160);
					card5.setMinWidth(120);
					card5.setPrefWidth(120);
					card5.setMaxWidth(120);
					card5.setPadding(new Insets(20, 0, 20, 0));
					card5.getStyleClass().add("card");
					card5.getChildren().addAll(color_choice5, shape_choice5);
				
				row3.getChildren().addAll(card3, card4, card5);
				row3.getStyleClass().add("card_row");
			
				
			HBox row4 = new HBox();
					
				VBox card6 = new VBox(50);
					
					final ComboBox<String> color_choice6 = new ComboBox<String>(color_options);
					color_choice6.getStyleClass().add("choice");
					color_choice6.setMinWidth(110);
					color_choice6.setPrefWidth(110);
					color_choice6.setMaxWidth(110);
					final ComboBox<String> shape_choice6 = new ComboBox<String>(shape_options);
					shape_choice6.getStyleClass().add("choice");
					shape_choice6.setMinWidth(110);
					shape_choice6.setPrefWidth(110);
					shape_choice6.setMaxWidth(110);
					card6.setPrefHeight(160);
					card6.setMinHeight(160);
					card6.setMaxHeight(160);
					card6.setMinWidth(120);
					card6.setPrefWidth(120);
					card6.setMaxWidth(120);
					card6.setPadding(new Insets(20, 0, 20, 0));
					card6.getStyleClass().add("card");
					card6.getChildren().addAll(color_choice6, shape_choice6);
				
				VBox card7 = new VBox(50);
				
					final ComboBox<String> color_choice7 = new ComboBox<String>(color_options);
					color_choice7.getStyleClass().add("choice");
					color_choice7.setMinWidth(110);
					color_choice7.setPrefWidth(110);
					color_choice7.setMaxWidth(110);
					final ComboBox<String> shape_choice7 = new ComboBox<String>(shape_options);
					shape_choice7.getStyleClass().add("choice");
					shape_choice7.setMinWidth(110);
					shape_choice7.setPrefWidth(110);
					shape_choice7.setMaxWidth(110);
					card7.setPrefHeight(160);
					card7.setMinHeight(160);
					card7.setMaxHeight(160);
					card7.setMinWidth(120);
					card7.setPrefWidth(120);
					card7.setMaxWidth(120);
					card7.setPadding(new Insets(20, 0, 20, 0));
					card7.getStyleClass().add("card");
					card7.getChildren().addAll(color_choice7, shape_choice7);
				
				row4.getChildren().addAll(card6, card7);
				row4.getStyleClass().add("card_row");
			
				
			HBox row5 = new HBox();
				
				Button flip_next = new Button("Next Flip");
				Label game_number = new Label("Game Number: ");
				row5.getChildren().addAll(flip_next, game_number);
			
			game_screen.getChildren().addAll(row1, row2, row3, row4, row5);

			
			
			// ADD EVENT HANDLING
			menu_button.setOnAction(e -> window.setScene(scene1));			
			
			
			
			// DISPLAY RESULTS
			
			// Add the .css file to the scene
			scene1.getStylesheets().add( 
					getClass().getResource("scene1.css").toExternalForm() );
			scene2.getStylesheets().add( 
					getClass().getResource("scene2.css").toExternalForm() );
			
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
