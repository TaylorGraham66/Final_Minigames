package minigames;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LandingSun extends Sprite {
    private final double GRAVITATIONAL_FORCE = 0.05;
    private final int SUN_RADIUS;

    // Method Name: LandingSun (constructor)
    // What it does: Initializes the LandingSun object, sets its position, and creates an image for the sun.
    // Parameters: Scene sc (the scene the object belongs to), int x (initial x position), int y (initial y position), int radius (radius of the sun)
    // Return Value: None (constructor)
    public LandingSun(Scene sc, int x, int y, int radius) {
        super(sc);
        this.SUN_RADIUS = radius;
        setX(x);
        setY(y);
        setPicture(createSunImage(radius));
    }

    // Method Name: createSunImage
    // What it does: Creates and returns an image of the sun with a radial gradient and flares.
    // Parameters: int radius (radius of the sun)
    // Return Value: Picture (image of the sun with flares)
    private Picture createSunImage(int radius) {
        BufferedImage img = BasicFrame.createImage(radius * 2, radius * 2);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, radius * 2, radius * 2);

        RadialGradientPaint gradient = new RadialGradientPaint(
                radius, radius, radius,
                new float[]{0.1f, 0.9f},
                new Color[]{Color.YELLOW, Color.RED}
        );
        g.setPaint(gradient);
        g.fillOval(0, 0, radius * 2, radius * 2);

        g.setColor(new Color(255, 165, 0, 100));
        for (int i = 0; i < 12; i++) {
            int flareLength = radius / 2 + Home.rand.nextInt(radius);
            int flareWidth = 5 + Home.rand.nextInt(10);
            double angle = i * Math.PI / 6;
            int x2 = radius + (int) ((radius + flareLength) * Math.cos(angle));
            int y2 = radius + (int) ((radius + flareLength) * Math.sin(angle));
            g.fillOval(x2 - flareWidth / 2, y2 - flareWidth / 2, flareWidth, flareWidth);
        }

        return new Picture(img);
    }

    // Method Name: applyGravity
    // What it does: Applies gravitational force from the sun to the rocket, pulling it towards the sun. If the rocket gets too close, it is destroyed.
    // Parameters: LanderRocket rocket (the rocket affected by the gravity)
    // Return Value: None
    public void applyGravity(LanderRocket rocket) {
        double rocketCenterX = rocket.getX() + rocket.getWidth()/2;
        double rocketCenterY = rocket.getY() + rocket.getHeight()/2;
        double sunCenterX = getX() + SUN_RADIUS;
        double sunCenterY = getY() + SUN_RADIUS;

        double dx = sunCenterX - rocketCenterX;
        double dy = sunCenterY - rocketCenterY;
        double distance = Math.sqrt(dx*dx + dy*dy);

        double dirX = dx/distance;
        double dirY = dy/distance;

        rocket.applyGravityPull(dirX * GRAVITATIONAL_FORCE, dirY * GRAVITATIONAL_FORCE);

        if(distance < 25) {
            rocket.destroy();
        }
    }
}