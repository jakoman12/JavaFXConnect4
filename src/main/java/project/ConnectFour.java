package project;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class ConnectFour extends Application {

    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private Button c1,c2,c3,c4,c5,c6; //game button for columns
    private List<Button> buttonsList; //to store button columns
    private Circle[][] circleBoard = new Circle[ROWS][COLUMNS]; //UI board for circles
    private int[][] gameBoard = new int[ROWS][COLUMNS]; //game board for logic
    private int currentPlayer = 1; //1 for Red; 2 for Yellow
    private Pane buttonsPane;
    private Label turnStatus; 
    private GridPane grid;
    private static BorderPane borderPane; //main page container
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //initialize the main "page"
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10,0,10,0));

        //Top: buttons
        buttonsList = List.of(createButton(0),createButton(1),createButton(2),
                                createButton(3),createButton(4),createButton(5));
        buttonsPane = new Pane();
        buttonsPane.getChildren().addAll(buttonsList);
        
        borderPane.setTop(buttonsPane);
        BorderPane.setAlignment(buttonsPane, Pos.CENTER);
        

        //Center: grid
        grid = new GridPane(15,5);
        grid.setAlignment(Pos.CENTER);
        //add circles
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                Circle circle = new Circle(35, Color.LIGHTGRAY);
                circleBoard[row][col] = circle;
                grid.add(circle, row, col);
            }
        }
        borderPane.setCenter(grid);
        BorderPane.setAlignment(grid, Pos.CENTER);

        //Bottom: turn status
        turnStatus = new Label("It is Player One's turn");
        borderPane.setBottom(turnStatus);
        BorderPane.setAlignment(turnStatus, Pos.CENTER);

        scene = new Scene(borderPane, 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    private Button createButton(int col){
        //make a circular button and define the xyposition
        Button button = new Button();
        button.setShape(new Circle(5));
        button.setMinSize(10, 10);
        button.setPrefSize(20, 20);
        button.setLayoutX(125 + 85*col);
        button.setLayoutY(25);
        button.setUserData(col); //to get colID without displaying on the button
        button.setOnAction(e -> dropPiece(button));
        return button;
    }

    private void dropPiece(Button button){
        
    }

    public static void main(String[] args) {
        launch();
    }

}