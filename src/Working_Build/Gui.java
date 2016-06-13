package Working_Build;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.ArrayList;

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
        this.socket = client.socket;
        guiFrame = new JFrame("Meme database");
        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setSize(new Dimension(640, 480));
        initObjectStream();
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

        JButton exitButton = new JButton("Exit database", buttonImage);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setForeground(Color.black);
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);

        JButton searchButton = new JButton("Search the database", buttonImage);
        searchButton.setFont(new Font("Arial", Font.PLAIN, 20));
        searchButton.setForeground(Color.black);
        searchButton.setHorizontalTextPosition(JButton.CENTER);
        searchButton.setVerticalTextPosition(JButton.CENTER);


        JButton mysteryButton = new JButton("Not a rick roll.", buttonImage);
        mysteryButton.setFont(new Font("Arial", Font.PLAIN, 20));
        mysteryButton.setForeground(Color.black);
        mysteryButton.setHorizontalTextPosition(JButton.CENTER);
        mysteryButton.setVerticalTextPosition(JButton.CENTER);

        startButton.addActionListener(e -> {
            try{
                sendCommand(Commands.ACCESS);

                ArrayList<Thumbnail> thumbnails = (ArrayList<Thumbnail>) objIn.readObject();

                Browser browser = new Browser(thumbnails, objIn, objOut);

            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        exitButton.addActionListener(e ->{
            try{
                //socket.close();
                System.exit(0);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

        searchButton.addActionListener(e ->{
            try {
                sendCommand(Commands.MODIFY);
                String categorie = JOptionPane.showInputDialog(null, "What categorie would you like to search for?", "Search by categorie",
                        JOptionPane.QUESTION_MESSAGE);
                synchronized (this){
                    this.notify();
                }
                objOut.writeUTF(categorie);
                ArrayList<Meme> memesFound = (ArrayList<Meme>)objIn.readObject();

                if(memesFound.size() > 0){
                    JOptionPane.showMessageDialog(null, "Found: " + memesFound.size() + "memes within the specified categorie." + '\n' + "Now showing all the meme's found.",
                            "Search result", JOptionPane.INFORMATION_MESSAGE);
                    memesFound.forEach(Meme -> {
                        DankMemeViewer dankMemeViewer = new DankMemeViewer(Meme.getImage(), Meme.getImage().getWidth(), Meme.getImage().getHeight());
                    });
                }
                else{
                    JOptionPane.showMessageDialog(null, "Found: " + memesFound.size() + "memes within the specified categorie." + '\n' + "There are no meme's to show.",
                            "Search result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch(IOException|ClassNotFoundException ex){
                ex.printStackTrace();
            }
        });

        mysteryButton.addActionListener(e -> {
            try{
                sendCommand(Commands.RICKROLL);
                BufferedImage img = retrieveImage();
                DankMemeViewer dankMemeViewer = new DankMemeViewer(img, img.getWidth()+10, img.getHeight()+20);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
        //</editor-fold>

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(searchButton);
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
        objOut.writeObject(commands);
    }

    private void initObjectStream() throws IOException {
        objOut = new ObjectOutputStream(socket.getOutputStream());
        objOut.flush();

        objIn = new ObjectInputStream(socket.getInputStream());
    }

    private BufferedImage retrieveImage() throws IOException {
        try {
            Meme meme = (Meme) objIn.readObject();
            return meme.getImage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
