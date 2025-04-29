package minigames;

import basicgraphics.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import basicgraphics.images.Picture;
import javax.swing.*;

public class LanderRocket extends Sprite {
    private double velX;
    private double velY;
    private final double MAX_SPEED = 0.5;
    private final double THRUST_POWER = 0.1;
    private final double BASE_GRAVITY = 0.01;

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
        velX = 0;
        velY = 0;

        Task movement = new Task() {
            @Override
            public void run() {
                velY += BASE_GRAVITY;

                velX = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, velX));
                velY = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, velY));

                setX(getX() + velX);
                setY(getY() + velY);
            }
        };
        ClockWorker.addTask(movement);
    }

    public void applyThrust(double xAccel, double yAccel) {
        velX += xAccel * THRUST_POWER;
        velY += yAccel * THRUST_POWER;
    }

    public void applyGravityPull(double forceX, double forceY) {
        velX += forceX;
        velY += forceY;
    }

    public void setVel(double x, double y) {
        this.velX = x;
        this.velY = y;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    private BufferedImage drawRocket(int w, int h, boolean withFlame) {
        BufferedImage img = BasicFrame.createImage(w, h);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.GRAY);
        g.fillRoundRect(w / 4, h / 5, w / 2, 3 * h / 5, 6, 6);

        Polygon nose = new Polygon();
        nose.addPoint(w / 2, 0);
        nose.addPoint(3 * w / 4, h / 5);
        nose.addPoint(w / 4, h / 5);
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(nose);

        g.setColor(Color.RED);
        g.fillRect(0, 3 * h / 5, w / 5, h / 5);
        g.fillRect(4 * w / 5, 3 * h / 5, w / 5, h / 5);

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

    public void update() {
        moveObject();
    }

    public void moveObject() {
        setX(getX() + velX);
        setY(getY() + velY);
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.xlo) {
            setX(sc.getFullSize().width - getWidth());
            velX *= -0.5;
        }
        if (se.xhi) {
            setX(0);
            velX *= -0.5;
        }
        if (se.ylo) {
            JOptionPane.showMessageDialog(sc, "You flew off into space");
            System.exit(0);
        }
        if (se.yhi) {
            JOptionPane.showMessageDialog(sc, "You missed the landing pad");
            System.exit(0);
        }
    }
}