import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;

public class ClientGame extends JPanel implements ActionListener {
    int[] screenSize = new int[2];
    int[][] bits = new int[4][4];
    int[] scores = new int [4];
    int number;
    int id_room;
    int[] ball = new int[2];
    int game_field_size = 750;
    int game_field_start_point_x, game_field_start_point_y;
    int color_notGameField = 0x580185;

    Timer timer;
    int countdown;
    boolean keyPressed = false;
    boolean isCountdown = false;
    boolean isStopped = true;
    boolean isStarted = false;
    boolean isEnded = false;
    boolean[] pressedKeys = new boolean[2];
    Date date;

    String[] players = new String[4];
    JFrame window1, window;
    PrintWriter out;
    BufferedReader in;

    ClientGame(JFrame window1, JFrame window, JSONObject json, PrintWriter out, BufferedReader in, String resolution){
        this.window1 = window1;
        this.window = window;
        this.out = out;
        this.in = in;
        this.number = json.getInt("number");
        this.id_room = json.getInt("id");
//        players[0] = "not ready";
//        players[1] = "none";
//        if (countPlayers == 2){
//            players[2] = "wall";
//            players[3] = "wall";
//        }
//        else if (countPlayers == 3){
//            players[2] = "none";
//            players[3] = "wall";
//        }
//        else{
//            players[2] = "none";
//            players[3] = "none";
//        }

        window.setTitle("Ping-Pong");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        if (! resolution.equals("fullscreen")) {
            String[] parts = resolution.split("x");
            int w = Integer.valueOf(parts[0]);
            int h = Integer.valueOf(parts[1]);
            window.setSize(w, h);
            if (w<width && h<height) {
                window.setLocation((width - w) / 2, (height - h) / 2);
            }
        }
        else{
            window.setSize(width, height);
        }
        this.screenSize[0] = width;
        this.screenSize[1] = height;
        game_field_start_point_x = (width - game_field_size) / 2 - 8;
        game_field_start_point_y = (height - game_field_size) / 2 - 20;

        window.setBackground(Color.BLACK);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setResizable(false);


        window.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B){
                    if (!isStarted){
                        timer.stop();
                        window1.setVisible(true);
                        window.dispose();
                        window.setVisible(false);
                        String message;
                        JSONObject json = new JSONObject();
                        json.put("mainTag", "leaveRoom");
                        json.put("id", id_room);
                        json.put("number", number);
                        message = json.toString();
                        out.println(message);
                    }
                    timer.stop();
                    isStopped = true;
                    window1.setVisible(true);
                    //frame.dispose();
                    //frame.setVisible(false);
                }
//                if(e.getKeyCode()==KeyEvent.VK_SPACE){
//                    if(isStopped){
//                        isStopped=false;
//                        timer.start();
//                    }
//                    else{
//                        isStopped=true;
//                        timer.stop();
//                    }
//                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    pressedKeys[0]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_W){
                    pressedKeys[1]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    pressedKeys[0]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    pressedKeys[1]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    pressedKeys[1]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    pressedKeys[0]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    pressedKeys[1]=true;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    pressedKeys[0]=true;
                    keyPressed = true;
                }
                if (keyPressed){
                    JSONObject json = new JSONObject();
                    String message;
                    json.put("mainTag", "keys");
                    json.put("id", id_room);
                    json.put("number", number);
                    json.put("key1", pressedKeys[0]);
                    json.put("key2", pressedKeys[1]);
                    message = json.toString();
                    out.println(message);
                    keyPressed = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S){
                    pressedKeys[0]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_W){
                    pressedKeys[1]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    pressedKeys[0]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    pressedKeys[1]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    pressedKeys[1]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    pressedKeys[0]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    pressedKeys[1]=false;
                    keyPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    pressedKeys[0]=false;
                    keyPressed = true;
                }
                if (keyPressed){
                    JSONObject json = new JSONObject();
                    String message;
                    json.put("mainTag", "keys");
                    json.put("id", id_room);
                    json.put("number", number);
                    json.put("key1", pressedKeys[0]);
                    json.put("key2", pressedKeys[1]);
                    message = json.toString();
                    out.println(message);
                    keyPressed = false;
                }
            }
        });

        timer = new Timer(10, this);
        window.setResizable(false);
        window.setVisible(true);
        timer.start();
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        window.repaint();
        try{
            String inputLine;
            if((inputLine = in.readLine()) != null){
                JSONObject js = new JSONObject(inputLine);
                if (js.getString("mainTag").equals("readiness") && !isStarted){
                    JSONArray ar = js.getJSONArray("ready");
                    String ready;
                    for (int i = 0; i<4; i++){
                        ready = ar.getString(i);
                        players[i] = ready;
                    }
                }
                else if (js.getString("mainTag").equals("game") && isStarted){
                    JSONArray ar1 = js.getJSONArray("bit1");
                    JSONArray ar2 = js.getJSONArray("bit2");
                    JSONArray ar3 = js.getJSONArray("bit3");
                    JSONArray ar4 = js.getJSONArray("bit4");
                    for (int i = 0; i<4; i++){
                        bits[0][i] = ar1.getInt(i);
                        bits[1][i] = ar2.getInt(i);
                        bits[2][i] = ar3.getInt(i);
                        bits[3][i] = ar4.getInt(i);
                    }
                    ar1 = js.getJSONArray("ball");
                    ball[0] = ar1.getInt(0);
                    ball[1] = ar1.getInt(1);
                    ar1 = js.getJSONArray("scores");
                    for (int i = 0; i<4; i++){
                        scores[i] = ar1.getInt(i);
                    }
                    countdown = js.getInt("countdown");
                }
                else if (js.getString("mainTag").equals("startGame")){
                    isStarted = true;
                }
                else if (js.getString("mainTag").equals("endGame")){
                    isStarted = false;
                    JSONArray ar = js.getJSONArray("ready");
                    for (int i = 0; i<4; i++){
                        players[i] = ar.getString(i);
                    }
                }
            }
        }
            catch (Throwable cause) {
            System.out.println("connection error: " + cause.getMessage());
        }
    }

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(color_notGameField));
        g.fillRect(0,0, screenSize[0], screenSize[1]);
        g.setColor(Color.black);
        g.fillRect(game_field_start_point_x,game_field_start_point_y,game_field_size,game_field_size);
        g.setColor(Color.white);
        if (countdown == -1) {
            g.drawLine(game_field_start_point_x, game_field_start_point_y, game_field_size + game_field_start_point_x, game_field_size + game_field_start_point_y);
            g.drawLine(game_field_start_point_x, game_field_size + game_field_start_point_y, game_field_size + game_field_start_point_x, game_field_start_point_y);
//            g.drawString(Integer.toString(scores[0]), 390, 345);
//            g.drawString(Integer.toString(scores[1]), 390, 414);
//            g.drawString(Integer.toString(scores[2]), 360, 380);
//            g.drawString(Integer.toString(scores[3]), 425, 380);
        }
        else {
            g.drawString(Integer.toString(countdown), game_field_start_point_x + 370, game_field_start_point_y + 370);
        }

        g.setColor(Color.white);

        //bits
        g.fillRect(bits[0][0] + game_field_start_point_x,
                bits[0][0] + game_field_start_point_y + game_field_size/2 - bits[0][3]/2, bits[0][2], bits[0][3]);
        g.fillRect(bits[1][0] + game_field_start_point_x + game_field_size - bits[1][2],
                bits[1][1] + game_field_start_point_y + game_field_size/2 - bits[1][3]/2, bits[1][2], bits[1][3]);
        g.fillRect(bits[2][0] + game_field_start_point_x + game_field_size/2 - bits[2][2]/2,
                bits[2][1] + game_field_start_point_y, bits[2][2], bits[2][3]);
        g.fillRect(bits[3][0] + game_field_start_point_x + game_field_size/2 - bits[3][2]/2,
                bits[3][1] + game_field_start_point_y + game_field_size - bits[3][3], bits[3][2], bits[3][3]);

        //ball
        g.fillOval(game_field_size/2 + game_field_start_point_x - 5 + ball[0],
                game_field_size/2 + game_field_start_point_y - 5 + ball[1], 10, 10);
    }
}
