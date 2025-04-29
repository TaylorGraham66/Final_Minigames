package minigames;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.CollisionEventType;
import basicgraphics.SpriteComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LandingOb extends Sprite {

    private double velocityX;
    private double velocityY;

    public LandingOb(Scene sc, int x, int y, int width, int height) {
        this(sc, x, y, width, height, 0, 0);
    }

    public LandingOb(Scene sc, int x, int y, int width, int height, double velocityX, double velocityY) {
        super(sc);
        BufferedImage asteroidImage = createAsteroidImage(width, height);
        setX(x);
        setY(y);
        Picture pic = new Picture(asteroidImage);
        setPicture(pic);
        this.velocityX = velocityX;
        this.velocityY = velocityY;

    }

    private Polygon createCollisionShape(int width, int height) {
        Polygon collisionShape = new Polygon();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 2;
        int points = 8 + Home.rand.nextInt(5);

        for (int i = 0; i < points; i++) {
            double angle = i * 2 * Math.PI / points;
            int r = radius - 5 + Home.rand.nextInt(10);
            int x = centerX + (int) (r * Math.cos(angle));
            int y = centerY + (int) (r * Math.sin(angle));
            collisionShape.addPoint(x, y);
        }
        return collisionShape;
    }

    public void moveObject() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            // Bounce off screen edges
            if (se.xlo || se.xhi) {
                velocityX *= -1; // Reverse horizontal direction
            }
            if (se.ylo || se.yhi) {
                velocityY *= -1; // Reverse vertical direction
            }

            // Adjust position to stay within bounds
            SpriteComponent sc = getSpriteComponent();
            if (se.xlo) {
                setX(0);
            }
            if (se.xhi) {
                setX(sc.getSize().width - getWidth());
            }
            if (se.ylo) {
                setY(0);
            }
            if (se.yhi) {
                setY(sc.getSize().height - getHeight());
            }
        }
    }

    private BufferedImage createAsteroidImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics g = image.getGraphics();

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        Polygon asteroidShape = createCollisionShape(width, height);

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