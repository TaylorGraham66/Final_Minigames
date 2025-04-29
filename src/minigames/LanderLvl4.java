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

    public LanderLvl4() {
        sc4 = new SpriteComponent();
        sc4.setPreferredSize(Home.SCREEN_SIZE);
        sc4.getScene().setPainter(LanderMain.painter);

        // Smaller landing pad for increased difficulty
        LandingPad lp4 = new LandingPad(sc4.getScene(), 800, 500);
        LanderRocket rocketImg4 = new LanderRocket(sc4.getScene(), 100, 50);

        // Fewer stationary obstacles (reduced from 5 to 2)
        new LandingOb(sc4.getScene(), 300, 350, 60, 60);
        new LandingOb(sc4.getScene(), 600, 150, 100, 50);

        // Increased number of moving obstacles with different patterns
        LandingOb movingHorz1 = new LandingOb(sc4.getScene(), 200, 100, 50, 50, 2, 0);
        LandingOb movingHorz2 = new LandingOb(sc4.getScene(), 500, 400, 60, 60, -1.5, 0);
        LandingOb movingHorz3 = new LandingOb(sc4.getScene(), 100, 200, 40, 40, 1.8, 0);

        LandingOb movingVert1 = new LandingOb(sc4.getScene(), 300, 250, 40, 40, 0, 1.2);
        LandingOb movingVert2 = new LandingOb(sc4.getScene(), 400, 100, 70, 70, 0, -1.3);
        LandingOb movingVert3 = new LandingOb(sc4.getScene(), 700, 300, 50, 50, 0, 1.5);

        // More diagonal moving obstacles
        LandingOb movingDiag1 = new LandingOb(sc4.getScene(), 100, 300, 60, 60, 1.2, 1.2);
        LandingOb movingDiag2 = new LandingOb(sc4.getScene(), 650, 200, 50, 50, -1.2, 1.1);
        LandingOb movingDiag3 = new LandingOb(sc4.getScene(), 200, 400, 70, 70, -1.5, -1.3);
        LandingOb movingDiag4 = new LandingOb(sc4.getScene(), 500, 100, 60, 60, 1.3, -1.1);

        // Create array of all moving obstacles for easier updating
        LandingOb[] movingObstacles = {
                movingHorz1, movingHorz2, movingHorz3,
                movingVert1, movingVert2, movingVert3,
                movingDiag1, movingDiag2, movingDiag3, movingDiag4
        };

        Timer moveTimer = new Timer(20, e -> {
            for (LandingOb obstacle : movingObstacles) {
                obstacle.update();
            }
        });
        moveTimer.start();

        KeyAdapter ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rocketImg4.setVel(.2, rocketImg4.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    rocketImg4.setVel(-.2, rocketImg4.getVelY());
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

        sc4.addSpriteSpriteCollisionListener(LanderRocket.class, LandingOb.class, (sp1, sp2) -> {
            explode.playOverlapping();
            rocketImg4.destroy();
            lp4.destroy();
            JOptionPane.showMessageDialog(sc4, "You crashed into an obstacle!");
            System.exit(0);
        });

        LanderMain.lvl4.addKeyListener(ka);
        LanderMain.lvl4.setLayout(new BasicLayout());
        LanderMain.lvl4.add("x=0,y=0,w=1,h=1", sc4);
    }
}