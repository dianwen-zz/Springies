import java.awt.Dimension;

import javax.swing.JFrame;

import utilities.JGameInteractions;

public class Main
{
    // constants
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Springies!";

    /**
     * main --- where the program starts
     * 
     * @param args
     */
    public static void main (String args[])
    {
        // view of user's content
        final JGameInteractions sp = new JGameInteractions();
        // container that will work with user's OS
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(sp);
        // display them
        frame.pack();
        frame.setVisible(true);
    }
}
