package minigames;

import basicgraphics.BasicLayout;
import basicgraphics.ClockWorker;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.images.Painter;
import basicgraphics.sounds.ReusableClip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class LanderLvl1 {


    static SpriteComponent sc1;

    public LanderLvl1() {
        final ReusableClip flame = new ReusableClip("fireburner.wav");
        final ReusableClip explode = new ReusableClip("explosion.wav");

        sc1 = new SpriteComponent();
        sc1.setPreferredSize(Home.SCREEN_SIZE);
        sc1.getScene().setPainter(LanderMain.painter);

        LandingPad lp = new LandingPad(sc1.getScene(), 500, 350);
        LanderRocket rocketImg = new LanderRocket(sc1.getScene(), 550, 50);

        KeyAdapter ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rocketImg.setVel(.2, rocketImg.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    rocketImg.setVel(-.2, rocketImg.getVelY());
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    flame.playOverlapping();
                    rocketImg.setVel(0, -.4);
                    rocketImg.setPicture(rocketImg.rocketWithFlame);
                } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg.setPicture(rocketImg.rocket);
            }
        };

        sc1.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, new SpriteSpriteCollisionListener<LanderRocket, LandingPad>() {
            public void collision(LanderRocket sp1, LandingPad sp2) {
                if (rocketImg.getVelY() > 1) {
                    explode.playOverlapping();
                    JOptionPane.showMessageDialog(sc1, "Y-Velocity too large! " + rocketImg.getVelY());
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(sc1, "You landed safely! You win!");
                    System.exit(0);
                }
            }
        });

        LanderMain.lvl1.addKeyListener(ka);
        LanderMain.lvl1.setLayout(new BasicLayout());
        LanderMain.lvl1.add("x=0,y=0,w=1,h=1", sc1);
    }
}