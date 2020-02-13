import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;


public class Draw extends Application {

    public static int WIDTH = 1000;
    public static int HEIGHT = 600;
    public static int CANVAS_HEIGHT = 470;
    static PlayerController playerController = new PlayerController(new Player(WIDTH / 2, (CANVAS_HEIGHT) - 4, Color.RED, "up"), new Player(WIDTH / 2, 4, Color.WHITE, "down"));
    public static AnimationTimer animationTimer;
    public static Image image;
    public static Canvas canvas = new Canvas(WIDTH, CANVAS_HEIGHT);
    public static Text txt = new Text(WIDTH / 2, 700, "");
    Button restart = new Button("restart");
    Button pause = new Button("pause");
    Button go = new Button("go");
    public static ColorPicker colorPicker = new ColorPicker(playerController.player1.getColor());
    public static ColorPicker colorPicker2 = new ColorPicker(playerController.player2.getColor());
    double i = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                playerController.player1.setColor(colorPicker.getValue());
                //playerController.player2.setColor(colorPicker.getValue());
            }
        });
        colorPicker2.setOnAction(new EventHandler() {
            public void handle(Event t) {
                playerController.player2.setColor(colorPicker2.getValue());
                //playerController.player2.setColor(colorPicker.getValue());
            }
        });

        BorderPane pane = new BorderPane(txt, canvas, restart, pause, go);
        pane.getChildren().addAll(colorPicker, colorPicker2);
        restart.setFocusTraversable(false);
        pause.setFocusTraversable(false);
        go.setFocusTraversable(false);
        colorPicker.setFocusTraversable(false);
        colorPicker2.setFocusTraversable(false);
        colorPicker.setLayoutX(600);
        colorPicker.setLayoutY(500);
        colorPicker2.setLayoutX(200);
        colorPicker2.setLayoutY(500);
        restart.setLayoutX(800);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillRect(0, 0, WIDTH, CANVAS_HEIGHT);

        restart.setOnMouseClicked(mouseEvent -> {
            Draw.animationTimer.start();
            playerController.reinitiate();
            playerController.restart = true;
        });
        pause.setOnMouseClicked(mouseEvent -> {

            Draw.animationTimer.stop();
        });
        go.setOnMouseClicked(mouseEvent -> {
            Draw.animationTimer.start();
        });


        animationTimer = new AnimationTimer() {
            long now = System.nanoTime();

            public void handle(long l) {

                //for (int j = 0; j < Math.max(3,i/200); j++) {
                    playerController.controlPlayer1(scene);
                    playerController.DirToMove(playerController.player1);
                    canvas.getGraphicsContext2D().setFill(playerController.player1.getColor());
                    canvas.getGraphicsContext2D().fillRect(playerController.player1.getX(), playerController.player1.getY(), 4, 4);
                    playerController.intersection(playerController.player1);

                    playerController.controlPlayer1(scene);
                    playerController.DirToMove(playerController.player2);
                    canvas.getGraphicsContext2D().setFill(playerController.player2.getColor());
                    canvas.getGraphicsContext2D().fillRect(playerController.player2.getX(), playerController.player2.getY(), 4, 4);
                    playerController.intersection(playerController.player2);
                    playerController.gainPlayer(playerController.player1, playerController.player2);

                    if (playerController.restart == true) {
                        canvas.getGraphicsContext2D().setFill(Color.BLACK);
                        canvas.getGraphicsContext2D().fillRect(0, 0, WIDTH, CANVAS_HEIGHT);
                        playerController.controlPlayer1(scene);
                        playerController.restart = false;
                   // }

                    }
                i+=1000;
                }

            };
            try {
                Thread.sleep((long) (2000-i));
                animationTimer.start();
                if(i>2000) i=100;
                ;

            } catch (Exception e) {
                e.printStackTrace();
            }

        stage.show();
            stage.setScene(scene);
        }
}
