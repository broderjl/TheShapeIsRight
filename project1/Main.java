package project1;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;



public class Main extends Application {
	
	Stage window;
	Scene scene1, scene2;
	static final int MAX_GAMES = 3;
	static int numberOfShapes;
	static int cardsFlipped = 0;
	static int points = 0;
	static int game = 1;
	static ObservableList<String> shape_options = FXCollections.observableArrayList();
	static ObservableList<String> color_options = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			
			
			// main screen (Jack)
			
			Label label1 = new Label("Main Menu:");
			Button play_button = new Button("Play Game");
			play_button.getStyleClass().add("button");
			
			
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
				//System.out.println(numberOfShapes);
			});
			
			// Play button event handling moved to end, since initial game setup occurs
			// as it is clicked, which affects elements created in scene 2.
			

			
			
			// Handling for color selections
			colorSelectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			colorSelectionListView.getSelectionModel().getSelectedItems().addListener(
					new ListChangeListener<String>() {
						@Override
						public void onChanged(ListChangeListener.Change<? extends String> c) {
							System.out.println(colorSelectionListView.getSelectionModel().getSelectedItems().toString());
							color_options.clear();
							color_options.addAll(colorSelectionListView.getSelectionModel().getSelectedItems());
							System.out.println(color_options.toString());
						}
					});
			
			// Handling for shape selection
			shapeSelectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			shapeSelectionListView.getSelectionModel().getSelectedItems().addListener(
					new ListChangeListener<String>() {
						@Override
						public void onChanged(ListChangeListener.Change<? extends String> c) {
							shape_options.clear();
							shape_options.addAll(shapeSelectionListView.getSelectionModel().getSelectedItems());
						}
					});
			
			/*
			 * Adding the nodes to the scene
			 */
			
			VBox main_screen = new VBox();
			main_screen.getStyleClass().add("background");
			main_screen.getChildren().addAll(label1, listViewsHBox, selectionBox, play_button);
			scene1 = new Scene(main_screen, 900, 700);

			
			
			
			
			// -----------------
			// GAME SCREEN - TIM
			// -----------------
			
			
			// SET UP VISUALS
			
			//Jack, you should create these lists based on what's selected in the main menu
//			ObservableList<String> color_options = 
//				    FXCollections.observableArrayList(
//				        "Color 1",
//				        "Color 2",
//				        "Color 3"
//				    );
//			ObservableList<String> shape_options = 
//				    FXCollections.observableArrayList(
//				        "Shape 1",
//				        "Shape 2",
//				        "Shape 3"
//				    );
		
			VBox game_screen = new VBox();
			game_screen.setSpacing(20);
			game_screen.getStyleClass().add("background");
			scene2 = new Scene(game_screen, 900, 700);
			


			BorderPane row1 = new BorderPane();
				Label score = new Label("Score: 0");
					row1.setLeft(score);
					score.setAlignment(Pos.CENTER);
					score.setMinWidth(120);
					score.setPrefWidth(120);
					score.setMaxWidth(120);
					score.setMinHeight(40);
					score.setPrefHeight(40);
					score.setMaxHeight(40);
					score.getStyleClass().add("display_info");
				Label title = new Label("The Shape is Right!");
					title.getStyleClass().add("title");
					row1.setCenter(title);
				Button menu_button = new Button("Main Menu");
					row1.setRight(menu_button);
					menu_button.setMinWidth(120);
					menu_button.setPrefWidth(120);
					menu_button.setMaxWidth(120);
					menu_button.setMinHeight(40);
					menu_button.setPrefHeight(40);
					menu_button.setMaxHeight(40);
					menu_button.getStyleClass().add("black_button");



			HBox row2 = new HBox();
				
				StackPane card1stack = new StackPane();
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
					ImageView back1 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back1.setFitHeight(160);
						back1.setFitWidth(120);
					Rectangle front1 = new Rectangle(0.012, 160);
						front1.getStyleClass().add("front");
				card1stack.getChildren().addAll(back1, card1, front1);
				
				StackPane card2stack = new StackPane();
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
					ImageView back2 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back2.setFitHeight(160);
						back2.setFitWidth(120);
					Rectangle front2 = new Rectangle(0.012, 160);
						front2.getStyleClass().add("front");
				card2stack.getChildren().addAll(back2, card2, front2);

				row2.getChildren().addAll(card1stack, card2stack);
				row2.getStyleClass().add("card_row");
			
			

			HBox row3 = new HBox();
				
				StackPane card3stack = new StackPane();
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
					ImageView back3 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back3.setFitHeight(160);
						back3.setFitWidth(120);
					Rectangle front3 = new Rectangle(0.012, 160);
						front3.getStyleClass().add("front");
				card3stack.getChildren().addAll(back3, card3, front3);
				
				StackPane card4stack = new StackPane();
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
					ImageView back4 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back4.setFitHeight(160);
						back4.setFitWidth(120);
					Rectangle front4 = new Rectangle(0.012, 160);
						front4.getStyleClass().add("front");
				card4stack.getChildren().addAll(back4, card4, front4);
			
				StackPane card5stack = new StackPane();
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
					ImageView back5 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back5.setFitHeight(160);
						back5.setFitWidth(120);
					Rectangle front5 = new Rectangle(0.012, 160);
						front5.getStyleClass().add("front");
				card5stack.getChildren().addAll(back5, card5, front5);
			
				row3.getChildren().addAll(card3stack, card4stack, card5stack);
				row3.getStyleClass().add("card_row");
			
				

			HBox row4 = new HBox();
					
				StackPane card6stack = new StackPane();
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
					ImageView back6 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back6.setFitHeight(160);
						back6.setFitWidth(120);
					Rectangle front6 = new Rectangle(0.012, 160);
						front6.getStyleClass().add("front");
				card6stack.getChildren().addAll(back6, card6, front6);
			
				StackPane card7stack = new StackPane();
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
					ImageView back7 = new ImageView(getClass().getResource("card_back.jpg").toExternalForm());
						back7.setFitHeight(160);
						back7.setFitWidth(120);
					Rectangle front7 = new Rectangle(0.012, 160);
						front7.getStyleClass().add("front");
				card7stack.getChildren().addAll(back7, card7, front7);
				
				row4.getChildren().addAll(card6stack, card7stack);
				row4.getStyleClass().add("card_row");
			
			

			BorderPane row5 = new BorderPane();
				HBox filler = new HBox();
					row5.setLeft(filler);
					filler.setMinWidth(120);
					filler.setPrefWidth(120);
					filler.setMaxWidth(120);
				StackPane game_buttons = new StackPane();
					row5.setCenter(game_buttons);
					Button flip_next = new Button("Next Flip");
						flip_next.setMinWidth(120);
						flip_next.setPrefWidth(120);
						flip_next.setMaxWidth(120);
						flip_next.setMinHeight(40);
						flip_next.setPrefHeight(40);
						flip_next.setMaxHeight(40);
						flip_next.getStyleClass().add("black_button");
					Button next_game = new Button("Next Game");
						next_game.setVisible(false);
						next_game.setMinWidth(120);
						next_game.setPrefWidth(120);
						next_game.setMaxWidth(120);
						next_game.setMinHeight(40);
						next_game.setPrefHeight(40);
						next_game.setMaxHeight(40);
						next_game.getStyleClass().add("black_button");
					Label final_score = new Label("Final Score: 0");
						final_score.getStyleClass().add("title");
						final_score.setVisible(false);
					game_buttons.getChildren().addAll(flip_next, next_game, final_score);
				Label game_number = new Label("Game: 1");
					row5.setRight(game_number);
					game_number.setAlignment(Pos.CENTER);
					game_number.setMinWidth(120);
					game_number.setPrefWidth(120);
					game_number.setMaxWidth(120);
					game_number.setMinHeight(40);
					game_number.setPrefHeight(40);
					game_number.setMaxHeight(40);
					game_number.getStyleClass().add("display_info");

			
			game_screen.getChildren().addAll(row1, row2, row3, row4, row5);

			
			
			// ADD EVENT HANDLING
			
			// go back to main menu
			menu_button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					window.setScene(scene1);
				}
			});
			
			
			
			// set up cards and start game screen
			play_button.setOnAction(new EventHandler<ActionEvent>() {
	            @Override 
	            public void handle(ActionEvent event) {
	                // move to next scene, where game play occurs
	            	window.setScene(scene2);
	            	
	            	// reset all variables, since new game began
	            	cardsFlipped = 0;
	            	points = 0;
	            	score.setText("Points: " + points);
	            	game = 1;
	            	game_number.setText("Game: " + game);
	            	
	            	// undo any previously played animations ("unflip" cards)
            		front1.scaleXProperty().setValue(1);
            		back1.scaleXProperty().setValue(1);
            		front2.scaleXProperty().setValue(1);
            		back2.scaleXProperty().setValue(1);
            		front3.scaleXProperty().setValue(1);
            		back3.scaleXProperty().setValue(1);
            		front4.scaleXProperty().setValue(1);
            		back4.scaleXProperty().setValue(1);
            		front5.scaleXProperty().setValue(1);
            		back5.scaleXProperty().setValue(1);
            		front6.scaleXProperty().setValue(1);
            		back6.scaleXProperty().setValue(1);
            		front7.scaleXProperty().setValue(1);
            		back7.scaleXProperty().setValue(1);
            		
            		//reset all button states as well
            		next_game.setVisible(false);
            		flip_next.setVisible(true);
            		final_score.setVisible(false);
	            	
	            	// adjust the number of visible cards
	            	// as well as the selection options on top
	            	if(numberOfShapes == 3) {
	            		card1stack.setVisible(false);
	            		card2stack.setVisible(false);
	            		card3stack.setVisible(true);
	            		color_choice3.setVisible(true);
	            		shape_choice3.setVisible(true);
	            		card4stack.setVisible(true);
	            		color_choice4.setVisible(true);
	            		shape_choice4.setVisible(true);
	            		card5stack.setVisible(true);
	            		color_choice5.setVisible(true);
	            		shape_choice5.setVisible(true);
	            		card6stack.setVisible(false);
	            		card7stack.setVisible(false);
	            	} else if (numberOfShapes == 5) {
	            		card1stack.setVisible(true);
	            		color_choice1.setVisible(true);
	            		shape_choice1.setVisible(true);
	            		card2stack.setVisible(true);
	            		color_choice2.setVisible(true);
	            		shape_choice2.setVisible(true);
	            		card3stack.setVisible(false);
	            		card4stack.setVisible(true);
	            		color_choice4.setVisible(true);
	            		shape_choice4.setVisible(true);
	            		card5stack.setVisible(false);
	            		card6stack.setVisible(true);
	            		color_choice6.setVisible(true);
	            		shape_choice6.setVisible(true);
	            		card7stack.setVisible(true);
	            		color_choice7.setVisible(true);
	            		shape_choice7.setVisible(true);
	            	} else {
	            		card1stack.setVisible(true);
	            		color_choice1.setVisible(true);
	            		shape_choice1.setVisible(true);
	            		card2stack.setVisible(true);
	            		color_choice2.setVisible(true);
	            		shape_choice2.setVisible(true);
	            		card3stack.setVisible(true);
	            		color_choice3.setVisible(true);
	            		shape_choice3.setVisible(true);
	            		card4stack.setVisible(true);
	            		color_choice4.setVisible(true);
	            		shape_choice4.setVisible(true);
	            		card5stack.setVisible(true);
	            		color_choice5.setVisible(true);
	            		shape_choice5.setVisible(true);
	            		card6stack.setVisible(true);
	            		color_choice6.setVisible(true);
	            		shape_choice6.setVisible(true);
	            		card7stack.setVisible(true);
	            		color_choice7.setVisible(true);
	            		shape_choice7.setVisible(true);
	            	}
	            }
	        });
			
			
			
			// flip cards one at a time
			// 3 cards = card3, card4, card5 displayed
			// 5 cards = card1, card2, card4, card6, card7 displayed
			// 7 cards = all cards displayed
			flip_next.setOnAction(new EventHandler<ActionEvent>() {
	            @Override 
	            public void handle(ActionEvent event) {
	            	
	            	if(cardsFlipped >= numberOfShapes)
	            		return;
	            	
	            	//set up time line and all 21 possible key frames
	    			Timeline timeline = new Timeline();
	            	KeyFrame frame0card1 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back1.scaleXProperty(), 1.0),
	    					new KeyValue(front1.scaleXProperty(), 1.0));
	    			KeyFrame frame1card1 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back1.scaleXProperty(), 0.01),
	    					new KeyValue(front1.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card1 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back1.scaleXProperty(), 0.01),
	    					new KeyValue(front1.scaleXProperty(), 10000.0));
	            	
					KeyFrame frame0card2 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back2.scaleXProperty(), 1.0),
	    					new KeyValue(front2.scaleXProperty(), 1.0));
	    			KeyFrame frame1card2 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back2.scaleXProperty(), 0.01),
	    					new KeyValue(front2.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card2 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back2.scaleXProperty(), 0.01),
	    					new KeyValue(front2.scaleXProperty(), 10000.0));
	            	
					KeyFrame frame0card3 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back3.scaleXProperty(), 1.0),
	    					new KeyValue(front3.scaleXProperty(), 1.0));
	    			KeyFrame frame1card3 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back3.scaleXProperty(), 0.01),
	    					new KeyValue(front3.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card3 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back3.scaleXProperty(), 0.01),
	    					new KeyValue(front3.scaleXProperty(), 10000.0));
	            	
					KeyFrame frame0card4 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back4.scaleXProperty(), 1.0),
	    					new KeyValue(front4.scaleXProperty(), 1.0));
	    			KeyFrame frame1card4 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back4.scaleXProperty(), 0.01),
	    					new KeyValue(front4.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card4 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back4.scaleXProperty(), 0.01),
	    					new KeyValue(front4.scaleXProperty(), 10000.0));
					
					KeyFrame frame0card5 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back5.scaleXProperty(), 1.0),
	    					new KeyValue(front5.scaleXProperty(), 1.0));
	    			KeyFrame frame1card5 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back5.scaleXProperty(), 0.01),
	    					new KeyValue(front5.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card5 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back5.scaleXProperty(), 0.01),
	    					new KeyValue(front5.scaleXProperty(), 10000.0));
					
					KeyFrame frame0card6 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back6.scaleXProperty(), 1.0),
	    					new KeyValue(front6.scaleXProperty(), 1.0));
	    			KeyFrame frame1card6 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back6.scaleXProperty(), 0.01),
	    					new KeyValue(front6.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card6 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back6.scaleXProperty(), 0.01),
	    					new KeyValue(front6.scaleXProperty(), 10000.0));
					
					KeyFrame frame0card7 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back7.scaleXProperty(), 1.0),
	    					new KeyValue(front7.scaleXProperty(), 1.0));
	    			KeyFrame frame1card7 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back7.scaleXProperty(), 0.01),
	    					new KeyValue(front7.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card7 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back7.scaleXProperty(), 0.01),
	    					new KeyValue(front7.scaleXProperty(), 10000.0));
	    			
	            	if(numberOfShapes == 3) {
	            		if(cardsFlipped == 0) {
	    	            	color_choice3.setVisible(false);
	    	            	shape_choice3.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card3);
	    	    			timeline.getKeyFrames().add(frame1card3);
	    	    			timeline.getKeyFrames().add(frame2card3);
	            		}
	            		if(cardsFlipped == 1) {
	    	            	color_choice4.setVisible(false);
	    	            	shape_choice4.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card4);
	    	    			timeline.getKeyFrames().add(frame1card4);
	    	    			timeline.getKeyFrames().add(frame2card4);
	            		}
	            		if(cardsFlipped == 2) {
	            			if(game == MAX_GAMES){
	            				final_score.setVisible(true);
	            				flip_next.setVisible(false);
	            				next_game.setVisible(false);
	            			} else {
		    	            	flip_next.setVisible(false);
		    	            	next_game.setVisible(true);
	            			}
	    	            	color_choice5.setVisible(false);
	    	            	shape_choice5.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card5);
	    	    			timeline.getKeyFrames().add(frame1card5);
	    	    			timeline.getKeyFrames().add(frame2card5);
	            		}
	            	}
	            	
	            	if(numberOfShapes == 5) {
	            		if(cardsFlipped == 0) {
	    	            	color_choice1.setVisible(false);
	    	            	shape_choice1.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card1);
	    	    			timeline.getKeyFrames().add(frame1card1);
	    	    			timeline.getKeyFrames().add(frame2card1);
	            		}
	            		if(cardsFlipped == 1) {
	    	            	color_choice2.setVisible(false);
	    	            	shape_choice2.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card2);
	    	    			timeline.getKeyFrames().add(frame1card2);
	    	    			timeline.getKeyFrames().add(frame2card2);
	            		}
	            		if(cardsFlipped == 2) {
	    	            	color_choice4.setVisible(false);
	    	            	shape_choice4.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card4);
	    	    			timeline.getKeyFrames().add(frame1card4);
	    	    			timeline.getKeyFrames().add(frame2card4);
	            		}
	            		if(cardsFlipped == 3) {
	    	            	color_choice6.setVisible(false);
	    	            	shape_choice6.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card6);
	    	    			timeline.getKeyFrames().add(frame1card6);
	    	    			timeline.getKeyFrames().add(frame2card6);
	            		}
	            		if(cardsFlipped == 4) {
	            			if(game == MAX_GAMES){
	            				final_score.setVisible(true);
	            				flip_next.setVisible(false);
	            				next_game.setVisible(false);
	            			} else {
		    	            	flip_next.setVisible(false);
		    	            	next_game.setVisible(true);
	            			}
	    	            	color_choice7.setVisible(false);
	    	            	shape_choice7.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card7);
	    	    			timeline.getKeyFrames().add(frame1card7);
	    	    			timeline.getKeyFrames().add(frame2card7);
	            		}
	            	}
	            	
	            	if(numberOfShapes == 7) {
	            		if(cardsFlipped == 0) {
	    	            	color_choice1.setVisible(false);
	    	            	shape_choice1.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card1);
	    	    			timeline.getKeyFrames().add(frame1card1);
	    	    			timeline.getKeyFrames().add(frame2card1);
	            		}
	            		if(cardsFlipped == 1) {
	    	            	color_choice2.setVisible(false);
	    	            	shape_choice2.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card2);
	    	    			timeline.getKeyFrames().add(frame1card2);
	    	    			timeline.getKeyFrames().add(frame2card2);
	            		}
	            		if(cardsFlipped == 2) {
	    	            	color_choice3.setVisible(false);
	    	            	shape_choice3.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card3);
	    	    			timeline.getKeyFrames().add(frame1card3);
	    	    			timeline.getKeyFrames().add(frame2card3);
	            		}
	            		if(cardsFlipped == 3) {
	    	            	color_choice4.setVisible(false);
	    	            	shape_choice4.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card4);
	    	    			timeline.getKeyFrames().add(frame1card4);
	    	    			timeline.getKeyFrames().add(frame2card4);
	            		}
	            		if(cardsFlipped == 4) {
	    	            	color_choice5.setVisible(false);
	    	            	shape_choice5.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card5);
	    	    			timeline.getKeyFrames().add(frame1card5);
	    	    			timeline.getKeyFrames().add(frame2card5);
	            		}
	            		if(cardsFlipped == 5) {
	    	            	color_choice6.setVisible(false);
	    	            	shape_choice6.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card6);
	    	    			timeline.getKeyFrames().add(frame1card6);
	    	    			timeline.getKeyFrames().add(frame2card6);
	            		}
	            		if(cardsFlipped == 6) {
	            			if(game == MAX_GAMES){
	            				final_score.setVisible(true);
	            				flip_next.setVisible(false);
	            				next_game.setVisible(false);
	            			} else {
		    	            	flip_next.setVisible(false);
		    	            	next_game.setVisible(true);
	            			}
	    	            	color_choice7.setVisible(false);
	    	            	shape_choice7.setVisible(false);
	    	            	timeline.getKeyFrames().add(frame0card7);
	    	    			timeline.getKeyFrames().add(frame1card7);
	    	    			timeline.getKeyFrames().add(frame2card7);
	            		}
	            	}
	            	
	            	cardsFlipped++;
	    			timeline.play();
	            }
			});
			
			// start a new game
						next_game.setOnAction(new EventHandler<ActionEvent>() {
				            @Override 
				            public void handle(ActionEvent event) {
				      
				            	// new game has begun, but save old score
				            	cardsFlipped = 0;
				            	game++;
				            	game_number.setText("Game: " + game);
				            	
				            	// undo any previously played animations ("unflip" cards)
			            		front1.scaleXProperty().setValue(1);
			            		back1.scaleXProperty().setValue(1);
			            		front2.scaleXProperty().setValue(1);
			            		back2.scaleXProperty().setValue(1);
			            		front3.scaleXProperty().setValue(1);
			            		back3.scaleXProperty().setValue(1);
			            		front4.scaleXProperty().setValue(1);
			            		back4.scaleXProperty().setValue(1);
			            		front5.scaleXProperty().setValue(1);
			            		back5.scaleXProperty().setValue(1);
			            		front6.scaleXProperty().setValue(1);
			            		back6.scaleXProperty().setValue(1);
			            		front7.scaleXProperty().setValue(1);
			            		back7.scaleXProperty().setValue(1);
			            		
				            	
				            	// adjust the number of visible cards
				            	// as well as the selection options on top
				            	if(numberOfShapes == 3) {
				            		card1stack.setVisible(false);
				            		card2stack.setVisible(false);
				            		card3stack.setVisible(true);
				            		color_choice3.setVisible(true);
				            		shape_choice3.setVisible(true);
				            		card4stack.setVisible(true);
				            		color_choice4.setVisible(true);
				            		shape_choice4.setVisible(true);
				            		card5stack.setVisible(true);
				            		color_choice5.setVisible(true);
				            		shape_choice5.setVisible(true);
				            		card6stack.setVisible(false);
				            		card7stack.setVisible(false);
				            	} else if (numberOfShapes == 5) {
				            		card1stack.setVisible(true);
				            		color_choice1.setVisible(true);
				            		shape_choice1.setVisible(true);
				            		card2stack.setVisible(true);
				            		color_choice2.setVisible(true);
				            		shape_choice2.setVisible(true);
				            		card3stack.setVisible(false);
				            		card4stack.setVisible(true);
				            		color_choice4.setVisible(true);
				            		shape_choice4.setVisible(true);
				            		card5stack.setVisible(false);
				            		card6stack.setVisible(true);
				            		color_choice6.setVisible(true);
				            		shape_choice6.setVisible(true);
				            		card7stack.setVisible(true);
				            		color_choice7.setVisible(true);
				            		shape_choice7.setVisible(true);
				            	} else {
				            		card1stack.setVisible(true);
				            		color_choice1.setVisible(true);
				            		shape_choice1.setVisible(true);
				            		card2stack.setVisible(true);
				            		color_choice2.setVisible(true);
				            		shape_choice2.setVisible(true);
				            		card3stack.setVisible(true);
				            		color_choice3.setVisible(true);
				            		shape_choice3.setVisible(true);
				            		card4stack.setVisible(true);
				            		color_choice4.setVisible(true);
				            		shape_choice4.setVisible(true);
				            		card5stack.setVisible(true);
				            		color_choice5.setVisible(true);
				            		shape_choice5.setVisible(true);
				            		card6stack.setVisible(true);
				            		color_choice6.setVisible(true);
				            		shape_choice6.setVisible(true);
				            		card7stack.setVisible(true);
				            		color_choice7.setVisible(true);
				            		shape_choice7.setVisible(true);
				            	}
				            	
				            	// new game began, display next_flip button
				            	flip_next.setVisible(true);
				            	next_game.setVisible(false);
				            }
				        });
			
			
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
