package minigames;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

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

    static Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }

    public static void main(String[] args) {

        final SpriteComponent sc = new SpriteComponent();
        sc.setPreferredSize(SCREEN_SIZE);
        home.setPainter(new BackgroundPainter(new Picture("home.jpg")));

        final JButton button1 = new JButton("Ultimate Wave Survival");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WaveGameMain.wg1.showCard();
                WaveGameMain.wg1.requestFocus();
            }
        });
        final JButton button2 = new JButton("Water Racer");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WaterRacerMain.wr1.showCard();
                WaterRacerMain.wr1.requestFocus();
            }
        });

        final JButton button3 = new JButton("Space Lander");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderMain.sl1.showCard();
                LanderMain.sl1.requestFocus();
            }
        });

        final JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        final Picture mg = new Picture("minigames.png");

        home.setStringLayout(home_layout);
        home.add("name", mg.makeButton());
        home.add("game1", button1);
        home.add("game2", button2);
        home.add("game3", button3);
        home.add("exit", exit);

        WaveGameMain wg = new WaveGameMain();
        WaterRacerMain wr = new WaterRacerMain();
        LanderMain lm = new LanderMain();

        bf.show();
    }
}