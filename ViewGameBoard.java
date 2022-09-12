package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

/**
 * Presents the user with the game graphical user interface
 */
public class ViewGameBoard extends Application
{
    private Canvas gameCanvas;
    private ControllerGameBoard controller;
    private GameBoardLabel fishRemaining;
    private GameBoardLabel guessesRemaining;
    private GameBoardLabel message;

    public static void main(String[] args)
    {
        // TODO: launch the app
        launch();
    }

    public void updateHeader() {
        //TODO update labels
        //"Fish: " + controller.modelGameBoard.getFishRemaining()
        //"Bait: " + controller.modelGameBoard.getGuessesRemaining()
        fishRemaining.setText("Fish: " + controller.modelGameBoard.getFishRemaining());
        guessesRemaining.setText("Bait: " + controller.modelGameBoard.getGuessesRemaining());

        if(controller.fishWin()) {
            //"Fishes win!"
            message.setText("Fishes win!Ha!");
        } else if(controller.playerWins()) {
            //"You win!"
            message.setText("You win!Finally!");
        } else {
            //"Find the fish!"
            message.setText("Find the fish!Go!");
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        controller = new ControllerGameBoard();
        // TODO initialize gameCanvas
        gameCanvas = new Canvas();

        fishRemaining = new GameBoardLabel();
        guessesRemaining = new GameBoardLabel();
        message = new GameBoardLabel();

        // TODO display game there are infinite ways to do this, I used BorderPane with HBox and VBox
        HBox labels = new HBox(35,fishRemaining,guessesRemaining,message);
        VBox columnCount = new VBox();
        BorderPane myPane = new BorderPane();

        labels.setAlignment(Pos.CENTER);
        myPane.setTop(labels);
        myPane.setCenter(columnCount);
        myPane.setMargin(columnCount, new Insets(10));

        updateHeader();

        for (int row=0; row < ModelGameBoard.DIMENSION; row++) {
            // TODO: create row container
            HBox rowCount = new HBox();
            rowCount.setSpacing(10);
            rowCount.setAlignment(Pos.CENTER);

            for (int col=0; col < ModelGameBoard.DIMENSION; col++) {
                GameBoardButton button = new GameBoardButton(row, col, controller.modelGameBoard.fishAt(row,col));
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> {
                    controller.makeGuess(finalRow, finalCol);
                    if(!controller.isGameOver()) {
                        button.handleClick();
                        updateHeader();
                    }
                });
                // TODO: add button to row
                rowCount.getChildren().add(button);
                rowCount.setHgrow(button, Priority.ALWAYS);
            }
            // TODO: add row to column
            columnCount.getChildren().add(rowCount);
            columnCount.setSpacing(5);
            columnCount.setAlignment(Pos.CENTER);
        }

        // TODO: create scene, stage, set title, and show
        Scene myScene = new Scene(myPane,500,300);
        stage.setTitle("Gone Fishing");
        stage.setScene(myScene);
        stage.show();

    }
}