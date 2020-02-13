import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class PlayerController {

    Player player1;
    Player player2;
    ArrayList<Coords> coordsArrayList = new ArrayList<>();
    double xOrigine = 0;
    double yOrigine = 0;
    boolean restart = false;

    public PlayerController(Player player1,Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }
    public void controlPlayer1(Scene scene){

        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.UP){
                player1.setCurDir("up");
            }
            if(keyEvent.getCode() == KeyCode.DOWN){
                player1.setCurDir("down");
            }
            if(keyEvent.getCode() == KeyCode.LEFT){
                player1.setCurDir("left");
            }
            if(keyEvent.getCode() == KeyCode.RIGHT){
                player1.setCurDir("right");
            }


            if(keyEvent.getCode() == KeyCode.W){
                player2.setCurDir("up");
            }
            if(keyEvent.getCode() == KeyCode.S){
                player2.setCurDir("down");
            }
            if(keyEvent.getCode() == KeyCode.A){
                player2.setCurDir("left");
            }
            if(keyEvent.getCode() == KeyCode.D){
                player2.setCurDir("right");
            }

            if(keyEvent.getCode() == KeyCode.SPACE){
                Draw.animationTimer.start();
                reinitiate();
                restart =true;

            }
        });
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent me) {
                xOrigine = me.getSceneX();
                yOrigine = me.getSceneY();
            }
        });
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                if(player1.getCurDir().equals("up")|| player1.getCurDir().equals("down")){
                    if(me.getSceneX() > xOrigine){
                        player1.setCurDir("right");
                    }
                    if(me.getSceneX() < xOrigine){
                        player1.setCurDir("left");
                    }
                }
                else if (player1.getCurDir().equals("right")|| player1.getCurDir().equals("left")){

                    if(me.getSceneY() > yOrigine){
                        player1.setCurDir("down");
                    }
                    if(me.getSceneY() < yOrigine){
                        player1.setCurDir("up");
                    }
                }
            }
        });
    }
    public void DirToMove(Player player){
        if(player.getCurDir().equals("up")) player.up();
        if(player.getCurDir().equals("down")) player.down();
        if(player.getCurDir().equals("left")) player.left();
        if(player.getCurDir().equals("right")) player.right();
    }

    public void generateBoard(Player player10,int i,int j){
        coordsArrayList.add(new Coords(i,j));
        player10.getPlayerCoord().add(new Coords(i,j));
    }

    public void intersection(Player player0){

        if(intersectionWithBorder(player0)) {
            player0.setLose(true);
            Draw.animationTimer.stop();
        }
        else{
            if(findCoords(player0.getX(),player0.getY())) {
                player0.setLose(true);
                Draw.animationTimer.stop();
            }
            else{
                generateBoard(player0,player0.getX(),player0.getY());
            }
        }
    }

    public boolean findCoords(int u,int v){
        for(int i=0;i<coordsArrayList.size();i++){
            if(coordsArrayList.get(i).x == u && coordsArrayList.get(i).y ==v) return true;
        }
        return false;
    }
    public void reinitiate(){
        int oldPointsP1 = player1.getPointsGained();
        int oldPointsP2 = player2.getPointsGained();
        player1 = new Player(Draw.WIDTH/2,Draw.CANVAS_HEIGHT-4,Color.RED,"up");
        player2 = new Player(Draw.WIDTH/2,4,Color.WHITE,"down");
        player1.setPointsGained(oldPointsP1);
        player2.setPointsGained(oldPointsP2);
        player1.setColor(Draw.colorPicker.getValue());
        player2.setColor(Draw.colorPicker2.getValue());
        coordsArrayList = new ArrayList<>();
    }

    public void gainPlayer(Player one, Player two){
        String outcome ="";

        if(one.getX() == two.getX() && one.getY() == two.getY()){
            outcome="tie";
            System.out.println("tie");
        }
        else {
            if(one.isLose() == true){
                outcome = "Player 2 wins";
                two.setPointsGained(two.getPointsGained()+1);
                System.out.println("player 2 wins");
            }
            else if(two.isLose() == true) {
                outcome = "player 1 wins";
                one.setPointsGained(one.getPointsGained()+1);
                System.out.println("player 1 wins");
            }
            else if(intersectionWithBorder(one) && one.searchElement(two.getX(),two.getY()) || intersectionWithBorder(two) && two.searchElement(one.getX(),one.getY()) || one.searchElement(two.getX(),two.getY()) && two.searchElement(one.getX(),one.getY()) || intersectionWithBorder(one) && intersectionWithBorder(two)){
                outcome = "tie";
                System.out.println("Tie");
            }

        }
        outcome += "\nplayer1 : "+ one.getPointsGained() + "\nplayer2 :" + two.getPointsGained();
        System.out.println("player 1 : " + one.getPointsGained());
        System.out.println("player 2 : " + two.getPointsGained());

        Draw.txt.setText(outcome);
    }
    public boolean intersectionWithBorder(Player playplayplay){
        if(playplayplay.getX()+1 == Draw.WIDTH-1 || playplayplay.getX()-1 == 0 || playplayplay.getY() + 1 == Draw.CANVAS_HEIGHT-1 || playplayplay.getY()-1 == 1){
            return true;
        }
        else return false;
    }
}
