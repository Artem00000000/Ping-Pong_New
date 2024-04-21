import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Random;

public class ServerGame implements ActionListener {


    int id;
    String name;
    String password;
    int maxCountPlayers;
    int countPlayers;

    Date time;
    Date time1 = new Date();
    Date time2 = new Date();
    Date time3 = new Date();
    Date time4 = new Date();
    Timer timer;
    boolean isStopped = true;
    boolean[] pressedKeys = new boolean[8];

    Random random = new Random();
    boolean gameOver = false;
    int game_field_size = 750;
    int game_field_start_point_x = 20;
    int game_field_start_point_y = 0;
    Bit[] bits = new Bit[4];
    Ball ball = new Ball();


    ServerGame(int id, String name, String password, int countPlayers, int maxCountPlayers){
        this.id = id;
        this.name = name;
        this.password = password;
        this.maxCountPlayers = maxCountPlayers;
        this.countPlayers = countPlayers;
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
        for (int i=0; i<8; i++){
            pressedKeys[i] = false;
        }

        timer = new Timer(10, this);
        timer.start();
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0; i<8; i++){
            if (pressedKeys[i]){
                if (i<4){
                    bits[i].direction = 1;
                    bits[i].move();
                }
                else{
                    bits[i-4].direction = -1;
                    bits[i-4].move();
                }
            }
        }
        ball.move();
        ball.isHitted();
        ball.isLosed();
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
            boolean t = false;
            if (paintX + sizeX >= game_field_size + game_field_start_point_x - 4 && paintY > bits[1].y + bits[1].globalY && paintY < bits[1].y + bits[1].globalY + bits[1].sizeY){
                if (time.getTime() - time1.getTime() > 500){
                    dx = -dx;
                    time1 = time;
                    t = true;
                }
            }
            if (paintX <= game_field_start_point_x + 4 && paintY > bits[0].y + bits[0].globalY && paintY < bits[0].y + bits[0].globalY + bits[0].sizeY){
                if (time.getTime() - time2.getTime() > 500){
                    dx = -dx;
                    time2 = time;
                    t = true;
                }
            }
            if (paintY + sizeY >= game_field_size + game_field_start_point_y - 4 && paintX > bits[3].x + bits[3].globalX && paintX < bits[3].x + bits[3].globalX + bits[3].sizeX){
                if (time.getTime() - time3.getTime() > 500){
                    dy = -dy;
                    time3 = time;
                    t = true;
                }
            }
            if (paintY <= game_field_start_point_y + 4 && paintX > bits[2].x + bits[2].globalX && paintX < bits[2].x + bits[2].globalX + bits[2].sizeX){
                if (time.getTime() - time4.getTime() > 500){
                    dy = -dy;
                    time4 = time;
                    t = true;
                }
            }
        }

        void isLosed(){
            boolean t = false;
            if (paintX + sizeX/2 > game_field_size + game_field_start_point_x - 4 && !(paintY >= bits[1].y + bits[1].globalY && paintY <= bits[1].y + bits[1].globalY + bits[1].sizeY)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                double ar = Math.abs(r);
                while (!(ar>k && ar<0.7)){
                    r = random.nextDouble() * 6.28 - 3.14;
                    ar = Math.abs(r);
                }
                bits[1].increaseScore();
            }
            if (paintX + sizeX/2 < game_field_start_point_x + 4 && !(paintY > bits[0].y + bits[0].globalY && paintY < bits[0].y + bits[0].globalY + bits[0].sizeY)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                double ar = Math.abs(r);
                while (!(ar<3.14-k && ar>3.14-0.7)){
                    r = random.nextDouble() * 6.28 - 3.14;
                    ar = Math.abs(r);
                }
                bits[0].increaseScore();
            }
            if (paintY + sizeY/2 > game_field_size + game_field_start_point_y - 4 && !(paintX > bits[3].x + bits[3].globalX && paintX < bits[3].x + bits[3].globalX + bits[3].sizeX)){
                t = true;
                r = random.nextDouble() * 6.28 - 3.14;
                while (!(r>0.85 && r<1.57-k || r>1.57+k && r<3.14-0.85)){
                    r = random.nextDouble() * 6.28 - 3.14;
                }
                bits[3].increaseScore();
            }
            if (paintY + sizeY/2 < game_field_start_point_y + 4 && !(paintX > bits[2].x + bits[2].globalX && paintX < bits[2].x + bits[2].globalX + bits[2].sizeX)){
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
            }
        }

        void move(){
            if (dx!=0 || dy!=0) {
                x += dx * direction;
                y += dy * direction;
                if (x + globalX + sizeX > game_field_size + game_field_start_point_x) {
                    x = game_field_size/2 - sizeX/2;
                }
                if (x + globalX < game_field_start_point_x) {
                    x = - game_field_size/2 + sizeX/2;
                }
                if (y + globalY + sizeY > game_field_size + game_field_start_point_y) {
                    y = game_field_size/2 - sizeY/2;
                }
                if (y + globalY < game_field_start_point_y) {
                    y = - game_field_size/2 + sizeY/2;
                }
            }
        }
    }

    private void sendJSON(){

    }

    private void getJSON(){

    }
}
