package Working_Build;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Menno on 8-6-2016.
 */
public class Gui extends JPanel{
    protected JFrame guiFrame;
    private SpringLayout springLayout;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel diaPanel;



    public Gui(){
        guiFrame = new JFrame("Meme database");
        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setSize(new Dimension(640, 480));
        initPanel();
        guiFrame.validate();
        guiFrame.setVisible(true);
    }

    //<editor-fold desc="And now, magic with the initPanel methods.">
    private void initPanel(){
        springLayout = new SpringLayout();
        setLayout(springLayout);

        guiFrame.setContentPane(this);

        initTitlePanel();
        initDiaPanel();
        initButtonPanel();

        createLayout();
    }

    private void initTitlePanel(){
        titlePanel = new JPanel();
        Font font = new Font("Impact", Font.BOLD, 50);
        JLabel label = new JLabel();
        label.setText("GENERAL MEME DATABASE");
        label.setFont(font);
        titlePanel.add(label);
        label.setVisible(true);
        add(titlePanel);
    }

    private void initDiaPanel(){
        diaPanel = new JPanel();
        //TODO add the dia functionality
        add(diaPanel);
    }

    private void initButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        //<editor-fold desc="Button generation. Hiding all the auto code down here.">
        JButton startButton = new JButton("Access to database.", new ImageIcon("src/Resources/Miku.jpg"));
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setForeground(Color.white);
        startButton.setHorizontalTextPosition(JButton.CENTER);
        startButton.setVerticalTextPosition(JButton.CENTER);

        JButton editButton = new JButton("Add/Remove meme's", new ImageIcon("src/Resources/Miku.jpg"));
        editButton.setFont(new Font("Arial", Font.PLAIN, 20));
        editButton.setForeground(Color.white);
        editButton.setHorizontalTextPosition(JButton.CENTER);
        editButton.setVerticalTextPosition(JButton.CENTER);

        JButton exitButton = new JButton("Exit database", new ImageIcon("src/Resources/Miku.jpg"));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setForeground(Color.white);
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);

        JButton mysteryButton = new JButton("Dunno, this probably makes you a sandwich or something.", new ImageIcon("src/Resources/Miku.jpg"));
        mysteryButton.setFont(new Font("Arial", Font.PLAIN, 20));
        mysteryButton.setForeground(Color.white);
        mysteryButton.setHorizontalTextPosition(JButton.CENTER);
        mysteryButton.setVerticalTextPosition(JButton.CENTER);

        startButton.addActionListener(e -> {
            //TODO: send search request
        });

        editButton.addActionListener(e -> {
            //TODO: Send request to edit resources
        });

        exitButton.addActionListener(e ->{
            //TODO: Send exit request
        });

        mysteryButton.addActionListener(e -> {
            //TODO: Send the rickrolls request
        });
        //</editor-fold>

        buttonPanel.add(startButton);
        buttonPanel.add(editButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(mysteryButton);

        add(buttonPanel);
    }

    private void createLayout(){
        springLayout.putConstraint(SpringLayout.NORTH,buttonPanel,0,SpringLayout.NORTH,this);
        springLayout.putConstraint(SpringLayout.EAST,buttonPanel, guiFrame.getWidth()/3,SpringLayout.WEST,this);
        springLayout.putConstraint(SpringLayout.WEST,buttonPanel,0,SpringLayout.WEST,this);
        springLayout.putConstraint(SpringLayout.SOUTH,buttonPanel,0,SpringLayout.SOUTH,this);

        springLayout.putConstraint(SpringLayout.NORTH,titlePanel, guiFrame.getHeight()/3,SpringLayout.NORTH,this);
        springLayout.putConstraint(SpringLayout.EAST,titlePanel,0,SpringLayout.EAST,this);
        springLayout.putConstraint(SpringLayout.SOUTH,titlePanel,0,SpringLayout.SOUTH,this);
        springLayout.putConstraint(SpringLayout.WEST,titlePanel,0,SpringLayout.EAST,buttonPanel);

        springLayout.putConstraint(SpringLayout.NORTH,titlePanel,0,SpringLayout.NORTH,this);
        springLayout.putConstraint(SpringLayout.EAST,titlePanel,0,SpringLayout.EAST,this);
        springLayout.putConstraint(SpringLayout.SOUTH,titlePanel,0,SpringLayout.NORTH,titlePanel);
        springLayout.putConstraint(SpringLayout.WEST,titlePanel,0,SpringLayout.EAST,buttonPanel);
    }
    //</editor-fold>
}
