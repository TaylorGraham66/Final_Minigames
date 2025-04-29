package minigames;

import basicgraphics.BasicLayout;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.sounds.ReusableClip;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LanderLvl5 {
    static SpriteComponent sc5;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    // Method Name: LanderLvl5 constructor - Sets up the fifth level of the lander game with gravity from the sun, rocket controls, collision detection, and scene setup
    // Parameters: None
    // Return Value: None (constructor)
    public LanderLvl5() {
        sc5 = new SpriteComponent();
        sc5.setPreferredSize(Home.SCREEN_SIZE);
        sc5.getScene().setPainter(LanderMain.painter);

        // Initialize landing pad, rocket, and sun object
        LandingPad lp5 = new LandingPad(sc5.getScene(), 500, 550);
        LanderRocket rocketImg5 = new LanderRocket(sc5.getScene(), 100, 50);
        LandingSun sun = new LandingSun(sc5.getScene(), Home.SCREEN_SIZE.width - 50, 10, 100);

        // Timer: gameTimer - Applies gravity from the sun and updates rocket's position periodically
        // Parameters: ActionEvent e (triggered automatically by the timer)
        // Return Value: None
        Timer gameTimer = new Timer(20, e -> {
            sun.applyGravity(rocketImg5);
            rocketImg5.update();
        });
        gameTimer.start();

        // Method Name: keyPressed - Handles user input for controlling the rocket (move left/right/up or quit)
        // Parameters: KeyEvent ke - the event triggered by a key press
        // Return Value: None
        KeyAdapter ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    rocketImg5.applyThrust(1, 0);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    rocketImg5.applyThrust(-2, 0);
                } else if (keyCode == KeyEvent.VK_UP) {
                    flame.playOverlapping();
                    rocketImg5.applyThrust(0, -3);
                    rocketImg5.setPicture(rocketImg5.rocketWithFlame);
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg5.setPicture(rocketImg5.rocket);
            }
        };

        // Sprite Collision Listener: Rocket and Landing Pad - Checks landing success or failure based on rocket's vertical velocity
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (landing pad)
        // Return Value: None
        sc5.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, (sp1, sp2) -> {
            if (rocketImg5.getVelY() > 0.8) {
                explode.playOverlapping();
                JOptionPane.showMessageDialog(sc5, "Landing failed! Velocity: " + rocketImg5.getVelY());
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(sc5, "Perfect landing! You win!");
                System.exit(0);
            }
        });

        // Sprite Collision Listener: Rocket and Sun - Handles crashing into the sun
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (sun)
        // Return Value: None
        sc5.addSpriteSpriteCollisionListener(LanderRocket.class, LandingSun.class, (sp1, sp2) -> {
            explode.playOverlapping();
            rocketImg5.destroy();
            JOptionPane.showMessageDialog(sc5, "You crashed into the sun!");
            System.exit(0);
        });

        // Setup KeyListener and Layout for the JFrame for level 5
        LanderMain.lvl5.addKeyListener(ka);
        LanderMain.lvl5.setLayout(new BasicLayout());
        LanderMain.lvl5.add("x=0,y=0,w=1,h=1", sc5);
    }
}
