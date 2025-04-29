package minigames;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;
import basicgraphics.SpriteSpriteCollisionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WaveGameMain {
    WaveGamePlayer wg_player;
    static Card wg1 = Home.bf.getCard();
    static Card wg2 = Home.bf.getCard();
    static Card wg3 = Home.bf.getCard();

    public static int wg_enemies = 1;
    public static int wg_killCount = 0;
    public static int wg_rounds = 1;
    static JLabel wg_waveLabel = new JLabel("Wave: 1");

    // Method Name: wg_spawn
    // What it does: Spawns a number of enemies (wg_enemies) in the game by creating new WaveGameEnemy objects.
    // Parameters:
    //   SpriteComponent sc - The component where enemies are placed.
    // Return Value: None
    public static void wg_spawn(SpriteComponent sc) {
        for (int i = 0; i < wg_enemies; i++) {
            new WaveGameEnemy(sc.getScene());
        }
    }

    // Method Name: WaveGameMain (constructor)
    // What it does: Initializes the main game components, including UI layout, player movement, enemy spawning, and collision detection. Sets up action listeners for buttons and key presses.
    // Parameters: None
    // Return Value: None
    public WaveGameMain() {
        final SpriteComponent sc1 = new SpriteComponent();
        sc1.setPreferredSize(Home.SCREEN_SIZE);
        sc1.getScene().setPainter(new BackgroundPainter(new Picture("dungeon.png")));

        final ReusableClip death = new ReusableClip("die.wav");

        wg1.setPainter(new BackgroundPainter(new Picture("dungeon.png")));
        wg3.setPainter(new BackgroundPainter(new Picture("dungeon.png")));

        String[][] wg_labels = {
                {"Title"},
                {"Button"},
                {"Return"}
        };
        wg1.setStringLayout(wg_labels);

        JLabel wg_title = new JLabel("Wave Game");
        wg_title.setForeground(Color.white);
        wg1.add(wg_title);
        JButton wg_jstart = new JButton("Start");
        wg_jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wg_player = new WaveGamePlayer(sc1.getScene());
                wg_rounds = 1;
                wg2.showCard();
                wg2.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        JButton wg_return = new JButton("Return");
        wg_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home.home.showCard();
                Home.home.requestFocus();
            }
        });
        wg1.add("Button", wg_jstart);
        wg1.add("Return", wg_return);

        wg2.setLayout(new BasicLayout());
        int totalCells = 100;
        int labelHeight = 5;
        int labelYPos = 0;
        wg2.add("x=0,y=" + labelYPos + ",w=100,h=" + labelHeight, wg_waveLabel);
        wg2.add("x=0,y=" + (labelYPos + labelHeight) + ",w=100,h=" + (totalCells - labelHeight), sc1);

        sc1.getScene().setFocus(wg_player);
        final double player_speed = 1.65;
        wg2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    wg_player.setVel(player_speed, 0);
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    wg_player.setVel(-player_speed, 0);
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    wg_player.setVel(0, -player_speed);
                } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    wg_player.setVel(0, player_speed);
                } else if (ke.getKeyCode() == ' ') {
                    final WaveGameProj bullet = new WaveGameProj(sc1.getScene(), wg_player);
                    bullet.setVel(wg_player.getVelX() * 1.5, wg_player.getVelY() * 1.5);
                    final int steps = 150;
                    ClockWorker.addTask(new Task(steps) {
                        @Override
                        public void run() {
                            if (iteration() == maxIteration()) {
                                bullet.destroy();
                            }
                        }
                    });
                } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    wg1.showCard();
                    wg1.requestFocus();
                } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    ClockWorker.finish();
                    wg3.showCard();
                    wg3.requestFocus();
                }
            }
        });

        String[][] wg_paused = {
                {"Round"},
                {"Resume"},
                {"Quit"}
        };
        wg3.setStringLayout(wg_paused);

        JButton wg_resume = new JButton("Resume");
        wg3.add(wg_resume);
        wg_resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wg2.showCard();
                wg2.requestFocus();
                wg_rounds = 0;
                wg_enemies = 0;
                ClockWorker.initialize(7);
            }
        });
        JButton wg_quit = new JButton("Quit");
        wg_quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home.home.showCard();
                Home.home.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        wg3.add("Resume", wg_resume);
        wg3.add("Quit", wg_quit);

        wg_spawn(sc1);

        sc1.addSpriteSpriteCollisionListener(WaveGameEnemy.class, WaveGameEnemy.class, new SpriteSpriteCollisionListener<WaveGameEnemy, WaveGameEnemy>() {
            @Override
            public void collision(WaveGameEnemy sp1, WaveGameEnemy sp2) {
                double vx = sp1.getVelX();
                double vy = sp1.getVelY();
                sp1.setVel(sp2.getVelX(), sp2.getVelY());
                sp2.setVel(vx, vy);
            }
        });

        sc1.addSpriteSpriteCollisionListener(WaveGameEnemy.class, WaveGameProj.class, new SpriteSpriteCollisionListener<WaveGameEnemy, WaveGameProj>() {
            public void collision(WaveGameEnemy sp1, WaveGameProj sp2) {
                sp1.destroy();
                sp2.destroy();
                wg_killCount++;
                death.playOverlapping();

                if (wg_killCount >= wg_enemies) {
                    wg_rounds++;
                    wg_enemies = (int) Math.ceil(wg_enemies * 1.5);
                    wg_killCount = 0;
                    wg_waveLabel.setText("Wave: " + wg_rounds);
                    wg_spawn(sc1);
                    JOptionPane pane = new JOptionPane("You have made it to round " + wg_rounds);
                }
            }
        });

        sc1.addSpriteSpriteCollisionListener(WaveGameEnemy.class, WaveGamePlayer.class, new SpriteSpriteCollisionListener<WaveGameEnemy, WaveGamePlayer>() {
            public void collision(WaveGameEnemy sp1, WaveGamePlayer sp2) {
                int random = Home.rand.nextInt(0, 5);
                if (random == 0) {
                    JOptionPane.showMessageDialog(sc1,"You got eaten! You reached wave: " + wg_rounds);
                } else if (random == 1) {
                    JOptionPane.showMessageDialog(sc1,"You got murdered! You reached wave: " + wg_rounds);
                } else if (random == 2) {
                    JOptionPane.showMessageDialog(sc1,"You were devoured by the squids! You reached wave: " + wg_rounds);
                } else if (random == 3) {
                    JOptionPane.showMessageDialog(sc1,"You let yourself die! You reached wave: " + wg_rounds);
                } else if (random == 4) {
                    JOptionPane.showMessageDialog(sc1,"You died! You reached wave: " + wg_rounds);
                }
                System.exit(0);
                sp2.destroy();
                wg1.showCard();
                wg1.requestFocus();
            }
        });

        ClockWorker.addTask(sc1.moveSprites());
    }
}