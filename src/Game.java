import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


class Game extends JPanel implements ActionListener {

    BufferedImage dvd;
    boolean is_dvd = false;
    Date time;
    Date time1 = new Date();
    Date time2 = new Date();
    Date time3 = new Date();
    Date time4 = new Date();
    Timer timer;
    boolean isStopped = true;
    boolean[] pressedKeys = new boolean[8];

    boolean scoreChanged;

    Font font = new Font("", Font.BOLD, 21);
    JLabel label = new JLabel();
    JFrame frame;
    Random random = new Random();
    //    Canvas canvas = new Canvas();
    int color_gameField = 0x000000;
    int color_notGameField = 0x580185;
    int color_gameElements = 0xffffff;
    int dvd_color = 0xffffff;
    int new_dvd_color = 0x00ff00;
    boolean gameOver = false;
    int countPlayers;
    int game_field_size = 750;
    int game_field_start_point_x = 20;
    int game_field_start_point_y = 0;
    Bit[] bits = new Bit[4];
    Ball ball = new Ball();


    Game(int countPlayers, JFrame menu, JFrame frame, String resolution, String bit2, String bit3, String bit4){
        try {
            //dvd = ImageIO.read(new File("res/DVD_logo30.png"));
            dvd = ImageIO.read(new File("res/DVD_logo30.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.frame = frame;
        this.countPlayers = countPlayers;
        bits[0] = new Bit(20,340,0, 5, 4, 70);
        if (bit3.equals("player")) {
            bits[1] = new Bit(766, 340, 0, 5, 4, 70);
        }
        else{
            bits[1] = new Bit(766, 0, 0, 0, 4, 750);
        }
        if (bit2.equals("player")) {
            bits[2] = new Bit(360,0,5, 0, 70, 4);
        }
        else{
            bits[2] = new Bit(20,0,0, 0, 750, 4);
        }
        if (bit4.equals("player")) {
            bits[3] = new Bit(360,746,5, 0, 70, 4);
        }
        else{
            bits[3] = new Bit(20,746,0, 0, 750, 4);
        }
//        if (countPlayers == 4) {
//            bits[2] = new Bit(360,0,5, 0, 70, 4);
//            bits[3] = new Bit(360,746,5, 0, 70, 4);
//        }
//        else {
//            bits[2] = new Bit(20,0,0, 0, 750, 4);
//            bits[3] = new Bit(20,746,0, 0, 750, 4);
//        }
        for (int i=0; i<8; i++){
            pressedKeys[i] = false;
        }
        frame.setTitle("Ping-Pong");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        if (! resolution.equals("fullscreen")) {
            String[] parts = resolution.split("x");
            int w = Integer.valueOf(parts[0]);
            int h = Integer.valueOf(parts[1]);
            frame.setSize(w, h);
            if (w<width && h<height) {
                frame.setLocation((width - w) / 2, (height - h) / 2);
            }
        }
        else{
            frame.setSize(width, height);
        }
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        label.setFont(font);
        label.setOpaque(true);
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        label.setHorizontalAlignment(JLabel.CENTER);
//        add(BorderLayout.CENTER, canvas);
        frame.add(BorderLayout.SOUTH, label);
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B){
                    timer.stop();
                    isStopped = true;
                    menu.setVisible(true);
                    frame.dispose();
                    //frame.setVisible(false);
                }
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    if(isStopped){
                        isStopped=false;
                        timer.start();
                    }
                    else{
                        isStopped=true;
                        timer.stop();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    pressedKeys[0]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_W){
                    pressedKeys[4]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    pressedKeys[1]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    pressedKeys[5]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    pressedKeys[6]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    pressedKeys[2]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    pressedKeys[7]=true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    pressedKeys[3]=true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S){
                    pressedKeys[0]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_W){
                    pressedKeys[4]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    pressedKeys[1]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    pressedKeys[5]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    pressedKeys[6]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    pressedKeys[2]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    pressedKeys[7]=false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    pressedKeys[3]=false;
                }
            }
        });
        timer = new Timer(10, this);
        frame.setResizable(false);
        frame.setVisible(true);
        timer.start();
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.repaint();
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

//    void go(){
//        while(!gameOver){
//            for (int i=0; i<4; i++){
//                bits[i].move();
//            }
//            ball.move();
//            repaint();
//        }
//    }

    @Override
    public void paint(Graphics g) {
        Rectangle r = frame.getBounds();
        int h = r.height;
        int w = r.width;
        if (h-40 < game_field_size || w - 40 < game_field_size){
            game_field_size = Math.min(h-40, w-40);
        }
        if (game_field_size < 750 && h-40 > game_field_size && w - 40 > game_field_size){
            game_field_size = Math.min(750, Math.min(h-40, w-40));
        }
        game_field_start_point_x = (w - game_field_size) / 2 - 8;
        game_field_start_point_y = (h - game_field_size) / 2 - 20;
        g.setColor(new Color(color_notGameField));
        g.fillRect(0,0, w, h);
        g.setColor(Color.black);
        g.fillRect(game_field_start_point_x,game_field_start_point_y,game_field_size,game_field_size);
        g.setColor(Color.white);
        if (! is_dvd) {
            g.drawLine(game_field_start_point_x, game_field_start_point_y, game_field_size + game_field_start_point_x, game_field_size + game_field_start_point_y);
            g.drawLine(game_field_start_point_x, game_field_size + game_field_start_point_y, game_field_size + game_field_start_point_x, game_field_start_point_y);
            g.drawString(Integer.toString(bits[2].score), game_field_start_point_x + 372, game_field_start_point_y + 345);
            g.drawString(Integer.toString(bits[3].score), game_field_start_point_x + 372, game_field_start_point_y + 414);
            g.drawString(Integer.toString(bits[0].score), game_field_start_point_x + 342, game_field_start_point_y + 380);
            g.drawString(Integer.toString(bits[1].score), game_field_start_point_x + 407, game_field_start_point_y + 380);
        }
        bits[0].globalX = game_field_start_point_x;
        bits[0].globalY = game_field_start_point_y + game_field_size/2 - bits[0].sizeY/2;
        bits[1].globalX = game_field_start_point_x + game_field_size - bits[1].sizeX;
        bits[1].globalY = game_field_start_point_y + game_field_size/2 - bits[1].sizeY/2;
        bits[2].globalY = game_field_start_point_y;
        bits[2].globalX = game_field_start_point_x + game_field_size/2 - bits[2].sizeX/2;
        bits[3].globalY = game_field_start_point_y + game_field_size - bits[3].sizeY;
        bits[3].globalX = game_field_start_point_x + game_field_size/2 - bits[3].sizeX/2;
        for (Bit bit : bits) {
            bit.paint(g);
        }
        ball.paint(g);
//        g.drawString(Integer.toString(bits[2].score),400-5, 412-30);
//        g.drawString(Integer.toString(bits[3].score),400-5, 412+30+9);
//        g.drawString(Integer.toString(bits[0].score),400-30-5, 412+5);
//        g.drawString(Integer.toString(bits[1].score),400+30, 412+5);
    }

//    void paint(Graphics g){
//        for (Bit bit : bits) bit.paint(g);
//        ball.paint(g);
//    }

    class Ball {
        private int paintX, paintY, speed = 5;
        double x, y, dx, dy, r, k = 0.3925;
        int sizeX = 10, sizeY = 10;
        //private boolean is_dvd = false;

        Ball(){
            //x=395;
            //y=407;
            x = 0;
            y = 0;
//            x = 395 - sizeX/2;
//            y = 375 - sizeY/2;
//            dx = random.nextBoolean()? 3 : -3;
//            dy = random.nextBoolean()? 3 : -3;
//            r = random.nextDouble();
//            while (r < 0.3){
//                r = random.nextDouble();
//            }
//            dy = dy * r;
            r = random.nextDouble() * 6.28 - 3.14;
            double ar = Math.abs(r);
            //while (r<k && r>-k || r<1.57+k && r>1.57-k || r<-1.57+k && r>-1.57-k || r>-3.14 && r<-3.14+k || r>3.14-k && r<3.14){
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

        void paint(Graphics g){
            g.setColor(Color.white);
            paintX = game_field_size/2 + game_field_start_point_x - sizeX/2 + (int)Math.round(x);
            paintY = game_field_size/2 + game_field_start_point_y - sizeY/2 + (int)Math.round(y);
            if (is_dvd){
                //if (bits[0].sizeY == 750 && bits[1].sizeY == 750 && bits[2].sizeX == 750 && bits[3].sizeX == 750){
                //sizeX = 111;
                //sizeY = 50;
//                for (int i = 0; i < dvd.getWidth(); i++) {
//                    for (int j = 0; j < dvd.getHeight(); j++) {
//                        System.out.println(dvd.getRGB(i, j));
//                        if (dvd.getRGB(i, j) != 0) {
//                            //if (dvd.getRGB(i, j) == new Color(dvd_color).getRGB()) {
//                            dvd.setRGB(i, j, new Color(new_dvd_color).getRGB());
//                        }
//                    }
//                }
                //g.setColor(new Color(dvd_color));
                g.drawImage(dvd, paintX, paintY, null);
            }
            else {
                g.fillOval(paintX, paintY, 10, 10);
            }
        }

        //        void isHitted(){
//            if (x + 10 >= 766 && y + 5 > bits[1].y && y + 5 < bits[1].y + bits[1].sizeY){
//                dx = -dx;
//            }
//            if (x <= 24 && y + 5 > bits[0].y && y + 5 < bits[0].y + bits[0].sizeY){
//                dx = -dx;
//            }
//            if (y + 10 >= 746 && x + 5 > bits[3].x && x + 5 < bits[3].x + bits[3].sizeX){
//                dy = -dy;
//            }
//            if (y <= 4 && x + 5 > bits[2].x && x + 5 < bits[2].x + bits[2].sizeX){
//                dy = -dy;
//            }
//        }
//        void isLosed(){
//            if (x + 5 > 766 && !(y + 5 >= bits[1].y && y + 5 <= bits[1].y + 70)){
//                x = 390;
//                y = 370;
//                timer.stop();
//                isStopped = true;
//                bits[1].increaseScore();
//            }
//            if (x + 5 < 24 && !(y + 5 > bits[0].y && y + 5 < bits[0].y + 70)){
//                x = 390;
//                y = 370;
//                timer.stop();
//                isStopped = true;
//                bits[0].increaseScore();
//            }
//            if (y + 5 > 746 && !(x + 5 > bits[3].x && x + 5 < bits[3].x + 70)){
//                x = 390;
//                y = 370;
//                timer.stop();
//                isStopped = true;
//                bits[3].increaseScore();
//            }
//            if (y + 5 < 4 && !(x + 5 > bits[2].x && x + 5 < bits[2].x + 70)){
//                x = 390;
//                y = 370;
//                timer.stop();
//                isStopped = true;
//                bits[2].increaseScore();
//            }
//        }
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
            if (t && is_dvd){
                new_dvd_color = random.nextInt(16777215);
                for (int i = 0; i < dvd.getWidth(); i++) {
                    for (int j = 0; j < dvd.getHeight(); j++) {
                        if (dvd.getRGB(i, j) != 0) {
                            //if (dvd.getRGB(i, j) == new Color(dvd_color).getRGB()) {
                            //new_dvd_color = random.nextInt(16777215);
                            dvd.setRGB(i, j, new Color(new_dvd_color).getRGB());
                        }
                        //else {
                        //    dvd.setRGB(i, j, 0);
                        //}
                        //System.out.println(dvd.getRGB(i, j));
                    }
                }
                //dvd_color = new_dvd_color;
            }
        }

        void isLosed(){
            boolean t = false;
            if (paintX + sizeX/2 > game_field_size + game_field_start_point_x - 4 && !(paintY >= bits[1].y + bits[1].globalY && paintY <= bits[1].y + bits[1].globalY + bits[1].sizeY)){
                t = true;
//                dy = dy/r;
//                r = random.nextDouble();
//                while (r < 0.3){
//                    r = random.nextDouble();
//                }
//                dy = dy * r;
                //r = random.nextDouble() * 1.57 - 0.785;
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
//                dy = dy/r;
//                r = random.nextDouble();
//                while (r < 0.3){
//                    r = random.nextDouble();
//                }
//                dy = dy * r;
                //r = random.nextDouble() * 1.57 + 1.57 + 0.785;
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
//                dy = dy/r;
//                r = random.nextDouble();
//                while (r < 0.3){
//                    r = random.nextDouble();
//                }
//                dy = dy * r;
                //r = random.nextDouble() * 1.57 + 0.785;
                r = random.nextDouble() * 6.28 - 3.14;
                while (!(r>0.85 && r<1.57-k || r>1.57+k && r<3.14-0.85)){
                    r = random.nextDouble() * 6.28 - 3.14;
                }
                bits[3].increaseScore();
            }
            if (paintY + sizeY/2 < game_field_start_point_y + 4 && !(paintX > bits[2].x + bits[2].globalX && paintX < bits[2].x + bits[2].globalX + bits[2].sizeX)){
                t = true;
//                dy = dy/r;
//                r = random.nextDouble();
//                while (r < 0.3){
//                    r = random.nextDouble();
//                }
//                dy = dy * r;
                //r = random.nextDouble() * 1.57 - 1.57 - 0.785;
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
                if (bits[0].sizeY == game_field_size && bits[1].sizeY == game_field_size && bits[2].sizeX == game_field_size && bits[3].sizeX == game_field_size && !is_dvd) {
                    sizeX = 111;
                    sizeY = 50;
                    is_dvd = true;
                    new_dvd_color = random.nextInt(16777215);
                    for (int i = 0; i < dvd.getWidth(); i++) {
                        for (int j = 0; j < dvd.getHeight(); j++) {
                            //if (dvd.getRGB(i, j) != 0) {
                            if (dvd.getRGB(i, j) == new Color(dvd_color).getRGB()) {
                                dvd.setRGB(i, j, new Color(new_dvd_color).getRGB());
                            }
                            else {
                                dvd.setRGB(i, j, 0);
                            }
                            //System.out.println(dvd.getRGB(i, j));
                        }
                    }
                }
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
//            bits[0].globalX = game_field_start_point_x;
//            bits[0].globalY = game_field_start_point_y + game_field_size/2 - bits[0].sizeY/2;
//            bits[1].globalX = game_field_start_point_x + game_field_size - bits[1].sizeX;
//            bits[1].globalY = game_field_start_point_y + game_field_size/2 - bits[1].sizeY/2;
//            bits[2].globalY = game_field_start_point_y;
//            bits[2].globalX = game_field_start_point_x + game_field_size/2 - bits[2].sizeX/2;
//            bits[3].globalY = game_field_start_point_y + game_field_size - bits[3].sizeY;
//            bits[3].globalX = game_field_start_point_x + game_field_size/2 - bits[3].sizeX/2;
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

//        void moveRight(){
//            x += dx;
//        }
//
//        void moveUp(){
//            y -= dy;
//        }
//
//        void moveDown(){
//            y += dy;
//        }

        void paint(Graphics g){
            g.setColor(Color.white);
            g.fillRect(x + globalX, y + globalY, sizeX, sizeY);
        }
    }

//    class Canvas extends JPanel {
//        @Override
//        public void paint(Graphics g) {
//            super.paint(g);
//            for (Bit bit : bits) bit.paint(g);
//            ball.paint(g);
//        }
//    }
}
