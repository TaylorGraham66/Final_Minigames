package minigames;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Painter;
import basicgraphics.images.Picture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class LanderMain {

    static Card sl1 = Home.bf.getCard();
    static Card lvl1 = Home.bf.getCard();
    static Card lvl2 = Home.bf.getCard();
    static Card lvl3 = Home.bf.getCard();
    static Card lvl4 = Home.bf.getCard();
    static Card lvl5 = Home.bf.getCard();
    static Card lvl6 = Home.bf.getCard();


    static basicgraphics.images.Painter painter = new Painter() {
        @Override
        public void paint(Graphics g, Dimension d) {
            g.setColor(Color.black);
            g.fillRect(0, 0, d.width, d.height);
            final int NUM_STARS = d.width * d.height / 500;
            Random rand = new Random();
            rand.setSeed(0);
            g.setColor(Color.white);
            for (int i = 0; i < NUM_STARS; i++) {
                int diameter = rand.nextInt(5) + 1;
                int xpos = (int) (rand.nextDouble() * d.width);
                int ypos = (int) (rand.nextDouble() * d.height);
                g.fillOval(xpos, ypos, diameter, diameter);
            }
        }
    };

    public LanderMain() {

        sl1.setPainter(new BackgroundPainter(new Picture("freespace.png")));

        String[][] sl_lables = {
                {"Title", "Title", "Title"},
                {"Level1", "Level2", "Level3"},
                {"Level4", "Level5", "Level6"},
                {"Return", "Return", "Return"}
        };
        sl1.setStringLayout(sl_lables);

        JLabel sl_title = new JLabel("Space Lander");
        sl_title.setForeground(Color.white);

        JButton sl_level1 = new JButton("Level 1");
        sl_level1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderLvl1 LL1 = new LanderLvl1();
                lvl1.showCard();
                lvl1.requestFocus();
                ClockWorker.initialize(7);
                ClockWorker.addTask(LanderLvl1.sc1.moveSprites());
            }
        });

        JButton sl_level2 = new JButton("Level 2");
        sl_level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderLvl2 LL2 = new LanderLvl2();
                lvl2.showCard();
                lvl2.requestFocus();
                ClockWorker.initialize(7);
                ClockWorker.addTask(LanderLvl2.sc2.moveSprites());
            }
        });

        JButton sl_level3 = new JButton("Level 3");
        sl_level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderLvl3 LL3 = new LanderLvl3();
                lvl3.showCard();
                lvl3.requestFocus();
                ClockWorker.initialize(7);
                ClockWorker.addTask(LanderLvl3.sc3.moveSprites());
            }
        });

        JButton sl_level4 = new JButton("Level 4");
        sl_level4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderLvl4 LL4 = new LanderLvl4();
                lvl4.showCard();
                lvl4.requestFocus();
                ClockWorker.initialize(7);
                ClockWorker.addTask(LanderLvl4.sc4.moveSprites());
            }
        });
        JButton sl_level5 = new JButton("Level 5");
        sl_level5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderLvl5 LL5 = new LanderLvl5();
                lvl5.showCard();
                lvl5.requestFocus();
                ClockWorker.initialize(7);
                ClockWorker.addTask(LanderLvl5.sc5.moveSprites());
            }
        });
        JButton sl_level6 = new JButton("Level 6");
        sl_level6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LanderLvl6 LL6 = new LanderLvl6();
                lvl6.showCard();
                lvl6.requestFocus();
                ClockWorker.initialize(7);
                ClockWorker.addTask(LanderLvl6.sc6.moveSprites());
            }
        });

        JButton sl_return = new JButton("Return");
        sl_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home.home.showCard();
                Home.home.requestFocus();
            }
        });

        sl1.add("Title", sl_title);
        sl1.add("Level1", sl_level1);
        sl1.add("Level2", sl_level2);
        sl1.add("Level3", sl_level3);
        sl1.add("Level4", sl_level4);
        sl1.add("Level5", sl_level5);
        sl1.add("Level6", sl_level6);
        sl1.add("Return", sl_return);
    }
}