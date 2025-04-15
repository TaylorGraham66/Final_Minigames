package minigames;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WaterRacerMain {

    static Card wr1 = Home.bf.getCard();
    static Card wr2 = Home.bf.getCard();
    static Card wr3 = Home.bf.getCard();

    static int counterNumber = 0;
    final int[] counter = {0};
    final int[] elapsedTime = {0};

    Task timerTask = new Task() {
        double playerSpeed = 2.0;
        int spawnInterval = 1500;
        int timeSinceLastSpawn = 0;

        @Override
        public void run() {
            elapsedTime[0] += 7;
            timeSinceLastSpawn += 7;

            if (elapsedTime[0] >= 1000) {
                counter[0]++;
                counterNumber++;
                elapsedTime[0] = 0;

                SwingUtilities.invokeLater(() -> {
                    wr_counter.setText("Time: " + counter[0]);
                });

                if (playerSpeed < 8.0) {
                    playerSpeed += 0.05;
                }

                wr_player.setBaseSpeed(playerSpeed);
            }

            if (counter[0] >= 5 && timeSinceLastSpawn >= spawnInterval) {
                spawnObstacle();
                timeSinceLastSpawn = 0;

                if (spawnInterval > 800) {
                    spawnInterval -= 10;
                }
            }
        }

        private void spawnObstacle() {
            int yPos = Home.rand.nextInt(0, 800);
            int xPos = Home.rand.nextInt(0, 2000);
            new WaterRacerOb(sc2.getScene(), xPos, yPos, xPos + 1, yPos + 1);
        }
    };

    JLabel wr_counter;
    final SpriteComponent sc2 = new SpriteComponent();
    WaterRacerPlayer wr_player = new WaterRacerPlayer(sc2.getScene());

    public WaterRacerMain() {
        sc2.setPreferredSize(Home.SCREEN_SIZE);
        sc2.getScene().setPainter(new BackgroundPainter(new Picture("racer.png")));
        sc2.getScene().setBackgroundSize(new Dimension(2000, 900));
        sc2.getScene().periodic_x = true;

        ClockWorker.addTask(timerTask);

        wr1.add(sc2);

        String[][] sr_labels = {
                {"WRTitle"},
                {"WRButton"},
                {"WRReturn"}
        };
        wr1.setStringLayout(sr_labels);

        JLabel wr_title = new JLabel("Wave Rider");
        wr1.add(wr_title);
        JButton wr_start = new JButton("Start");
        wr_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wr2.showCard();
                wr2.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        JButton wr_return = new JButton("Return");
        wr_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home.home.showCard();
                Home.home.requestFocus();
            }
        });
        wr1.add("WRButton", wr_start);
        wr1.add("WRReturn", wr_return);
        wr1.setPainter(new BackgroundPainter(new Picture("racer.png")));
        wr3.setPainter(new BackgroundPainter(new Picture("racer.png")));

        wr2.setLayout(new BasicLayout());
        int totalCells = 100;
        int labelHeight = 5;
        int labelYPos = 0;
        wr_counter = new JLabel("Time: " + counter[counterNumber]);
        wr2.add("x=0,y=" + labelYPos + ",w=100,h=" + labelHeight, wr_counter);
        wr2.add("x=0,y=" + (labelYPos + labelHeight) + ",w=100,h=" + (totalCells - labelHeight), sc2);

        sc2.getScene().setFocus(wr_player);
        wr2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                double speed = wr_player.getBaseSpeed();
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    wr_player.setVel(speed, 0);
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    wr_player.setVel(speed, -speed);
                } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    wr_player.setVel(speed, speed);
                }
            }
        });

        sc2.addSpriteSpriteCollisionListener(WaterRacerPlayer.class, WaterRacerOb.class, new SpriteSpriteCollisionListener<WaterRacerPlayer, WaterRacerOb>() {
            public void collision(WaterRacerPlayer sp1, WaterRacerOb sp2) {
                JOptionPane.showMessageDialog(sc2, "You died!");
                System.exit(0);
            }
        });
        ClockWorker.addTask(sc2.moveSprites());
    }
}