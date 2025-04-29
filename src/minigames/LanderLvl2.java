package minigames;

import basicgraphics.BasicLayout;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.sounds.ReusableClip;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LanderLvl2 {
    static SpriteComponent sc2;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    // Constructor: Sets up Level 2 of the Space Lander game with obstacles, the rocket, landing pad, key controls, and collision behavior.
    // Parameters: None
    // Return Value: None
    public LanderLvl2() {

        sc2 = new SpriteComponent();
        sc2.setPreferredSize(Home.SCREEN_SIZE);

        LandingPad lp2 = new LandingPad(sc2.getScene(), 400, 500);
        LanderRocket rocketImg2 = new LanderRocket(sc2.getScene(), 100, 50);
        sc2.getScene().setPainter(LanderMain.painter);

        // Generate 5 random obstacles (LandingOb objects) for the level
        for (int i = 0; i < 5; i++) {
            new LandingOb(sc2.getScene(), Home.rand.nextInt(150, 900), Home.rand.nextInt(120, 450), Home.rand.nextInt(25, 150), Home.rand.nextInt(25, 150));
        }

        // KeyAdapter: Handles keyboard input for moving the rocket and updating its appearance.
        // Parameters: KeyEvent ke (contains the key that was pressed or released)
        // Return Value: None
        KeyAdapter ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rocketImg2.setVel(.2, rocketImg2.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    rocketImg2.setVel(-.2, rocketImg2.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    flame.playOverlapping();
                    rocketImg2.setVel(0, -.4);
                    rocketImg2.setPicture(rocketImg2.rocketWithFlame);
                } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg2.setPicture(rocketImg2.rocket);
            }
        };

        // SpriteSpriteCollisionListener: Handles collision between the rocket and the landing pad (safe landing check).
        // Parameters: LanderRocket sp1 (rocket sprite), LandingPad sp2 (landing pad sprite)
        // Return Value: None
        sc2.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, new SpriteSpriteCollisionListener<LanderRocket, LandingPad>() {
            public void collision(LanderRocket sp1, LandingPad sp2) {
                if (rocketImg2.getVelY() > 1) {
                    explode.playOverlapping();
                    JOptionPane.showMessageDialog(sc2, "Y-Velocity too large! " + rocketImg2.getVelY());
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(sc2, "You landed safely! You win!");
                    System.exit(0);
                }
            }
        });

        // Method Name: keyPressed - Handles user input for controlling the rocket (move left/right/up or quit)
        // Parameters: KeyEvent ke - the event triggered by a key press
        // Return Value: None
        sc2.addSpriteSpriteCollisionListener(LanderRocket.class, LandingOb.class, new SpriteSpriteCollisionListener<LanderRocket, LandingOb>() {
            public void collision(LanderRocket sp1, LandingOb sp2) {
                explode.playOverlapping();
                if (rocketImg2.getVelY() > 1) {
                    JOptionPane.showMessageDialog(sc2, "You crashed into an asteroid! You died!");
                } else if (rocketImg2.getVelY() < 0) {
                    JOptionPane.showMessageDialog(sc2, "You smashed into an asteroid! You died!");
                } else if (rocketImg2.getVelY() == 0) {
                    JOptionPane.showMessageDialog(sc2, "You landed on the asteroid! Somehow.. but now you don't have enough fuel to leave!");
                } else if (rocketImg2.getVelX() > .5) {
                    JOptionPane.showMessageDialog(sc2, "You crashed into an asteroid! You died!");
                } else if (rocketImg2.getVelX() < 0) {
                    JOptionPane.showMessageDialog(sc2, "You smashed into an asteroid! You died!");
                } else {
                    JOptionPane.showMessageDialog(sc2, "You crashed AND smashed into an asteroid! You died!");
                }
                rocketImg2.destroy();
                lp2.destroy();
                System.exit(0);
            }
        });

        LanderMain.lvl2.addKeyListener(ka);
        LanderMain.lvl2.setLayout(new BasicLayout());
        LanderMain.lvl2.add("x=0,y=0,w=1,h=1", sc2);
    }
}