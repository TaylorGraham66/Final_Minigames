package minigames;

import basicgraphics.BasicLayout;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.sounds.ReusableClip;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LanderLvl3 {
    static SpriteComponent sc3;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    // Constructor: Sets up level 3 of the lander game, including rocket, obstacles, landing pad, key controls, and collision detection.
    // Parameters: None
    // Return Value: None
    public LanderLvl3() {
        sc3 = new SpriteComponent();
        sc3.setPreferredSize(Home.SCREEN_SIZE);
        sc3.getScene().setPainter(LanderMain.painter);

        // Create landing pad, rocket, and static obstacles
        LandingPad lp3 = new LandingPad(sc3.getScene(), 800, 500);
        LanderRocket rocketImg3 = new LanderRocket(sc3.getScene(), 100, 50);
        LandingOb wall = new LandingOb(sc3.getScene(), 100, 200, 50, 100);

        new LandingOb(sc3.getScene(), 250, 300, 80, 80);
        new LandingOb(sc3.getScene(), 400, 350, 100, 50);
        new LandingOb(sc3.getScene(), 600, 200, 120, 60);
        new LandingOb(sc3.getScene(), 750, 100, 70, 70);

        // Create moving obstacles
        LandingOb moving1 = new LandingOb(sc3.getScene(), 200, 100, 60, 60, 1, 0);
        LandingOb moving2 = new LandingOb(sc3.getScene(), 500, 400, 50, 50, -1, 0);
        LandingOb moving3 = new LandingOb(sc3.getScene(), 300, 250, 70, 70, 0, 1);

        // Timer: Updates the position of moving obstacles periodically
        // Parameters: ActionListener triggered every 20 milliseconds
        // Return Value: None
        Timer moveTimer = new Timer(20, e -> {
            moving1.update();
            moving2.update();
            moving3.update();
        });
        moveTimer.start();

        // KeyAdapter: Handles key presses and releases for rocket movement
        // Methods inside:
        //   keyPressed(KeyEvent) - Move the rocket based on key inputs
        //   keyReleased(KeyEvent) - Resets the rocket's picture when key is released
        KeyAdapter ka = new KeyAdapter() {

            // Method Name: keyPressed - Handles user input for controlling the rocket (move left/right/up or quit)
            // Parameters: KeyEvent ke - the event triggered by a key press
            // Return Value: None
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rocketImg3.setVel(.5, rocketImg3.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    rocketImg3.setVel(-.5, rocketImg3.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    flame.playOverlapping();
                    rocketImg3.setVel(0, -.4);
                    rocketImg3.setPicture(rocketImg3.rocketWithFlame);
                } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            // Method Name: keyReleased - Resets rocket image when movement keys are released
            // Parameters: KeyEvent ke - the event triggered by a key release
            // Return Value: None
            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg3.setPicture(rocketImg3.rocket);
            }
        };

        // Collision Listener: Handles rocket landing on the landing pad
        // Parameters: sp1 (rocket sprite), sp2 (landing pad sprite)
        // Return Value: None
        sc3.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, (sp1, sp2) -> {
            if (rocketImg3.getVelY() > 1) {
                explode.playOverlapping();
                JOptionPane.showMessageDialog(sc3, "Y-Velocity too large! " + rocketImg3.getVelY());
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(sc3, "You landed safely! You win!");
                System.exit(0);
            }
        });

        // Collision Listener: Handles rocket crashing into an obstacle
        // Parameters: sp1 (rocket sprite), sp2 (obstacle sprite)
        // Return Value: None
        sc3.addSpriteSpriteCollisionListener(LanderRocket.class, LandingOb.class, (sp1, sp2) -> {
            explode.playOverlapping();
            if (rocketImg3.getVelY() > 1) {
                JOptionPane.showMessageDialog(sc3, "You crashed into an asteroid! You died!");
                rocketImg3.destroy();
                lp3.destroy();
                System.exit(0);
            } else if (rocketImg3.getVelY() < 0) {
                JOptionPane.showMessageDialog(sc3, "You smashed into an asteroid! You died!");
                rocketImg3.destroy();
                lp3.destroy();
                System.exit(0);
            } else if (rocketImg3.getVelY() == 0) {
                JOptionPane.showMessageDialog(sc3, "You landed on the asteroid! Somehow.. but now you don't have enough fuel to leave!");
                rocketImg3.destroy();
                lp3.destroy();
                System.exit(0);
            } else if (rocketImg3.getVelX() > .5) {
                JOptionPane.showMessageDialog(sc3, "You crashed into an asteroid! You died!");
                rocketImg3.destroy();
                lp3.destroy();
                System.exit(0);
            } else if (rocketImg3.getVelX() < 0) {
                JOptionPane.showMessageDialog(sc3, "You smashed into an asteroid! You died!");
                rocketImg3.destroy();
                lp3.destroy();
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(sc3, "You crashed AND smashed into an asteroid! You died!");
                rocketImg3.destroy();
                lp3.destroy();
                System.exit(0);
            }
        });

        // Set up key listener and layout
        LanderMain.lvl3.addKeyListener(ka);
        LanderMain.lvl3.setLayout(new BasicLayout());
        LanderMain.lvl3.add("x=0,y=0,w=1,h=1", sc3);
    }
}