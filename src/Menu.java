import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;

public class Menu extends JFrame {

    Font font1 = new Font("Serif", Font.BOLD, 30);
    Font font2 = new Font("Serif", Font.BOLD, 20);
    JLabel label = new JLabel();
    JFrame frame, frame1, frame2;
    String resolution = "800x800";

    boolean firstMenu_button8 = true;
    boolean firstMenu_button9 = true;

    public static void main(String[] args) {new Menu();}

    Menu(){
        frame = this;
        frame1 = new JFrame();
        frame2 = new JFrame();
        //frame1 = new JFrame();
        setTitle("Ping-Pong");
        setSize(800, 500);
        setBackground(Color.BLACK);
        setLocation(400, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.black);
        setForeground(Color.white);
        JButton button1 = new JButton("Play");
        JButton button2 = new JButton("Settings");
        JButton button3 = new JButton("Exit game");
        JButton button4 = new JButton("Online game");
        //JButton button5 = new JButton("Online game 4 players");
        JButton button6 = new JButton("Local game");
        //JButton button7 = new JButton("Local game 4 players");
        JButton button8 = new JButton("Back");
        JLabel label1 = new JLabel("Welcome to Ping-Pong");
        JLabel label2 = new JLabel("Screen resolution: ");
        String[] list = {"800x800", "1280x800", "1600x900", "1920x1080", "1920x1200", "2560x1440", "2560x1600", "3840x2160", "4096x2160", "fullscreen"};
        JComboBox box = new JComboBox(list);
        JSlider volume_slider = new JSlider(0, 100, 50);
        JLabel volume = new JLabel("Music volume");
        JLabel volume_value = new JLabel(Double.toString(volume_slider.getValue()));
        String[] list1 = {"player", "wall", "bot(easy)", "bot(medium)", "bot(hard)"};
        JComboBox box1 = new JComboBox(list1);
        JComboBox box2 = new JComboBox(list1);
        JComboBox box3 = new JComboBox(list1);
        JLabel labelBox1 = new JLabel("Bit 2: ");
        JLabel labelBox2 = new JLabel("Bit 3: ");
        JLabel labelBox3 = new JLabel("Bit 4: ");
        JButton button9 = new JButton("Save");
        label1.setForeground(Color.white);
        label.add(label1);
        label1.setFont(new Font("Serif", Font.BOLD, 40));
        label1.setBounds(204, 30, 392, 70);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 1");
                button1.setVisible(false);
                button2.setVisible(false);
                button3.setVisible(false);
                button4.setVisible(true);
                //button5.setVisible(true);
                button6.setVisible(true);
                //button7.setVisible(true);
                button8.setVisible(true);
                label1.setText("Choose the game mode");

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 2");
                button1.setVisible(false);
                button2.setVisible(false);
                button3.setVisible(false);
                button8.setVisible(true);
                label1.setText("Settings");
                label1.setBounds(332, 30, 136, 70);
                label2.setVisible(true);
                box.setVisible(true);
                button9.setVisible(true);
                volume_slider.setVisible(true);
                volume.setVisible(true);
                volume_value.setVisible(true);
                firstMenu_button9 = true;
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 3");
                System.exit(0);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 4");
                //firstMenu = false;
                //button4.setVisible(false);
                //button5.setVisible(false);
                //button6.setVisible(false);
                //button7.setVisible(false);
                //button8.setVisible(false);
                button1.setVisible(false);
                button2.setVisible(false);
                button3.setVisible(false);
                button4.setVisible(true);
                //button5.setVisible(true);
                button6.setVisible(true);
                //button7.setVisible(true);
                button8.setVisible(true);
                label1.setText("Choose the game mode");
                label1.setBounds(204, 30, 392, 70);
                setVisible(false);
                frame2 = new JFrame();
                CSVmake csv = new CSVmake(frame, frame2);
                //frame2.add(csv);
            }
        });
//        button5.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Нажата кнопка 5");
//                button4.setVisible(false);
//                button5.setVisible(false);
//                button6.setVisible(false);
//                button7.setVisible(false);
//                button8.setVisible(false);
//            }
//        });
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 6");
                firstMenu_button8 = false;
                firstMenu_button9 = false;
                button4.setVisible(false);
                //button5.setVisible(false);
                button6.setVisible(false);
                //button7.setVisible(false);
                button8.setVisible(true);
                label1.setText("Customize your game");
                label1.setBounds(206, 10, 388, 70);
                box1.setVisible(true);
                labelBox1.setVisible(true);
                box2.setVisible(true);
                labelBox2.setVisible(true);
                box3.setVisible(true);
                labelBox3.setVisible(true);
                button9.setText("Start game");
                button9.setVisible(true);
                button9.setBounds(250, 350, 300, 70);

//                frame1 = new JFrame();
//                Game game = new Game(2, frame, frame1, resolution);
//                frame1.add(game);
            }
        });
//        button7.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Нажата кнопка 7");
//                button1.setVisible(true);
//                button2.setVisible(true);
//                button3.setVisible(true);
//                button4.setVisible(false);
//                button5.setVisible(false);
//                button6.setVisible(false);
//                button7.setVisible(false);
//                button8.setVisible(false);
//                label1.setText("Welcome to Ping-Pong");
//                setVisible(false);
//                frame1 = new JFrame();
//                Game game = new Game(4, frame, frame1, resolution);
//                frame1.add(game);
//            }
//        });
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 8");
                if (firstMenu_button8) {
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                    button4.setVisible(false);
                    //button5.setVisible(false);
                    button6.setVisible(false);
                    //button7.setVisible(false);
                    button8.setVisible(false);
                    button9.setVisible(false);
                    label2.setVisible(false);
                    box.setVisible(false);
                    volume_slider.setVisible(false);
                    volume.setVisible(false);
                    volume_value.setVisible(false);
                    label1.setText("Welcome to Ping-Pong");
                    label1.setBounds(204, 30, 392, 70);
                }
                else{
                    button1.setVisible(false);
                    button2.setVisible(false);
                    button3.setVisible(false);
                    button4.setVisible(true);
                    //button5.setVisible(true);
                    button6.setVisible(true);
                    //button7.setVisible(true);
                    button8.setVisible(true);
                    label1.setText("Choose the game mode");
                    label1.setBounds(204, 30, 392, 70);
                    firstMenu_button8 = true;
                    firstMenu_button9 = true;
                    box1.setVisible(false);
                    labelBox1.setVisible(false);
                    box2.setVisible(false);
                    labelBox2.setVisible(false);
                    box3.setVisible(false);
                    labelBox3.setVisible(false);
                    button9.setBounds(250, 350, 300, 70);
                    button9.setVisible(false);
                    button9.setText("Save");

                }
            }
        });
        label.setFont(font1);
        label.setOpaque(true);
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
        label.add(button1);
        button1.setBounds(250, 130, 300, 70);
        button1.setBackground(Color.black);
        button1.setForeground(Color.white);
        button1.setFont(font1);
        label.add(button2);
        button2.setBounds(250, 240, 300, 70);
        button2.setBackground(Color.black);
        button2.setForeground(Color.white);
        button2.setFont(font1);
        label.add(button3);
        button3.setBounds(250, 350, 300, 70);
        button3.setBackground(Color.black);
        button3.setForeground(Color.white);
        button3.setFont(font1);

        label.add(button4);
        button4.setBounds(250, 130, 300, 70);
        button4.setBackground(Color.black);
        button4.setForeground(Color.white);
        button4.setFont(font1);
//        label.add(button5);
//        button5.setBounds(450, 130, 250, 70);
//        button5.setBackground(Color.black);
//        button5.setForeground(Color.white);
//        button5.setFont(font2);
        label.add(button6);
        button6.setBounds(250, 240, 300, 70);
        button6.setBackground(Color.black);
        button6.setForeground(Color.white);
        button6.setFont(font1);
//        label.add(button7);
//        button7.setBounds(450, 240, 250, 70);
//        button7.setBackground(Color.black);
//        button7.setForeground(Color.white);
//        button7.setFont(font2);
        label.add(button8);
        button8.setBounds(50, 400, 100, 50);
        button8.setBackground(Color.black);
        button8.setForeground(Color.white);
        button8.setFont(font2);


        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        button4.setVisible(false);
        //button5.setVisible(false);
        button6.setVisible(false);
        //button7.setVisible(false);
        button8.setVisible(false);


        label.add(label2);
        label2.setForeground(Color.white);
        label2.setFont(font1);
        label2.setBounds(50, 190, 250, 50);
        label.add(box);
        box.setBounds(310, 190, 200, 50);
        box.setBackground(Color.black);
        box.setForeground(Color.white);
        box.setFont(font1);
        label2.setVisible(false);
        box.setVisible(false);

        label.add(labelBox1);
        labelBox1.setForeground(Color.white);
        labelBox1.setFont(font1);
        labelBox1.setBounds(50, 110, 250, 50);
        label.add(box1);
        box1.setBounds(310, 110, 200, 50);
        box1.setBackground(Color.black);
        box1.setForeground(Color.white);
        box1.setFont(font1);
        labelBox1.setVisible(false);
        box1.setVisible(false);

        label.add(labelBox2);
        labelBox2.setForeground(Color.white);
        labelBox2.setFont(font1);
        labelBox2.setBounds(50, 190, 250, 50);
        label.add(box2);
        box2.setBounds(310, 190, 200, 50);
        box2.setBackground(Color.black);
        box2.setForeground(Color.white);
        box2.setFont(font1);
        labelBox2.setVisible(false);
        box2.setVisible(false);

        label.add(labelBox3);
        labelBox3.setForeground(Color.white);
        labelBox3.setFont(font1);
        labelBox3.setBounds(50, 270, 250, 50);
        label.add(box3);
        box3.setBounds(310, 270, 200, 50);
        box3.setBackground(Color.black);
        box3.setForeground(Color.white);
        box3.setFont(font1);
        labelBox3.setVisible(false);
        box3.setVisible(false);


        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 9");
                if (firstMenu_button9) {
                    resolution = box.getSelectedItem().toString();
                }
                else{
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                    button4.setVisible(false);
                    //button5.setVisible(true);
                    button6.setVisible(false);
                    //button7.setVisible(true);
                    button8.setVisible(false);
                    box1.setVisible(false);
                    labelBox1.setVisible(false);
                    box2.setVisible(false);
                    labelBox2.setVisible(false);
                    box3.setVisible(false);
                    labelBox3.setVisible(false);
                    button9.setBounds(250, 350, 300, 70);
                    button9.setVisible(false);
                    button9.setText("Save");
                    setVisible(false);
                    label1.setText("Choose the game mode");
                    label1.setBounds(204, 30, 392, 70);
                    firstMenu_button8 = true;
                    firstMenu_button9 = true;
                    frame1 = new JFrame();
                    Game game = new Game(2, frame, frame1, resolution, box1.getSelectedItem().toString(),
                            box2.getSelectedItem().toString(), box3.getSelectedItem().toString());
                    frame1.add(game);
                }
//                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//                int width = (int) screenSize.getWidth();
//                int height = (int) screenSize.getHeight();
//                System.out.println(width + "  " + height);
//                if (! resolution.equals("fullscreen")) {
//                    String[] parts = resolution.split("x");
//                    int w = Integer.valueOf(parts[0]);
//                    int h = Integer.valueOf(parts[1]);
//                    System.out.println(w + "  " + h);
//                    if (w<width && h<height) {
//                        frame1.setSize(w, h);
//                        frame1.setLocation((width - w) / 2, (height - h) / 2);
//                    }
//                }
//                else{
//                    frame1.setSize(width, height);
//                }
            }
        });
        label.add(button9);
        button9.setBounds(250, 350, 300, 70);
        button9.setBackground(Color.black);
        button9.setForeground(Color.white);
        button9.setFont(font1);
        button9.setVisible(false);


        label.add(volume);
        volume.setForeground(Color.white);
        volume.setBackground(Color.black);
        volume.setFont(font1);
        volume.setBounds(50, 250, 250, 50);
        label.add(volume_slider);
        volume_slider.setBounds(310, 250, 200, 50);
        volume_slider.setBackground(Color.black);
        volume_slider.setForeground(Color.white);
        volume_slider.setFont(font1);
        label.add(volume_value);
        volume_value.setForeground(Color.white);
        volume_value.setBackground(Color.black);
        volume_value.setFont(font1);
        volume_value.setBounds(550, 250, 80, 50);
        volume.setVisible(false);
        volume_slider.setVisible(false);
        volume_value.setVisible(false);
        volume_slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider)e.getSource()).getValue();
                volume_value.setText(Integer.toString(value));
            }
        });


        setResizable(false);
        setVisible(true);
    }
}
