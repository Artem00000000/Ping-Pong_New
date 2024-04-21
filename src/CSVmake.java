//package base;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;


public class CSVmake
{
    //public JFrame window = new JFrame();
    public JFrame window, window1;
    ArrayList<Room> rooms = new ArrayList<Room>();
    Font font = new Font("Serif", Font.BOLD, 30);
    Boolean creatingRoom = false;
    JScrollPane spane;
    JPanel panel;


    public CSVmake(JFrame window1, JFrame window)
    {
        this.window = window;
        this.window1 = window1;
        makeWindow();
        makeJPanel();
        makeFonWindow();
        doit();
    }


    private void makeWindow()
    {
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setTitle("CSV");
        window.setFont(new Font("Serif", Font.BOLD, 40));
        window.setLayout(null);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


    private void makeFonWindow()
    {
//        JLabel fon0 = new JLabel();
//        fon0.setBounds(0, 0, 800, 600);
//        fon0.setIcon(new ImageIcon(getClass().getResource("res/123.png")));
//        fon0.setLayout(null);
//        window.add(fon0);

        window.repaint();
    }


    private void makeJPanel()
    {
        int b = 15;

//        rooms.add(new Room(1, "ОСТ", "1231", 1, 2));
//        rooms.add(new Room(1, "-", "1236", 1, 2));
//        rooms.add(new Room(1, "говно", "1232", 2, 4));
//        rooms.add(new Room(1, "единственный", "1233", 1, 3));
//        rooms.add(new Room(1, "выход", "1234", 3, 4));
//        rooms.add(new Room(1, "-", "1237", 1, 2));
//        rooms.add(new Room(1, "отчисление", "1235", 1, 2));
        rooms.add(new Room(1, "room_1", "1231", 1, 2));
        rooms.add(new Room(1, "room_2", "1236", 1, 2));
        rooms.add(new Room(1, "room_3", "1232", 2, 4));
        rooms.add(new Room(1, "room_4", "1233", 1, 3));

//        panel = new JPanel();
        //jp.setPreferredSize(new Dimension(500,5+47*b));
//        panel.setOpaque(false);
//        panel.setLayout(null);

        //JScrollPane spane = new JScrollPane(scrol);
        spane = new JScrollPane();
        spane.setBounds(25,25,730,350);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        spane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//        spane.setViewportView(panel);
        JScrollBar bar = spane.getVerticalScrollBar();
        bar.setBackground(Color.BLACK);
        bar.setForeground(Color.black);

        window.add(spane);


        reWriteJPanel();

//        for (int i = 0; i < b; i++)
//        {
//            JButton menyBt = new JButton();
//            menyBt.setBounds(5, 5+i*47, 520, 42);
//            menyBt.setName(i+"");
//            menyBt.setText("Helloy");
//            menyBt.setForeground(Color.WHITE);
//            menyBt.setOpaque(false);
//            menyBt.setContentAreaFilled(false);
//            menyBt.setBorderPainted(true);
//            menyBt.setFocusPainted(false);
//            menyBt.setLayout(null);
//
//            jp.add(menyBt);
//        }


//        for (int i = 0; i < rooms.size(); i++){
//            JButton menyBt = new JButton();
//            Room room = rooms.get(i);
//            menyBt.setBounds(5, 5+i*47, 720, 42);
//            menyBt.setFont(font);
//            menyBt.setName(i+"");
//            menyBt.setText(room.name + "    players: " + room.count_players + "/" + room.max_count_players);
//            menyBt.setForeground(Color.WHITE);
//            menyBt.setOpaque(false);
//            menyBt.setContentAreaFilled(false);
//            menyBt.setBorderPainted(true);
//            menyBt.setFocusPainted(false);
//            menyBt.setLayout(null);
//
//            panel.add(menyBt);
//        }
//
//        window.add(spane);
//
//        window.repaint();
    }

    private void reWriteJPanel(){
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);
        spane.setViewportView(panel);
        panel.setPreferredSize(new Dimension(700,5+47*rooms.size()));
        for (int i = 0; i < rooms.size(); i++){
            JButton menyBt = new JButton();
            Room room = rooms.get(i);
            menyBt.setBounds(5, 5+i*47, 700, 42);
            //menyBt.setBounds(5, 5+i*47, 520, 42);
            menyBt.setFont(font);
            menyBt.setName(i+"");
            menyBt.setText(room.name + "    players: " + room.count_players + "/" + room.max_count_players);
            menyBt.setForeground(Color.WHITE);
            menyBt.setOpaque(false);
            menyBt.setContentAreaFilled(false);
            menyBt.setBorderPainted(true);
            menyBt.setFocusPainted(false);
            menyBt.setLayout(null);

            panel.add(menyBt);
        }

        window.repaint();
    }

    private void doit(){
        JLabel lab = new JLabel();
        lab.setVisible(false);
        lab.setForeground(Color.white);
        lab.setBackground(Color.black);
        lab.setSize(800, 600);
        window.add(lab);
        JLabel label = new JLabel("Creating room");
        label.setForeground(Color.white);
        lab.add(label);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setBounds(275, 30, 250, 50);
        JLabel label1 = new JLabel("Room name: ");
        lab.add(label1);
        label1.setForeground(Color.white);
        label1.setFont(font);
        label1.setBounds(40, 150, 200, 50);
        JTextField text1 = new JTextField(15);
        text1.setBackground(Color.black);
        text1.setForeground(Color.white);
        text1.setToolTipText("room name");
        text1.setBounds(250, 150, 450, 50);
        text1.setFont(font);
        lab.add(text1);
        JLabel label2 = new JLabel("Count players: ");
        lab.add(label2);
        label2.setForeground(Color.white);
        label2.setFont(font);
        label2.setBounds(40, 250, 200, 50);
        String[] list = {"2", "3", "4"};
        JComboBox box = new JComboBox(list);
        lab.add(box);
        box.setBounds(250, 250, 200, 50);
        box.setBackground(Color.black);
        box.setForeground(Color.white);
        box.setFont(font);
        JLabel label3 = new JLabel("Password: ");
        lab.add(label3);
        label3.setForeground(Color.white);
        label3.setFont(font);
        label3.setBounds(40, 350, 200, 50);
        JPasswordField pass = new JPasswordField(12);
        pass.setEchoChar('$');
        pass.setBackground(Color.black);
        pass.setForeground(Color.white);
        pass.setFont(font);
        pass.setBounds(250, 350, 200, 50);
        lab.add(pass);
        //lab.setVisible(false);


        JButton button = new JButton("Create new room");
        window.add(button);
        button.setFont(font);
        button.setBounds(250, 450, 300, 70);
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (! creatingRoom) {
                    System.out.println("Нажата кнопка 1");
                    creatingRoom = true;
                    button.setText("Create");
                    spane.setVisible(false);
                    pass.setText("");
                    box.setSelectedIndex(0);
                    text1.setText("");
                    lab.setVisible(true);
                }
                else{
                    System.out.println("Нажата кнопка 2");
                    String name = text1.getText();
                    String password = new String(pass.getPassword());
                    int count = Integer.valueOf(box.getSelectedItem().toString());
                    if (! name.equals("") && ! password.equals("")) {
                        rooms.add(new Room(1, name, password, 1, count));
                        creatingRoom = false;
                        button.setText("Create new room");
                        spane.setVisible(true);
                        lab.setVisible(false);
                        reWriteJPanel();
                    }
                }
                //window.setVisible(false);
                //CreatingRoom();
                //JFrame window1 = new JFrame();
                //CreatingRoom creRoom = new CreatingRoom(window, window1);
            }
        });
        JButton button1 = new JButton("Back");
        window.add(button1);
        button1.setFont(font);
        button1.setBounds(30, 490, 100, 50);
        button1.setBackground(Color.black);
        button1.setForeground(Color.white);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (creatingRoom) {
                    System.out.println("Нажата кнопка 3");
                    creatingRoom = false;
                    button.setText("Create new room");
                    spane.setVisible(true);
                    lab.setVisible(false);
                    reWriteJPanel();
                }
                else{
                    window.setVisible(false);
                    window1.setVisible(true);
                }
                //window.setVisible(false);
                //CreatingRoom();
                //JFrame window1 = new JFrame();
                //CreatingRoom creRoom = new CreatingRoom(window, window1);
            }
        });
    }


    public static void main(String[] args)
    {
        //new CSVmake();
    }
}