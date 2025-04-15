package minigames;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LandingOb extends Sprite {

    private int velocityX;
    private int velocityY;

    public LandingOb(Scene sc, int x, int y, int width, int height) {
        super(sc);
        BufferedImage asteroidImage = createAsteroidImage(width, height);
        setX(x);
        setY(y);
        Picture pic = new Picture(asteroidImage);
        setPicture(pic);
        velocityX = 0;
        velocityY = 0;
    }

    public LandingOb(Scene sc, int x, int y, int width, int height, int velocityX, int velocityY) {
        super(sc);
        BufferedImage asteroidImage = createAsteroidImage(width, height);
        setX(x);
        setY(y);
        Picture pic = new Picture(asteroidImage);
        setPicture(pic);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void moveObject() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
    }

    private BufferedImage createAsteroidImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics g = image.getGraphics();

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        Polygon asteroidShape = new Polygon();

        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 2;
        int points = 8 + Home.rand.nextInt(5);

        for (int i = 0; i < points; i++) {
            double angle = i * 2 * Math.PI / points;
            int r = radius - 5 + Home.rand.nextInt(10);
            int x = centerX + (int) (r * Math.cos(angle));
            int y = centerY + (int) (r * Math.sin(angle));
            asteroidShape.addPoint(x, y);
        }

        int gray = 100 + Home.rand.nextInt(100);
        g.setColor(new Color(gray, gray, gray));
        g.fillPolygon(asteroidShape);

        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(asteroidShape);

        return image;
    }

    public void update() {
        moveObject();
    }
}
