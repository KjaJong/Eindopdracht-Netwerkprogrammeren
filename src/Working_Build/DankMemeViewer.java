package Working_Build;

import javax.swing.*;
import java.awt.*;

public class DankMemeViewer {
    public static JFrame frame;
    public static Image img = null;
    private static int width;
    private static int height;

    public DankMemeViewer(Image img, int width, int height){
        this.img = img;
        this.width = width;
        this.height = height;
        main();
    }



    public static void main()
    {
        frame = new JFrame("Dank Meme Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new DankMemePanel();
        frame.setPreferredSize(new Dimension(width, height));
        frame.getContentPane().add(panel);

        /**
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File("/home/hugo/Downloads/dank_meme.jpg"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        **/

        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);
    }
}

class DankMemePanel extends JPanel
{
    private static boolean centered = false;

    public DankMemePanel()
    {
        setPreferredSize( new Dimension(600,460) );
    }

    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


    }
}