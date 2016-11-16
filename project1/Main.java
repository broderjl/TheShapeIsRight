package project1;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
	static int numberOfShapes = -1;
	static int cardsFlipped = 0;
	static int points = 0;
	static int game = 1;
	static ObservableList<String> shape_options = FXCollections.observableArrayList();
	static ObservableList<String> color_options = FXCollections.observableArrayList();
	Double[] x = {0.0, 276.0, 546.0, 141.0, 411.0, 681.0, 276.0, 546.0}; // x coordinates of center of shape (not correct yet)
	Double[] y = {0.0, 115.0, 115.0, 320.0, 320.0, 320.0, 515.0, 515.0}; // y coordinates of center of shape (not correct yet)
	Timeline timeline = new Timeline();
	String[] actual_color = {"", "", "", "", "", "", "", ""};
	String[] actual_shape = {"", "", "", "", "", "", "", ""};
	Shape[] shapes = new Shape[8];
	Shape[] possible_shapes = new Shape[8];
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			
			
			// main screen (Jack)
			
			Label label1 = new Label("The Shape Is Right!");
			label1.getStyleClass().add("title");
			
			Button play_button = new Button("Play Game");
			play_button.getStyleClass().add("button");
			
			
			
			/*
			 * Drop down menu for selecting an N
			 * at least 3 and at most	 9
			 */
			
			// Label for selection
			VBox selectionBox = new VBox();
			
				selectionBox.setAlignment(Pos.CENTER);
				selectionBox.setSpacing(2);
			
				Label selectionLabel = new Label("Number of Shapes You Would Like To Play With:");
				selectionLabel.getStyleClass().add("label");

				// Observable list of possible side number selections
				ObservableList<String> numberOfShapesList =
						FXCollections.observableArrayList(
								"3", "5", "7");
				
				// Create ComboBox for selecting the number of shapes
				ComboBox<String> numberOfShapesBox = new ComboBox<String>(numberOfShapesList);		
				selectionBox.getChildren().addAll(selectionLabel, numberOfShapesBox);
			
			
			
			/*
			 * ListView for selecting colors and shapes to 
			 * be used for the playing of the game 
			 */
			// HBox to hold the list views 
			HBox listViewsHBox = new HBox();
			
				// Observable list and list view for selecting colors
				ObservableList<String> colorSelectionList = FXCollections.observableArrayList(
						"Red", "Orange", "Yellow", "Green", "Blue", "Purple");
				ListView<String> colorSelectionListView = new ListView<String>(colorSelectionList);
				colorSelectionListView.setPrefHeight(155);
				colorSelectionListView.setPrefWidth(100);
			
				// Observable list and list view for selecting shapes
				ObservableList<String> shapeSelectionList = FXCollections.observableArrayList(
					"Circle", "Triangle", "Square", "Rectangle", "Pentagon", "Hexagon");
				ListView<String> shapeSelectionListView = new ListView<String>(shapeSelectionList);
				shapeSelectionListView.setPrefHeight(155);
				shapeSelectionListView.setPrefWidth(100);
			
				listViewsHBox.getChildren().add(colorSelectionListView);
				listViewsHBox.getChildren().add(shapeSelectionListView);
				listViewsHBox.getStyleClass().add("listViewsHBox");
				
			Label badInputLabel = new Label("Please complete your selections");
			badInputLabel.getStyleClass().add("badInput");
			badInputLabel.setStyle("-fx-text-fill: red");
			badInputLabel.setVisible(true);
			

			
			
			
			
			/*
			 * Event Handling
			 */
			
			// Handling for the number of shapes selection
			numberOfShapesBox.setOnAction((event) -> {
				String selectedNumber = numberOfShapesBox.getSelectionModel().getSelectedItem();
				numberOfShapes = Integer.parseInt(selectedNumber);
				if (numberOfShapes == -1 || color_options.size() == 0 || shape_options.size() == 0)
				{
					badInputLabel.setVisible(true);
				}
				else
					badInputLabel.setVisible(false);
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
							color_options.clear();
							color_options.addAll(colorSelectionListView.getSelectionModel().getSelectedItems());
							if (numberOfShapes == -1 || color_options.size() == 0 || shape_options.size() == 0)
							{
								badInputLabel.setVisible(true);
							}
							else
								badInputLabel.setVisible(false);
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
							if (numberOfShapes == -1 || color_options.size() == 0 || shape_options.size() == 0)
							{
								badInputLabel.setVisible(true);
							}
							else
								badInputLabel.setVisible(false);
						}
					});
			
			/*
			 * Adding the nodes to the scene
			 */
			
			VBox main_screen = new VBox();
			
			main_screen.setAlignment(Pos.CENTER);
			main_screen.setSpacing(30);
			main_screen.getStyleClass().add("background");
			main_screen.getChildren().addAll(label1, listViewsHBox, selectionBox, play_button, badInputLabel);
			scene1 = new Scene(main_screen, 1020, 700);

			
			
			
			
			// -----------------
			// GAME SCREEN - TIM
			// -----------------
			
			
			// SET UP VISUALS
		
			Pane layers = new Pane();
			VBox game_screen = new VBox();
			HBox full_screen = new HBox();
			game_screen.setSpacing(20);
			game_screen.setMinWidth(900);
			game_screen.setPrefWidth(900);
			game_screen.setMaxWidth(900);
			game_screen.setMinHeight(700);
			game_screen.setPrefHeight(700);
			game_screen.setMaxHeight(700);
			scene2 = new Scene(layers, 1000, 700);
			
			VBox shapes_to_guess = new VBox();
				shapes_to_guess.setAlignment(Pos.CENTER);
				shapes_to_guess.setSpacing(10);
				shapes_to_guess.setPadding(new Insets(10, 10, 10, 10));
				Label shapesToGuessLabel = new Label("Possible\n Shapes");
				shapesToGuessLabel.getStyleClass().add("label");
				shapes_to_guess.getChildren().add(shapesToGuessLabel);
				
			full_screen.getChildren().addAll(game_screen, shapes_to_guess);
			full_screen.getStyleClass().add("background");
			

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
			layers.getChildren().addAll(full_screen);
			
			
			// ADD EVENT HANDLING
			
			// go back to main menu
			menu_button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					timeline.getKeyFrames().clear();
					layers.getChildren().removeAll(shapes);
					timeline.stop();
					cardsFlipped = 0;
					shapes_to_guess.getChildren().clear();
					for(int i = 0; i < 8; i++)
					{
						shapes[i] = null;
						possible_shapes[i] = null;
					}
					points = 0;
					window.setScene(scene1);
				}
			});
			
			
			
			// set up cards and start game screen
			play_button.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					if (numberOfShapes == -1 || color_options.size() == 0 || shape_options.size() == 0)
					{
						badInputLabel.setVisible(true);
					}
					else{
						badInputLabel.setVisible(false);

						// move to next scene, where game play occurs
						window.setScene(scene2);

						// "deal out the cards"
						for(int i = 0; i < 8; i++)
						{
							actual_color[i] = color_options.get((int)(Math.random() * color_options.size()) );
						}

						for(int i = 0; i < 8; i++)
						{
							actual_shape[i] = shape_options.get((int)(Math.random() * shape_options.size()) );
						}



						for(int i = 1; i < 8; i++)
						{
							String shape = actual_shape[i];
							String color = actual_color[i];

							switch (shape.toLowerCase()) {
							case "circle":
								shapes[i] = new Circle();
								((Circle) shapes[i]).setRadius(40);
								possible_shapes[i] = new Circle();
								((Circle) possible_shapes[i]).setRadius(40);
								break;

							case "triangle":
								shapes[i] = new Polygon();
								((Polygon) shapes[i]).getPoints().addAll(new Double[]{
										-40.0, 0.0,
									    40.0, 0.0,
									    0.0, -70.0 });
								possible_shapes[i] = new Polygon();
								((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
										-40.0, 0.0,
									    40.0, 0.0,
									    0.0, -70.0 });
								break;

							case "square":
								shapes[i] = new Polygon();
								((Polygon) shapes[i]).getPoints().addAll(new Double[]{
										-40.0, 40.0,
									    40.0, 40.0,
									    40.0, -40.0,
									    -40.0, -40.0,});
								possible_shapes[i] = new Polygon();
								((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
										-40.0, 40.0,
									    40.0, 40.0,
									    40.0, -40.0,
									    -40.0, -40.0,});
								break;

							case "rectangle":
								shapes[i] = new Polygon();
								((Polygon) shapes[i]).getPoints().addAll(new Double[]{
										40.0, 25.0,
									    40.0, -25.0,
									    -40.0, -25.0,
									    -40.0, 25.0,});
								possible_shapes[i] = new Polygon();
								((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
										40.0, 25.0,
									    40.0, -25.0,
									    -40.0, -25.0,
									    -40.0, 25.0,});
								break;

							case "pentagon":
								shapes[i] = new Polygon();
								((Polygon) shapes[i]).getPoints().addAll(new Double[]{
									    0.0, -40.0,
									    -38.0, -12.0,
									    -24.0, 32.0,
									    24.0, 32.0,
									    38.0, -12.0});
								possible_shapes[i] = new Polygon();
								((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
									    0.0, -40.0,
									    -38.0, -12.0,
									    -24.0, 32.0,
									    24.0, 32.0,
									    38.0, -12.0});
								break;

							case "hexagon":
								shapes[i] = new Polygon();
								((Polygon) shapes[i]).getPoints().addAll(new Double[]{
										40.0, 0.0,
									    20.0, -34.641,
									    -20.0, -34.641,
									    -40.0, 0.0,
									    -20.0, 34.641,
									    20.0, 34.641});
								possible_shapes[i] = new Polygon();
								((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
										40.0, 0.0,
									    20.0, -34.641,
									    -20.0, -34.641,
									    -40.0, 0.0,
									    -20.0, 34.641,
									    20.0, 34.641});
								break;
							}
							
							//set shape color
							switch (color.toLowerCase()) {
							case "red":
								shapes[i].setFill(Color.RED);
								possible_shapes[i].setFill(Color.RED);
								break;

							case "orange":
								shapes[i].setFill(Color.ORANGE);
								possible_shapes[i].setFill(Color.ORANGE);
								break;

							case "yellow":
								shapes[i].setFill(Color.YELLOW);
								possible_shapes[i].setFill(Color.YELLOW);
								break;

							case "green":
								shapes[i].setFill(Color.GREEN);
								possible_shapes[i].setFill(Color.GREEN);
								break;

							case "blue":
								shapes[i].setFill(Color.BLUE);
								possible_shapes[i].setFill(Color.BLUE);
								break;

							case "purple":
								shapes[i].setFill(Color.PURPLE);
								possible_shapes[i].setFill(Color.PURPLE);
								break;
							}
							
							// set shape location
							shapes[i].relocate(x[i], y[i]);
							possible_shapes[i].relocate(x[i],y[i]);
							
							layers.getChildren().add(shapes[i]);
							
							System.out.println(shapes[i]);
						}

						// reset all variables, since new game began
						cardsFlipped = 0;
						points = 0;
						score.setText("Points: " + points);
						game = 1;
						game_number.setText("Game: " + game);

						// undo any previously played animations ("unflip" cards)
						front1.scaleXProperty().setValue(1);
						back1.scaleXProperty().setValue(1);
						shapes[1].scaleXProperty().setValue(.00001);
						front2.scaleXProperty().setValue(1);
						back2.scaleXProperty().setValue(1);
						shapes[2].scaleXProperty().setValue(.00001);
						front3.scaleXProperty().setValue(1);
						back3.scaleXProperty().setValue(1);
						shapes[3].scaleXProperty().setValue(.00001);
						front4.scaleXProperty().setValue(1);
						back4.scaleXProperty().setValue(1);
						shapes[4].scaleXProperty().setValue(.00001);
						front5.scaleXProperty().setValue(1);
						back5.scaleXProperty().setValue(1);
						shapes[5].scaleXProperty().setValue(.00001);
						front6.scaleXProperty().setValue(1);
						back6.scaleXProperty().setValue(1);
						shapes[6].scaleXProperty().setValue(.00001);
						front7.scaleXProperty().setValue(1);
						back7.scaleXProperty().setValue(1);
						shapes[7].scaleXProperty().setValue(.00001);

						//reset all button states as well
						next_game.setVisible(false);
						flip_next.setVisible(true);
						final_score.setVisible(false);

						// adjust the number of visible cards
						// as well as the selection options on top
						if(numberOfShapes == 3) {
							int[] placed = {0, 0, 0, 0, 0, 0, 0, 0};
							int j;
							for(int i = 3; i < 6; i++)
							{
								do {
									j = (int) (3 * Math.random() + 3);
									System.out.println(j);
								} while ( placed[j] == 1 );
								shapes_to_guess.getChildren().add(possible_shapes[j]);
								placed[j] = 1;
								System.out.println(placed.toString());
							}
								
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
							int[] placed = {0, 0, 0, 0, 0, 0, 0, 0};
							for(int i = 0; i < 8; i++)
								System.out.print(placed[i] + " ");
		            		int j = 0;
		            		for(int i = 0; i < 5; i++)
		            		{
		            			boolean flag = true;
		            			while (flag)
		            			{
		            				j = (int)(7 * Math.random() + 1);
		            				System.out.println(j);
		            				if(placed[j] == 0 && ((j >= 1 && j < 3) || (j >= 6 && j < 8) || (j == 4)))
		            					flag = false;
		            			}
		            			placed[j] = 1;
								shapes_to_guess.getChildren().add(possible_shapes[j]);
		            		}
							
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
							int[] placed = {0, 0, 0, 0, 0, 0, 0, 0};
							for(int i = 1; i < 8; i++)
							{
								int j = 0;
								boolean flag = true;
								while (flag)
								{
									j = (int)(7 * Math.random() + 1);
									if( placed[j] == 0)
										flag = false;
								}
								placed[j] = 1;
								shapes_to_guess.getChildren().add(possible_shapes[j]);
							}
								
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
	            	timeline.getKeyFrames().clear();
	            	System.out.println(timeline.getKeyFrames().toString());
	            	
	            	//set up time line and all 21 possible key frames
	            	KeyFrame frame0card1 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back1.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[1].scaleXProperty(), 0.00001),
	    					new KeyValue(front1.scaleXProperty(), 1.0));
	    			KeyFrame frame1card1 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back1.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[1].scaleXProperty(), 0.00001),
	    					new KeyValue(front1.scaleXProperty(), 1.0));
	    			KeyFrame frame2card1 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back1.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[1].scaleXProperty(), 1.0),
	    					new KeyValue(front1.scaleXProperty(), 10000.0));
	            	
					KeyFrame frame0card2 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back2.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[2].scaleXProperty(), 0.00001),
	    					new KeyValue(front2.scaleXProperty(), 1.0));
	    			KeyFrame frame1card2 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back2.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[2].scaleXProperty(), 0.00001),
	    					new KeyValue(front2.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card2 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back2.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[2].scaleXProperty(), 1.0),
	    					new KeyValue(front2.scaleXProperty(), 10000.0));
	            	
					KeyFrame frame0card3 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back3.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[3].scaleXProperty(), 0.00001),
	    					new KeyValue(front3.scaleXProperty(), 1.0));
	    			KeyFrame frame1card3 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back3.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[3].scaleXProperty(), 0.00001),
	    					new KeyValue(front3.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card3 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back3.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[3].scaleXProperty(), 1.0),
	    					new KeyValue(front3.scaleXProperty(), 10000.0));
	            	
					KeyFrame frame0card4 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back4.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[4].scaleXProperty(), 0.00001),
	    					new KeyValue(front4.scaleXProperty(), 1.0));
	    			KeyFrame frame1card4 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back4.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[4].scaleXProperty(), 0.00001),
	    					new KeyValue(front4.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card4 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back4.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[4].scaleXProperty(), 1.0),
	    					new KeyValue(front4.scaleXProperty(), 10000.0));
					
					KeyFrame frame0card5 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back5.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[5].scaleXProperty(), 0.00001),
	    					new KeyValue(front5.scaleXProperty(), 1.0));
	    			KeyFrame frame1card5 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back5.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[5].scaleXProperty(), 0.00001),
	    					new KeyValue(front5.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card5 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back5.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[5].scaleXProperty(), 1.0),
	    					new KeyValue(front5.scaleXProperty(), 10000.0));
					
					KeyFrame frame0card6 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back6.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[6].scaleXProperty(), 0.00001),
	    					new KeyValue(front6.scaleXProperty(), 1.0));
	    			KeyFrame frame1card6 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back6.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[6].scaleXProperty(), 0.00001),
	    					new KeyValue(front6.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card6 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back6.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[6].scaleXProperty(), 1.0),
	    					new KeyValue(front6.scaleXProperty(), 10000.0));
					
					KeyFrame frame0card7 = new KeyFrame(
	    					Duration.ZERO,
	    					new KeyValue(back7.scaleXProperty(), 1.0),
	    					new KeyValue(shapes[7].scaleXProperty(), 0.00001),
	    					new KeyValue(front7.scaleXProperty(), 1.0));
	    			KeyFrame frame1card7 = new KeyFrame(
	    					new Duration(500.0),
	    					new KeyValue(back7.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[7].scaleXProperty(), 0.00001),
	    					new KeyValue(front7.scaleXProperty(), 1.0));		
	    			KeyFrame frame2card7 = new KeyFrame(
	    					new Duration(1000.0),
	    					new KeyValue(back7.scaleXProperty(), 0.01),
	    					new KeyValue(shapes[7].scaleXProperty(), 1.0),
	    					new KeyValue(front7.scaleXProperty(), 10000.0));
	    			
	            	if(numberOfShapes == 3) {
	            		if(cardsFlipped == 0) {
	    	            	if(		actual_color[3] == color_choice3.getValue()
	    	            		&& 	actual_shape[3] == shape_choice3.getValue())
	    	            		points++;
	    	            	
	            			color_choice3.setVisible(false);
	    	            	shape_choice3.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card3);
	    	    			timeline.getKeyFrames().add(frame1card3);
	    	    			timeline.getKeyFrames().add(frame2card3);
	            		}
	            		if(cardsFlipped == 1) {
	    	            	if(		actual_color[4] == color_choice4.getValue()
		    	            		&& 	actual_shape[4] == shape_choice4.getValue())
		    	            		points++;
	            			
	    	            	color_choice4.setVisible(false);
	    	            	shape_choice4.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card4);
	    	    			timeline.getKeyFrames().add(frame1card4);
	    	    			timeline.getKeyFrames().add(frame2card4);
	            		}
	            		if(cardsFlipped == 2) {
	    	            	if(		actual_color[5] == color_choice5.getValue()
		    	            		&& 	actual_shape[5] == shape_choice5.getValue())
		    	            		points++;
	    	            	
	            			if(game == MAX_GAMES){
	            				final_score.setText("Final Score: " + points);
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
	    	            	if(		actual_color[1] == color_choice1.getValue()
		    	            		&& 	actual_shape[1] == shape_choice1.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice1.setVisible(false);
	    	            	shape_choice1.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card1);
	    	    			timeline.getKeyFrames().add(frame1card1);
	    	    			timeline.getKeyFrames().add(frame2card1);
	            		}
	            		if(cardsFlipped == 1) {
	    	            	if(		actual_color[2] == color_choice2.getValue()
		    	            		&& 	actual_shape[2] == shape_choice2.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice2.setVisible(false);
	    	            	shape_choice2.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card2);
	    	    			timeline.getKeyFrames().add(frame1card2);
	    	    			timeline.getKeyFrames().add(frame2card2);
	            		}
	            		if(cardsFlipped == 2) {
	    	            	if(		actual_color[4] == color_choice4.getValue()
		    	            		&& 	actual_shape[4] == shape_choice4.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice4.setVisible(false);
	    	            	shape_choice4.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card4);
	    	    			timeline.getKeyFrames().add(frame1card4);
	    	    			timeline.getKeyFrames().add(frame2card4);
	            		}
	            		if(cardsFlipped == 3) {
	    	            	if(		actual_color[6] == color_choice6.getValue()
		    	            		&& 	actual_shape[6] == shape_choice6.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice6.setVisible(false);
	    	            	shape_choice6.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card6);
	    	    			timeline.getKeyFrames().add(frame1card6);
	    	    			timeline.getKeyFrames().add(frame2card6);
	            		}
	            		if(cardsFlipped == 4) {
	    	            	if(		actual_color[7] == color_choice7.getValue()
		    	            		&& 	actual_shape[7] == shape_choice7.getValue())
		    	            		points++;
	    	            	
	            			if(game == MAX_GAMES){
	            				final_score.setText("Final Score: " + points);
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
	    	            	if(		actual_color[1] == color_choice1.getValue()
		    	            		&& 	actual_shape[1] == shape_choice1.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice1.setVisible(false);
	    	            	shape_choice1.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card1);
	    	    			timeline.getKeyFrames().add(frame1card1);
	    	    			timeline.getKeyFrames().add(frame2card1);
	            		}
	            		if(cardsFlipped == 1) {

		    	            	if(		actual_color[2] == color_choice2.getValue()
			    	            		&& 	actual_shape[2] == shape_choice2.getValue())
			    	            		points++;
		    	            	
	    	            	color_choice2.setVisible(false);
	    	            	shape_choice2.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card2);
	    	    			timeline.getKeyFrames().add(frame1card2);
	    	    			timeline.getKeyFrames().add(frame2card2);
	            		}
	            		if(cardsFlipped == 2) {
	    	            	if(		actual_color[3] == color_choice3.getValue()
		    	            		&& 	actual_shape[3] == shape_choice3.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice3.setVisible(false);
	    	            	shape_choice3.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card3);
	    	    			timeline.getKeyFrames().add(frame1card3);
	    	    			timeline.getKeyFrames().add(frame2card3);
	            		}
	            		if(cardsFlipped == 3) {
	    	            	if(		actual_color[4] == color_choice4.getValue()
		    	            		&& 	actual_shape[4] == shape_choice4.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice4.setVisible(false);
	    	            	shape_choice4.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card4);
	    	    			timeline.getKeyFrames().add(frame1card4);
	    	    			timeline.getKeyFrames().add(frame2card4);
	            		}
	            		if(cardsFlipped == 4) {
	    	            	if(		actual_color[5] == color_choice5.getValue()
		    	            		&& 	actual_shape[5] == shape_choice5.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice5.setVisible(false);
	    	            	shape_choice5.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card5);
	    	    			timeline.getKeyFrames().add(frame1card5);
	    	    			timeline.getKeyFrames().add(frame2card5);
	            		}
	            		if(cardsFlipped == 5) {
	    	            	if(		actual_color[6] == color_choice6.getValue()
		    	            		&& 	actual_shape[6] == shape_choice6.getValue())
		    	            		points++;
	    	            	
	    	            	color_choice6.setVisible(false);
	    	            	shape_choice6.setVisible(false);
	    	    			timeline.getKeyFrames().add(frame0card6);
	    	    			timeline.getKeyFrames().add(frame1card6);
	    	    			timeline.getKeyFrames().add(frame2card6);
	            		}
	            		if(cardsFlipped == 6) {
	    	            	if(		actual_color[7] == color_choice7.getValue()
		    	            		&& 	actual_shape[7] == shape_choice7.getValue())
		    	            		points++;
	    	            	
	            			if(game == MAX_GAMES){
	            				final_score.setText("Final Score: " + points);
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
	            	
					score.setText("Points: " + points);
	            	cardsFlipped++;
	    			timeline.play();
	            }
			});
			
			// start a new game
						next_game.setOnAction(new EventHandler<ActionEvent>() {
				            @Override 
				            public void handle(ActionEvent event) {

									// move to next scene, where game play occurs
				            		window.setScene(scene2);
				            		layers.getChildren().removeAll(shapes);
				            		timeline.getKeyFrames().clear();
				            		timeline.stop();
				            		shapes_to_guess.getChildren().clear();
				            		for(int i = 0; i < shapes.length; i++)
				            			shapes[i]  = null;
									System.out.println(timeline.getKeyFrames().toString());
//									timeline.getKeyFrames().clear();
//									System.out.println(timeline.getKeyFrames());
									// "deal out the cards"
									for(int i = 0; i < 8; i++)
									{
										actual_color[i] = color_options.get((int)(Math.random() * color_options.size()) );
										System.out.println(i + " " + actual_color[i]);
									}

									for(int i = 0; i < 8; i++)
									{
										actual_shape[i] = shape_options.get((int)(Math.random() * shape_options.size()) );
										System.out.println(i + " " + actual_shape[i]);
									}

									shapes_to_guess.getChildren().add(shapesToGuessLabel);

									for(int i = 1; i < 8; i++)
									{
										String shape = actual_shape[i];
										String color = actual_color[i];

										switch (shape.toLowerCase()) {
										case "circle":
											shapes[i] = new Circle();
											((Circle) shapes[i]).setRadius(40);
											((Circle) shapes[i]).scaleXProperty().setValue(0.00001);
											possible_shapes[i] = new Circle();
											((Circle) possible_shapes[i]).setRadius(40);
											break;

										case "triangle":
											shapes[i] = new Polygon();
											((Polygon) shapes[i]).getPoints().addAll(new Double[]{
													-40.0, 0.0,
												    40.0, 0.0,
												    0.0, -70.0 });
											((Polygon) shapes[i]).scaleXProperty().setValue(0.00001);
											possible_shapes[i] = new Polygon();
											((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
													-40.0, 0.0,
												    40.0, 0.0,
												    0.0, -70.0 });
											break;

										case "square":
											shapes[i] = new Polygon();
											((Polygon) shapes[i]).getPoints().addAll(new Double[]{
													-40.0, 40.0,
												    40.0, 40.0,
												    40.0, -40.0,
												    -40.0, -40.0,});
											((Polygon) shapes[i]).scaleXProperty().setValue(0.00001);
											possible_shapes[i] = new Polygon();
											((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
													-40.0, 40.0,
												    40.0, 40.0,
												    40.0, -40.0,
												    -40.0, -40.0,});
											break;

										case "rectangle":
											shapes[i] = new Polygon();
											((Polygon) shapes[i]).getPoints().addAll(new Double[]{
													40.0, 25.0,
												    40.0, -25.0,
												    -40.0, -25.0,
												    -40.0, 25.0,});
											((Polygon) shapes[i]).scaleXProperty().setValue(0.00001);
											possible_shapes[i] = new Polygon();
											((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
													40.0, 25.0,
												    40.0, -25.0,
												    -40.0, -25.0,
												    -40.0, 25.0,});
											break;

										case "pentagon":
											shapes[i] = new Polygon();
											((Polygon) shapes[i]).getPoints().addAll(new Double[]{
												    0.0, -40.0,
												    -38.0, -12.0,
												    -24.0, 32.0,
												    24.0, 32.0,
												    38.0, -12.0});
											((Polygon) shapes[i]).scaleXProperty().setValue(0.00001);
											possible_shapes[i] = new Polygon();
											((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
												    0.0, -40.0,
												    -38.0, -12.0,
												    -24.0, 32.0,
												    24.0, 32.0,
												    38.0, -12.0});
											break;

										case "hexagon":
											shapes[i] = new Polygon();
											((Polygon) shapes[i]).getPoints().addAll(new Double[]{
													40.0, 0.0,
												    20.0, -34.641,
												    -20.0, -34.641,
												    -40.0, 0.0,
												    -20.0, 34.641,
												    20.0, 34.641});
											((Polygon) shapes[i]).scaleXProperty().setValue(0.00001);
											possible_shapes[i] = new Polygon();
											((Polygon) possible_shapes[i]).getPoints().addAll(new Double[]{
													40.0, 0.0,
												    20.0, -34.641,
												    -20.0, -34.641,
												    -40.0, 0.0,
												    -20.0, 34.641,
												    20.0, 34.641});
											break;
										}
										
										//set shape color
										switch (color.toLowerCase()) {
										case "red":
											shapes[i].setFill(Color.RED);
											possible_shapes[i].setFill(Color.RED);
											break;

										case "orange":
											shapes[i].setFill(Color.ORANGE);
											possible_shapes[i].setFill(Color.ORANGE);
											break;

										case "yellow":
											shapes[i].setFill(Color.YELLOW);
											possible_shapes[i].setFill(Color.YELLOW);
											break;

										case "green":
											shapes[i].setFill(Color.GREEN);
											possible_shapes[i].setFill(Color.GREEN);
											break;

										case "blue":
											shapes[i].setFill(Color.BLUE);
											possible_shapes[i].setFill(Color.BLUE);
											break;

										case "purple":
											shapes[i].setFill(Color.PURPLE);
											possible_shapes[i].setFill(Color.PURPLE);
											break;
										}
										
										// set shape location
										shapes[i].relocate(x[i], y[i]);
										possible_shapes[i].relocate(x[i], y[i]);
										
										layers.getChildren().add(shapes[i]);
										
										//System.out.println(shapes[i]);
									}
				            

				            	
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
									int[] placed = {0, 0, 0, 0, 0, 0, 0, 0};
									int j;
									for(int i = 3; i < 6; i++)
									{
										do {
											j = (int) (3 * Math.random() + 3);
											System.out.println(j);
										} while ( placed[j] == 1 );
										shapes_to_guess.getChildren().add(possible_shapes[j]);
										placed[j] = 1;
									}
										
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
									int[] placed = {0, 0, 0, 0, 0, 0, 0, 0};
									for(int i = 0; i < 8; i++)
										System.out.print(placed[i] + " ");
				            		int j = 0;
				            		for(int i = 0; i < 5; i++)
				            		{
				            			boolean flag = true;
				            			while (flag)
				            			{
				            				j = (int)(7 * Math.random() + 1);
				            				System.out.println(j);
				            				if(placed[j] == 0 && ((j >= 1 && j < 3) || (j >= 6 && j < 8) || (j == 4)))
				            					flag = false;
				            			}
				            			placed[j] = 1;
										shapes_to_guess.getChildren().add(possible_shapes[j]);
				            		}
									
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
									int[] placed = {0, 0, 0, 0, 0, 0, 0, 0};
									for(int i = 1; i < 8; i++)
									{
										int j = 0;
										boolean flag = true;
										while (flag)
										{
											j = (int)(7 * Math.random() + 1);
											if( placed[j] == 0)
												flag = false;
										}
										placed[j] = 1;
										shapes_to_guess.getChildren().add(possible_shapes[j]);
									}
									
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
			

			full_screen.getStylesheets().add("background");
			
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
