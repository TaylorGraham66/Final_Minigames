package minigames;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// Minigames by Taylor

public class Home {

    final public static Random rand = new Random();
    final public static Dimension SCREEN_SIZE = new Dimension(800, 400);

    static BasicFrame bf = new BasicFrame("Minigames");

    static Card home = bf.getCard();

    static String[][] home_layout = {
            {"name", "name", "name"},
            {"game1", "game2", "game3"},
            {"exit", "exit", "exit"}
    };

    // Method Name: makeBall
    // What it does: Creates a circular Picture object with a specified color and size.
    // Parameters:
    //   Color color - The color of the ball.
    //   int size - The size (diameter) of the ball.
    // Return Value: A Picture object representing a circular ball of the specified color and size.
    static Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }

    // Method Name: main
    // What it does: Initializes the main menu, sets up the buttons for each game, and handles the transitions between them.
    // Parameters:
    //   String[] args - Command line arguments (not used in this method).
    // Return Value: None
    public static void main(String[] args) {

        // Initialize the SpriteComponent for the main menu
        final SpriteComponent sc = new SpriteComponent();
        sc.setPreferredSize(SCREEN_SIZE);

        // Set the background image for the home screen
        home.setPainter(new BackgroundPainter(new Picture("home.jpg")));

        // Create and set up the button for "Ultimate Wave Survival" game
        final JButton button1 = new JButton("Ultimate Wave Survival");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WaveGameMain.wg1.showCard();
                WaveGameMain.wg1.requestFocus();
            }
        });

        // Create and set up the button for "Water Racer" game
        final JButton button2 = new JButton("Water Racer");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WaterRacerMain.wr1.showCard();
                WaterRacerMain.wr1.requestFocus();
            }
        });

        // Create and set up the button for "Space Lander" game
        final JButton button3 = new JButton("Space Lander");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderMain.sl1.showCard();
                LanderMain.sl1.requestFocus();
            }
        });

        // Create and set up the exit button
        final JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create a picture for the main menu
        final Picture mg = new Picture("minigames.png");

        // Set up the layout and add the components (buttons and picture) to the home screen
        home.setStringLayout(home_layout);
        home.add("name", mg.makeButton());
        home.add("game1", button1);
        home.add("game2", button2);
        home.add("game3", button3);
        home.add("exit", exit);

        // Initialize each game and their main frames
        WaveGameMain wg = new WaveGameMain();
        WaterRacerMain wr = new WaterRacerMain();
        LanderMain lm = new LanderMain();

        // Show the main frame
        bf.show();
    }
}