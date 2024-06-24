import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import java.io.PrintWriter;

public class ServerGame implements ActionListener {


    int id;
    String name;
    String password;
    int maxCountPlayers;
    int countPlayers;

    int countdown = -1;
    Date countdownTime;
    Date time;
    Date time1 = new Date();
    Date time2 = new Date();
    Date time3 = new Date();
    Date time4 = new Date();
    Timer timer;
    boolean isStopped = true;
    boolean[][] pressedKeys = new boolean[4][2];

    Random random = new Random();
    boolean gameOver = false;
    int game_field_size = 750;
    int game_field_start_point_x = 20;
    int game_field_start_point_y = 0;
    Bit[] bits = new Bit[4];
    Ball ball = new Ball();
    String[] players = new String[4];
    PrintWriter[] out = new PrintWriter[4];


    ServerGame(int id, String name, String password, int countPlayers, int maxCountPlayers){
        this.id = id;
        this.name = name;
        this.password = password;
        this.maxCountPlayers = maxCountPlayers;
        this.countPlayers = countPlayers;
        players[0] = "not ready";
        if (maxCountPlayers == 2){
            players[1] = "none";
            players[2] = "wall";
            players[3] = "wall";
        }
        else if (maxCountPlayers == 3){
            players[1] = "none";
            players[2] = "none";
            players[3] = "wall";
        }
        else if (maxCountPlayers == 4){
            players[1] = "none";
            players[2] = "none";
            players[3] = "none";
        }
//        bits[0] = new Bit(20,340,0, 5, 4, 70);
//        if (bit3.equals("player")) {
//            bits[1] = new Bit(766, 340, 0, 5, 4, 70);
//        }
//        else{
//            bits[1] = new Bit(766, 0, 0, 0, 4, 750);
//        }
//        if (bit2.equals("player")) {
//            bits[2] = new Bit(360,0,5, 0, 70, 4);
//        }
//        else{
//            bits[2] = new Bit(20,0,0, 0, 750, 4);
//        }
//        if (bit4.equals("player")) {
//            bits[3] = new Bit(360,746,5, 0, 70, 4);
//        }
//        else{
//            bits[3] = new Bit(20,746,0, 0, 750, 4);
//        }
        for (int i=0; i<4; i++){
            pressedKeys[i][0] = false;
            pressedKeys[i][1] = false;
        }

        timer = new Timer(10, this);
        timer.start();
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (countdown == 0){
            countdown = -1;
        }
        if(countdown == -1) {
            for (int i = 0; i < 4; i++) {
                if (pressedKeys[i][0]) {
                    bits[i].direction = 1;
                    bits[i].move();
                }
                if (pressedKeys[i][1]) {
                    bits[i].direction = -1;
                    bits[i].move();
                }
            }
            ball.move();
            ball.isHitted();
            ball.isLosed();
        }
        else{
            Date t = new Date();
            countdown = countdown - (int) ((t.getTime() - countdownTime.getTime())/1000);
        }
        sendJSON("game");
    }


    class Ball {
        private int paintX, paintY, speed = 5;
        double x, y, dx, dy, r, k = 0.3925;
        int sizeX = 10, sizeY = 10;

        Ball(){
            x = 0;
            y = 0;
            r = random.nextDouble() * 6.28 - 3.14;
            double ar = Math.abs(r);
            while (!(ar>k && ar<0.75 || ar<3.14-k && ar>3.14-0.75 || ar>0.8 && ar<1.57-k || ar>1.57+k && ar<3.14-0.8)){
                r = random.nextDouble() * 6.28 - 3.14;
                ar = Math.abs(r);
            }
            dx = speed * Math.cos(r);
            dy = speed * Math.sin(r);
        }

        void move(){
            x += dx;
            y += dy;
        }


        void isHitted(){
            time = new Date();
            if (x + sizeX/2 >= 750 - 4 && y > bits[1].y - bits[1].sizeY/2 && y < bits[1].y + bits[1].sizeY/2){
                if (time.getTime() - time1.getTime() > 500){
                    dx = -dx;
                    time1 = time;
                }
            }
            if (x - sizeX/2 <= 4 && y > bits[0].y - bits[0].sizeY/2 && y < bits[0].y + bits[0].sizeY/2){
                if (time.getTime() - time2.getTime() > 500){
                    dx = -dx;
                    time2 = time;
                }
            }
            if (y + sizeY/2 >= 750 - 4 && x > bits[3].x - bits[3].sizeX/2 && x < bits[3].x + bits[3].sizeX/2){
                if (time.getTime() - time3.getTime() > 500){
                    dy = -dy;
                    time3 = time;
                }
            }
            if (y - sizeY/2 <= 4 && x > bits[2].x - bits[2].sizeX/2 && x < bits[2].x + bits[2].sizeX/2){
                if (time.getTime() - time4.getTime() > 500){
                    dy = -dy;
                    time4 = time;
                }
            }
        }

        void isLosed(){
            boolean t = false;
            if (x > 750 - 4 && !(y > bits[1].y - bits[1].sizeY/2 && y < bits[1].y + bits[1].sizeY/2)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                double ar = Math.abs(r);
                while (!(ar>k && ar<0.7)){
                    r = random.nextDouble() * 6.28 - 3.14;
                    ar = Math.abs(r);
                }
                bits[1].increaseScore();
            }
            if (x < 4 && !(y > bits[0].y - bits[0].sizeY/2 && y < bits[0].y + bits[0].sizeY/2)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                double ar = Math.abs(r);
                while (!(ar<3.14-k && ar>3.14-0.7)){
                    r = random.nextDouble() * 6.28 - 3.14;
                    ar = Math.abs(r);
                }
                bits[0].increaseScore();
            }
            if (y > 750 - 4 && !(x > bits[3].x - bits[3].sizeX/2 && x < bits[3].x + bits[3].sizeX/2)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                while (!(r>0.85 && r<1.57-k || r>1.57+k && r<3.14-0.85)){
                    r = random.nextDouble() * 6.28 - 3.14;
                }
                bits[3].increaseScore();
            }
            if (y < 4 && !(x > bits[2].x - bits[2].sizeX/2 && x < bits[2].x + bits[2].sizeX/2)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                while (!(r<-0.85 && r>-1.57+k || r<-1.57-k && r>-3.14+0.85)){
                    r = random.nextDouble() * 6.28 - 3.14;
                }
                bits[2].increaseScore();
            }
            if (t){
                dx = speed * Math.cos(r);
                dy = speed * Math.sin(r);
                timer.stop();
                isStopped = true;
                x = 0;
                y = 0;
                countdown = 3;
                countdownTime = new Date();
            }
        }
    }

    class Bit{
        int x, y, dx, dy, sizeX, sizeY, globalX, globalY;
        int direction = 0;
        int score = 0;

        Bit(int x, int y, int dx, int dy, int sizeX, int sizeY){
            this.globalX = x;
            this.globalY = y;
            this.dx = dx;
            this.dy = dy;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.x = 0;
            this.y = 0;
        }


        void increaseScore(){
            score ++;
            if (score == 5){
                if (sizeX > sizeY){
                    sizeX = 750;
                    x = 0;
                }
                else{
                    sizeY = 750;
                    y = 0;
                }
                dx = 0;
                dy = 0;
                int c = 0;
                for (int i = 0; i < 4; i++){
                    if (bits[i].score == 5){
                        c++;
                    }
                }
                if (c == 3){
                    gameOver = true;
                    timer.stop();
                    for (int i=0; i<4; i++){
                        if (players[i].equals("ready")){
                            players[i] = "not ready";
                        }
                    }
                    sendJSON("endGame");
                }
            }
        }

        void move(){
            if (dx!=0 || dy!=0) {
                x += dx * direction;
                y += dy * direction;
                if (x + sizeX/2 > 750) {
                    x = 750/2 - sizeX/2;
                }
                if (x - sizeX/2 < 0) {
                    x = - 750/2 + sizeX/2;
                }
                if (y + sizeY/2 > 750) {
                    y = 750/2 - sizeY/2;
                }
                if (y + sizeY/2 < 0) {
                    y = - 750/2 + sizeY/2;
                }
            }
        }
    }

    public void sendJSON(String mainTag){
        String message;
        JSONObject json = new JSONObject();
        json.put("mainTag", mainTag);
        if (mainTag.equals("readiness")) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < 4; i++) {
                array.put(players[i]);
            }
            json.put("ready", array);
        }
        else if (mainTag.equals("game")){
            JSONArray ar;
            for (int i=0; i<4; i++) {
                ar = new JSONArray();
                ar.put(bits[i].x);
                ar.put(bits[i].y);
                ar.put(bits[i].sizeX);
                ar.put(bits[i].sizeY);
                json.put("bit" + i, ar);
            }
            ar = new JSONArray();
            ar.put(ball.x);
            ar.put(ball.y);
            json.put("ball", ar);
            ar = new JSONArray();
            for (int i = 0; i < 4; i++) {
                ar.put(bits[i].score);
            }
            json.put("scores", ar);
            json.put("countdown", countdown);
        }
        else if (mainTag.equals("endGame")){
            JSONArray array = new JSONArray();
            for (int i = 0; i < 4; i++) {
                array.put(players[i]);
            }
            json.put("ready", array);
        }
        message = json.toString();
        for (int i=0; i<4; i++){
            if (players[i].equals("ready") || players[i].equals("not ready")){
                out[i].println(message);
            }
        }
    }

    private void getJSON(){

    }
}
