package minigames;

import basicgraphics.Scene;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

import basicgraphics.Sprite;

import javax.swing.*;

public class WaterRacerPlayer extends Sprite{

    public Picture initialPic;
    private double baseSpeed = 1.0;

    public WaterRacerPlayer(Scene sc) {
        super(sc);
        initialPic = new Picture("sub.jpg");
        setPicture(initialPic);
        setX(550);
        setY(Home.SCREEN_SIZE.height / .8);
        setVel(baseSpeed, 0);
    }

    public void setBaseSpeed(double speed) {
        this.baseSpeed = speed;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc2 = getSpriteComponent();
        if (se.ylo) {
            JOptionPane.showMessageDialog(sc2, "You died!");
            System.exit(0);
        }if (se.yhi) {
            JOptionPane.showMessageDialog(sc2, "You died!");
            System.exit(0);
        }
    }
}