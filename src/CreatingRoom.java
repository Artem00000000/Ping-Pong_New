import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreatingRoom {

    Font font = new Font("Serif", Font.BOLD, 30);

    CreatingRoom(JFrame window1, JFrame window){
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setTitle("Creating room");
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        JLabel lab = new JLabel();
        lab.setForeground(Color.white);
        lab.setBackground(Color.black);
        lab.setSize(800, 600);
        window.add(lab);
        //JPanel panel = new JPanel();
        //panel.setSize(800, 600);
        //panel.setBackground(Color.BLACK);
        //window.add(panel);
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
        JButton button = new JButton("Create");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Нажата кнопка 2");
                String name = text1.getText();
                String password = new String(pass.getPassword());
                int count = Integer.valueOf(box.getSelectedItem().toString());
                if (! name.equals("") && ! password.equals("")) {
                    //rooms.add(new Room(name, count, password));
                    window.setVisible(false);
                    window1.setVisible(true);
                }
            }
        });
        lab.add(button);
        button.setBounds(250, 450, 300, 70);
        button.setBackground(Color.black);
        button.setForeground(Color.white);
    }
}
