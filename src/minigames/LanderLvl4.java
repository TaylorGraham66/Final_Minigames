package minigames;

import basicgraphics.BasicLayout;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.sounds.ReusableClip;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LanderLvl4 {
    static SpriteComponent sc4;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    // Constructor: LanderLvl4 - Sets up the third level of the lander game with moving and stationary obstacles, rocket controls, collision handling, and scene setup
    // Parameters: None
    // Return Value: None (constructor)
    public LanderLvl4() {
        sc4 = new SpriteComponent();
        sc4.setPreferredSize(Home.SCREEN_SIZE);
        sc4.getScene().setPainter(LanderMain.painter);

        // Initialize landing pad and player rocket
        LandingPad lp4 = new LandingPad(sc4.getScene(), 800, 500);
        LanderRocket rocketImg4 = new LanderRocket(sc4.getScene(), 100, 50);

        // Create stationary obstacles
        new LandingOb(sc4.getScene(), 300, 350, 60, 60);
        new LandingOb(sc4.getScene(), 600, 150, 100, 50);

        // Create horizontally moving obstacles
        LandingOb movingHorz1 = new LandingOb(sc4.getScene(), 200, 100, 50, 50, 2, 0);
        LandingOb movingHorz2 = new LandingOb(sc4.getScene(), 500, 400, 60, 60, -1.5, 0);
        LandingOb movingHorz3 = new LandingOb(sc4.getScene(), 100, 200, 40, 40, 1.8, 0);

        // Create vertically moving obstacles
        LandingOb movingVert1 = new LandingOb(sc4.getScene(), 300, 250, 40, 40, 0, 1.2);
        LandingOb movingVert2 = new LandingOb(sc4.getScene(), 400, 100, 70, 70, 0, -1.3);
        LandingOb movingVert3 = new LandingOb(sc4.getScene(), 700, 300, 50, 50, 0, 1.5);

        // Create diagonally moving obstacles
        LandingOb movingDiag1 = new LandingOb(sc4.getScene(), 100, 300, 60, 60, 1.2, 1.2);
        LandingOb movingDiag2 = new LandingOb(sc4.getScene(), 650, 200, 50, 50, -1.2, 1.1);
        LandingOb movingDiag3 = new LandingOb(sc4.getScene(), 200, 400, 70, 70, -1.5, -1.3);
        LandingOb movingDiag4 = new LandingOb(sc4.getScene(), 500, 100, 60, 60, 1.3, -1.1);

        // Create an array of all moving obstacles for batch updating
        LandingOb[] movingObstacles = {
                movingHorz1, movingHorz2, movingHorz3,
                movingVert1, movingVert2, movingVert3,
                movingDiag1, movingDiag2, movingDiag3, movingDiag4
        };

        // Timer: moveTimer - Updates the positions of all moving obstacles periodically
        // Parameters: ActionEvent e (triggered automatically by the timer)
        // Return Value: None
        Timer moveTimer = new Timer(20, e -> {
            for (LandingOb obstacle : movingObstacles) {
                obstacle.update();
            }
        });
        moveTimer.start();

        // Method Name: keyPressed - Handles user input for controlling the rocket (move left/right/up or quit)
        // Parameters: KeyEvent ke - the event triggered by a key press
        // Return Value: None
        KeyAdapter ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rocketImg4.setVel(.5, rocketImg4.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    rocketImg4.setVel(-.5, rocketImg4.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    flame.playOverlapping();
                    rocketImg4.setVel(0, -.4);
                    rocketImg4.setPicture(rocketImg4.rocketWithFlame);
                } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg4.setPicture(rocketImg4.rocket);
            }
        };

        // Sprite Collision Listener: Rocket and Landing Pad - Handles landing success or failure based on velocity
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (landing pad)
        // Return Value: None
        sc4.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, (sp1, sp2) -> {
            if (rocketImg4.getVelY() > 0.8) {
                explode.playOverlapping();
                JOptionPane.showMessageDialog(sc4, "Landing failed! Velocity: " + rocketImg4.getVelY());
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(sc4, "Perfect landing! You win!");
                System.exit(0);
            }
        });

        // Sprite Collision Listener: Rocket and Obstacles - Handles rocket crashing into obstacles
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (obstacle)
        // Return Value: None
        sc4.addSpriteSpriteCollisionListener(LanderRocket.class, LandingOb.class, (sp1, sp2) -> {
            explode.playOverlapping();
            rocketImg4.destroy();
            lp4.destroy();
            JOptionPane.showMessageDialog(sc4, "You crashed into an obstacle!");
            System.exit(0);
        });

        // Setup KeyListener and Layout for the JFrame for level 4
        LanderMain.lvl4.addKeyListener(ka);
        LanderMain.lvl4.setLayout(new BasicLayout());
        LanderMain.lvl4.add("x=0,y=0,w=1,h=1", sc4);
    }
}