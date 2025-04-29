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

    // Method Name: LanderRocket (constructor)
    // What it does: Initializes the LanderRocket object, setting up the initial properties such as position, velocity, and rocket pictures. It also adds a movement task to the clock worker to update the rocket's position based on gravity.
    // Parameters: Scene sc (the scene the rocket belongs to), int x (initial x position), int y (initial y position)
    // Return Value: None (constructor)
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

    // Method Name: applyThrust
    // What it does: Applies thrust to the rocket, modifying its velocity based on the given acceleration values.
    // Parameters: double xAccel (acceleration in the x direction), double yAccel (acceleration in the y direction)
    // Return Value: None (void method)
    public void applyThrust(double xAccel, double yAccel) {
        velX += xAccel * THRUST_POWER;
        velY += yAccel * THRUST_POWER;
    }

    // Method Name: applyGravityPull
    // What it does: Applies gravity pull to the rocket's velocity based on external forces.
    // Parameters: double forceX (force in the x direction), double forceY (force in the y direction)
    // Return Value: None (void method)
    public void applyGravityPull(double forceX, double forceY) {
        velX += forceX;
        velY += forceY;
    }

    // Method Name: setVel
    // What it does: Sets the velocity of the rocket directly.
    // Parameters: double x (velocity in the x direction), double y (velocity in the y direction)
    // Return Value: None (void method)
    public void setVel(double x, double y) {
        this.velX = x;
        this.velY = y;
    }

    // Method Name: getVelX
    // What it does: Retrieves the current velocity in the x direction.
    // Parameters: None
    // Return Value: double (the current velocity in the x direction)
    public double getVelX() {
        return velX;
    }

    // Method Name: getVelY
    // What it does: Retrieves the current velocity in the y direction.
    // Parameters: None
    // Return Value: double (the current velocity in the y direction)
    public double getVelY() {
        return velY;
    }

    // Method Name: drawRocket
    // What it does: Draws a rocket with optional flame based on the provided dimensions and flame status.
    // Parameters: int w (width of the rocket), int h (height of the rocket), boolean withFlame (whether the rocket should have a flame or not)
    // Return Value: BufferedImage (the image of the rocket)
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

    // Method Name: update
    // What it does: Updates the rocket's position based on its velocity.
    // Parameters: None
    // Return Value: None (void method)
    public void update() {
        moveObject();
    }

    // Method Name: moveObject
    // What it does: Moves the rocket by updating its position based on its velocity.
    // Parameters: None
    // Return Value: None (void method)
    public void moveObject() {
        setX(getX() + velX);
        setY(getY() + velY);
    }

    // Method Name: processEvent
    // What it does: Handles the collision events for the rocket, such as when it hits the edges or misses the landing pad.
    // Parameters: SpriteCollisionEvent se (the collision event)
    // Return Value: None (void method)
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