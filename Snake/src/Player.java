import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    private int x;
    private int y;
    private Color color;
    String curDir;
    private int pointsGained = 0;
    private boolean lose = false;
    private ArrayList<Coords> playerCoord = new ArrayList<>();

    public ArrayList<Coords> getPlayerCoord() {
        return playerCoord;
    }

    public Player(int x, int y, Color color, String curDir){
        this.x = x;
        this.y = y;
        this.color = color;
        this.curDir = curDir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void up(){
        y--;
    }
    public void down(){
        y++;
    }
    public void right(){
        x++;
    }
    public void left(){
        x--;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCurDir(String curDir) {
        this.curDir = curDir;
    }

    public String getCurDir() {
        return curDir;
    }

    public void setPointsGained(int pointsGained) {
        this.pointsGained = pointsGained;
    }

    public int getPointsGained() {
        return pointsGained;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public boolean searchElement(int x,int y){
        for(int i=0;i<playerCoord.size();i++){
            if(playerCoord.get(i).x == x && playerCoord.get(i).y == y) return true;
            else return false;
        }
        return false;
    }
}