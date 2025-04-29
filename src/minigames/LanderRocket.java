package minigames;

import basicgraphics.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import basicgraphics.images.Picture;
import javax.swing.*;

public class LanderRocket extends Sprite {

    Picture rocket;
    Picture rocketWithFlame;

    public LanderRocket(Scene sc, int x, int y) {
        super(sc);

        int w = 20;
        int h = 40;
        rocket = new Picture(drawRocket(w, h, false));
        rocketWithFlame = new Picture(drawRocket(w, h, true));

        freezeOrientation = true;
        setX(x);
        setY(y);
        setPicture(rocket);

        Task grav = new Task() {
            @Override
            public void run() {
                double x = getVelX();
                double y = getVelY() + .002;
                setVel(x, y);
            }
        };

        ClockWorker.addTask(grav);
    }

    private BufferedImage drawRocket(int w, int h, boolean withFlame) {
        BufferedImage img = BasicFrame.createImage(w, h);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rocket Body
        g.setColor(Color.GRAY);
        g.fillRoundRect(w / 4, h / 5, w / 2, 3 * h / 5, 6, 6);

        // Nose cone
        Polygon nose = new Polygon();
        nose.addPoint(w / 2, 0);
        nose.addPoint(3 * w / 4, h / 5);
        nose.addPoint(w / 4, h / 5);
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(nose);

        // Side fins
        g.setColor(Color.RED);
        g.fillRect(0, 3 * h / 5, w / 5, h / 5);
        g.fillRect(4 * w / 5, 3 * h / 5, w / 5, h / 5);

        // Flame if applicable
        if (withFlame) {
            Polygon flame = new Polygon();
            flame.addPoint(w / 4, 4 * h / 5);
            flame.addPoint(w / 2, h);
            flame.addPoint(3 * w / 4, 4 * h / 5);
            g.setColor(Color.ORANGE);
            g.fillPolygon(flame);
        }

        return img;
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.xlo) {
            setX(sc.getFullSize().width - getWidth());
        }
        if (se.xhi) {
            setX(0);
        }
        if (se.ylo) {
            JOptionPane.showMessageDialog(sc, "You flew off into space");
            LanderMain.sl1.showCard();
            LanderMain.sl1.requestFocus();
        }
        if (se.yhi) {
            JOptionPane.showMessageDialog(sc, "You missed the landing pad");
            LanderMain.sl1.showCard();
            LanderMain.sl1.requestFocus();
        }
    }
}