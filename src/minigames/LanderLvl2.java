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

    public LanderLvl2() {

        sc2 = new SpriteComponent();
        sc2.setPreferredSize(Home.SCREEN_SIZE);

        LandingPad lp2 = new LandingPad(sc2.getScene(), 400, 500);
        LanderRocket rocketImg2 = new LanderRocket(sc2.getScene(), 100, 50);
        sc2.getScene().setPainter(LanderMain.painter);

        for(int i = 0; i<5; i++) {
            new LandingOb(sc2.getScene(), Home.rand.nextInt(150, 900), Home.rand.nextInt(120, 450), Home.rand.nextInt(25, 150), Home.rand.nextInt(25, 150));
        }

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
                    LanderMain.sl1.showCard();
                    LanderMain.sl1.requestFocus();
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
                rocketImg2.setPicture(rocketImg2.rocket);
            }
        };

        sc2.addSpriteSpriteCollisionListener(LanderRocket.class, LandingPad.class, new SpriteSpriteCollisionListener<LanderRocket, LandingPad>() {
            public void collision(LanderRocket sp1, LandingPad sp2) {
                if(rocketImg2.getVelY() > 1) {
                    explode.playOverlapping();
                    JOptionPane.showMessageDialog(sc2, "Y-Velocity too large! " + rocketImg2.getVelY());
                    System.exit(0);
                }else {
                    JOptionPane.showMessageDialog(sc2, "You landed safely! You win!");
                    System.exit(0);
                }
            }
        });

        sc2.addSpriteSpriteCollisionListener(LanderRocket.class, LandingOb.class, new SpriteSpriteCollisionListener<LanderRocket, LandingOb>() {
            public void collision(LanderRocket sp1, LandingOb sp2) {
                explode.playOverlapping();
                if(rocketImg2.getVelY() > 1) {
                    JOptionPane.showMessageDialog(sc2, "You crashed into an asteroid! You died!");
                    rocketImg2.destroy();
                    lp2.destroy();
                    System.exit(0);
                } else if(rocketImg2.getVelY() < 0) {
                    JOptionPane.showMessageDialog(sc2, "You smashed into an asteroid! You died!");
                    rocketImg2.destroy();
                    lp2.destroy();
                    System.exit(0);
                } else if(rocketImg2.getVelY() == 0) {
                    JOptionPane.showMessageDialog(sc2, "You landed on the asteroid! Somehow.. but now you don't have enough fuel to leave!");
                    rocketImg2.destroy();
                    lp2.destroy();
                    System.exit(0);
                } else if(rocketImg2.getVelX() > .5) {
                    JOptionPane.showMessageDialog(sc2, "You crashed into an asteroid! You died!");
                    rocketImg2.destroy();
                    lp2.destroy();
                    System.exit(0);
                } else if(rocketImg2.getVelX() < 0) {
                    JOptionPane.showMessageDialog(sc2, "You smashed into an asteroid! You died!");
                    rocketImg2.destroy();
                    lp2.destroy();
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(sc2, "You crashed AND smashed into an asteroid! You died!");
                    rocketImg2.destroy();
                    lp2.destroy();
                    System.exit(0);
                }
            }
        });

        LanderMain.lvl2.addKeyListener(ka);
        LanderMain.lvl2.setLayout(new BasicLayout());
        LanderMain.lvl2.add("x=0,y=0,w=1,h=1", sc2);
    }
}
