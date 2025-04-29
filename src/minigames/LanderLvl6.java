package minigames;

import basicgraphics.BasicLayout;
import basicgraphics.SpriteComponent;
import basicgraphics.sounds.ReusableClip;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LanderLvl6 {
    static SpriteComponent sc6;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    // Method Name: LanderLvl6 constructor - Sets up the sixth level of the lander game with obstacles, gravity from the sun, rocket controls, collision detection, and scene setup
    // Parameters: None
    // Return Value: None (constructor)
    public LanderLvl6() {
        sc6 = new SpriteComponent();
        sc6.setPreferredSize(Home.SCREEN_SIZE);
        sc6.getScene().setPainter(LanderMain.painter);

        // Initialize landing pad, rocket, and sun object
        LandingPad lp6 = new LandingPad(sc6.getScene(), 500, 550);
        LanderRocket rocketImg6 = new LanderRocket(sc6.getScene(), 100, 50);
        LandingSun sun = new LandingSun(sc6.getScene(), Home.SCREEN_SIZE.width - 50, 10, 100);

        // Add stationary obstacles (asteroids)
        new LandingOb(sc6.getScene(), 300, 200, 60, 60);
        new LandingOb(sc6.getScene(), 150, 350, 80, 80);
        new LandingOb(sc6.getScene(), 600, 400, 100, 50);

        // Add moving obstacles (asteroids with different movement patterns)
        LandingOb movingHorz = new LandingOb(sc6.getScene(), 200, 100, 50, 50, 1.5, 0);
        LandingOb movingVert = new LandingOb(sc6.getScene(), 400, 150, 70, 70, 0, 1.2);
        LandingOb movingDiag = new LandingOb(sc6.getScene(), 650, 300, 60, 60, -1, 0.8);

        // Create array of moving obstacles for easier updating
        LandingOb[] movingAsteroids = {movingHorz, movingVert, movingDiag};

        // Timer: gameTimer - Applies gravity from the sun, updates the rocket's position, and moves the asteroids periodically
        // Parameters: ActionEvent e (triggered automatically by the timer)
        // Return Value: None
        Timer gameTimer = new Timer(20, e -> {
            sun.applyGravity(rocketImg6);
            rocketImg6.update();
            for(LandingOb asteroid : movingAsteroids) {
                asteroid.update();
            }
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
                    rocketImg6.applyThrust(1, 0);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    rocketImg6.applyThrust(-2, 0);
                } else if (keyCode == KeyEvent.VK_UP) {
                    flame.playOverlapping();
                    rocketImg6.applyThrust(0, -3);
                    rocketImg6.setPicture(rocketImg6.rocketWithFlame);
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg6.setPicture(rocketImg6.rocket);
            }
        };

        // Sprite Collision Listener: Rocket and Landing Pad - Checks landing success or failure based on rocket's vertical and horizontal velocity
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (landing pad)
        // Return Value: None
        sc6.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, (sp1, sp2) -> {
            if (rocketImg6.getVelY() > 0.8 || Math.abs(rocketImg6.getVelX()) > 0.5) {
                explode.playOverlapping();
                JOptionPane.showMessageDialog(sc6, "Landing Failed!, Your landing was too hard! Velocity: " +
                        String.format("(%.2f,%.2f)", rocketImg6.getVelX(), rocketImg6.getVelY()));
            }
            System.exit(0);
        });

        // Sprite Collision Listener: Rocket and Sun - Handles crashing into the sun
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (sun)
        // Return Value: None
        sc6.addSpriteSpriteCollisionListener(LanderRocket.class, LandingSun.class, (sp1, sp2) -> {
            explode.playOverlapping();
            JOptionPane.showMessageDialog(sc6, "Solar Disaster!, You got too close to the sun!");
            System.exit(0);
        });

        // Sprite Collision Listener: Rocket and Asteroid - Handles crashing into an asteroid
        // Parameters: Sprite sp1 (rocket), Sprite sp2 (asteroid)
        // Return Value: None
        sc6.addSpriteSpriteCollisionListener(LanderRocket.class, LandingOb.class, (sp1, sp2) -> {
            explode.playOverlapping();
            JOptionPane.showMessageDialog(sc6, "Asteroid Collision! You crashed into an asteroid!");
            System.exit(0);
        });

        // Setup KeyListener and Layout for the JFrame for level 6
        LanderMain.lvl6.addKeyListener(ka);
        LanderMain.lvl6.setLayout(new BasicLayout());
        LanderMain.lvl6.add("x=0,y=0,w=1,h=1", sc6);
    }
}