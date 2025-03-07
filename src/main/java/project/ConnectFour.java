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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class ConnectFour extends Application {

    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private List<Button> buttonsList; //to store button columns
    private Circle[][] circleBoard = new Circle[ROWS][COLUMNS]; //UI board for circles
    private int[][] gameBoard = new int[ROWS][COLUMNS]; //game board for logic
    private boolean gameOver = false;
    private Button playAgainButton;
    private int currentPlayer = 1; //1 for Red; 2 for Yellow
    private Color pieceColor = Color.INDIANRED;
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

        //TOP: buttons
        buttonsList = List.of(createButton(0),createButton(1),createButton(2),
                                createButton(3),createButton(4),createButton(5));
        buttonsPane = new Pane();
        buttonsPane.getChildren().addAll(buttonsList);
        
        borderPane.setTop(buttonsPane);
        BorderPane.setAlignment(buttonsPane, Pos.CENTER);
        

        //CENTER: grid of circles
        grid = new GridPane(15,5);
        grid.setAlignment(Pos.CENTER);
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                Circle circle = new Circle(35, Color.LIGHTGRAY);
                circleBoard[row][col] = circle;
                grid.add(circleBoard[row][col], col, row);
            }
        }
        borderPane.setCenter(grid);
        BorderPane.setAlignment(grid, Pos.CENTER);

        //BOTTOM: turn status
        turnStatus = new Label("It is Player One's turn");
        turnStatus.setFont(new Font(24));
        
        playAgainButton = new Button("Play Again?");
        playAgainButton.setOnAction(e -> resetGame());
        playAgainButton.setVisible(false);
        VBox bottomBox = new VBox(turnStatus, playAgainButton);
        bottomBox.setAlignment(Pos.CENTER);
        
        borderPane.setBottom(bottomBox);
        BorderPane.setAlignment(bottomBox, Pos.CENTER);
        
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
        int col = (int) button.getUserData();
        for(int r = COLUMNS-1; r >= 0; r--){
            if (gameBoard[r][col] == 0){
                gameBoard[r][col] = currentPlayer;
                circleBoard[r][col].setFill(pieceColor);

                if (checkWinCon(r, col)){
                    GameOver();
                }
                break;
            }
        }
        //if col is full disable it
        if(gameBoard[0][col] != 0){
            button.setDisable(true);
        }

        //change the player
        if (!gameOver){
            currentPlayer = (currentPlayer == 1) ? 2:1;
            pieceColor = (currentPlayer == 1) ? Color.INDIANRED : Color.GOLD;
            turnStatus.setText("It is Player " + ((currentPlayer == 1) ? "One's (Red) ": "Two's (Yellow) ") + "turn");
        }
    }

    private boolean checkWinCon(int row, int col){
        int player = gameBoard[row][col];
        
        return checkDirection(row, col, 0, 1, player) //Right
            || checkDirection(row, col, -1, 1, player) //UpRight
            || checkDirection(row, col, -1, 0, player) //Up
            || checkDirection(row, col, -1, -1, player) //UpLeft
            || checkDirection(row, col, 0, -1, player) //Left
            || checkDirection(row, col, 1, -1, player) //DownLeft
            || checkDirection(row, col, 1, 0, player) //Down
            || checkDirection(row, col, 1, 1, player); //DownRight

    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir, int player){
        int count = 1;
        row += rowDir;
        col += colDir;
        while(row >= 0 && row < ROWS && col >=0 && col < COLUMNS && gameBoard[row][col]==player){
            count++;
            row += rowDir;
            col += colDir;
        }

        return count >= 4;
    }

    private void GameOver(){
        gameOver = true;
        for (Button b : buttonsList){
            b.setDisable(true);
        }
        System.out.println(turnStatus.getText());
        turnStatus.setText("Player " + ((currentPlayer==1)?"One ":"Two ") + "won the game!! ");
        turnStatus.setTextFill(pieceColor);
        //ask to play again
        playAgainButton.setVisible(true);
        currentPlayer = (currentPlayer == 1) ? 2:1;
        pieceColor = (currentPlayer == 1) ? Color.INDIANRED : Color.GOLD;
    }

    private void resetGame(){
        gameOver = false;
        gameBoard = new int[ROWS][COLUMNS]; //reset board to 0
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLUMNS; j++){
                circleBoard[i][j].setFill(Color.LIGHTGRAY); //reset circles
            }
        }
        for (Button b : buttonsList){
            b.setDisable(false); //reenable buttons
        }
        turnStatus.setTextFill(Color.BLACK);
        turnStatus.setText("It is Player " + ((currentPlayer == 1) ? "One's (Red) ": "Two's (Yellow) ") + "turn");
        playAgainButton.setVisible(false);
    }

    public static void main(String[] args) {
        launch();
    }

}