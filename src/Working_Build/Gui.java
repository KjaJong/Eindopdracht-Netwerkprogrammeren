package Working_Build;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * Created by Menno on 8-6-2016.
 */
public class Gui extends JPanel{
    protected JFrame guiFrame;
    private SpringLayout springLayout;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel diaPanel;
    private Client client;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private ImageIcon buttonImage;


    public Gui(Client client) throws IOException {
        this.client = client;
        //in = client.getIn();
        //out = client.getOut();
        this.socket = client.socket;
        initObjectStream();
        guiFrame = new JFrame("Meme database");
        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setSize(new Dimension(640, 480));
        initPanel();
        buttonImage = null;
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
        //Font font = new Font("Times New Roman", Font.BOLD, 50);
        //JLabel label = new JLabel("Image and Text");
        //label.setFont(font);

        try {
            titlePanel.add(new JLabel(new ImageIcon(retrieveImage())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //label.setText("GENERAL MEME DATABASE");
        //label.setVisible(true);

        add(titlePanel);
    }

    private void initDiaPanel() {
        diaPanel = new JPanel();
        //TODO add the dia functionality
        add(diaPanel);
    }

    private void initButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        //<editor-fold desc="Button generation. Hiding all the auto code down here.">
        JButton startButton = new JButton("Access to database.", buttonImage);
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setForeground(Color.black);
        startButton.setHorizontalTextPosition(JButton.CENTER);
        startButton.setVerticalTextPosition(JButton.CENTER);

        JButton editButton = new JButton("Add/Remove meme's", buttonImage);
        editButton.setFont(new Font("Arial", Font.PLAIN, 20));
        editButton.setForeground(Color.black);
        editButton.setHorizontalTextPosition(JButton.CENTER);
        editButton.setVerticalTextPosition(JButton.CENTER);

        JButton exitButton = new JButton("Exit database", buttonImage);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setForeground(Color.black);
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);


        JButton mysteryButton = new JButton("Not a rick roll.", buttonImage);
        mysteryButton.setFont(new Font("Arial", Font.PLAIN, 20));
        mysteryButton.setForeground(Color.black);
        mysteryButton.setHorizontalTextPosition(JButton.CENTER);
        mysteryButton.setVerticalTextPosition(JButton.CENTER);

        startButton.addActionListener(e -> {
            try{
                sendCommand(Commands.ACCESS);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        editButton.addActionListener(e -> {
            try{
                sendCommand(Commands.MODIFY);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        exitButton.addActionListener(e ->{
            try{
                socket.close();
                System.exit(0);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        mysteryButton.addActionListener(e -> {
            try{
                sendCommand(Commands.RICKROLL);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
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

        springLayout.putConstraint(SpringLayout.NORTH,diaPanel,0,SpringLayout.NORTH,this);
        springLayout.putConstraint(SpringLayout.EAST,diaPanel,0,SpringLayout.EAST,this);
        springLayout.putConstraint(SpringLayout.SOUTH,diaPanel,0,SpringLayout.NORTH,titlePanel);
        springLayout.putConstraint(SpringLayout.WEST,diaPanel,0,SpringLayout.EAST,buttonPanel);
    }
    //</editor-fold>

    private void sendCommand(Commands commands) throws IOException{
        //ObjectOutputStream command = new ObjectOutputStream(socket.getOutputStream());
        //command.flush();
        //initObjectStream();
        objOut.writeObject(commands);
        //objOut.close();
    }

    private void initObjectStream() throws IOException {
        objOut = new ObjectOutputStream(socket.getOutputStream());
        objOut.flush();
    }

    private BufferedImage retrieveImage() throws IOException {
        objIn = new ObjectInputStream(socket.getInputStream());
        try {
            Meme meme = (Meme) objIn.readObject();
            return meme.getImage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
