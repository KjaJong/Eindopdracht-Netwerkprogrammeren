package Working_Build;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by hugo on 6/13/16.
 */
public class Browser {

    /**
    private ArrayList<Thumbnail> thumbnails;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
     **/

    public Browser(ArrayList<Thumbnail> thumbnails, ObjectInputStream objIn, ObjectOutputStream objOut){
        JFrame browseFrame = new JFrame("World Meme Database");
        JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new GridLayout(5, 5));


        JButton[] buttons = new JButton[thumbnails.size()];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new JButton();
            buttons[i].setIcon(thumbnails.get(i).getThumbnail());
            buttons[i].setPreferredSize(new Dimension(150, 150));
            int finalI = i;
            buttons[i].addActionListener(event ->{
                try{
                    System.out.println("Image: " + thumbnails.get(finalI).getPath());
                    objOut.writeObject(thumbnails.get(finalI).getPath());
                    Meme meme = (Meme) objIn.readObject();

                    DankMemeViewer dankMemeViewer = new DankMemeViewer(meme.getImage(), meme.getImage().getWidth()+10, meme.getImage().getHeight()+20);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            });
            browsePanel.add(buttons[i]);
        }



        browseFrame.add(browsePanel);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setSize(new Dimension(640, 480));
        browseFrame.setVisible(true);

        browseFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
                    objOut.writeObject("stop");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });
    }
}
