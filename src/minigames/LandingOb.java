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

    // Method Name: LandingOb (constructor)
    // What it does: Initializes the LandingOb object, setting up its position, image, and velocities. It creates an asteroid image based on the specified width and height.
    // Parameters: Scene sc (the scene the object belongs to), int x (initial x position), int y (initial y position), int width (width of the object), int height (height of the object)
    // Return Value: None (constructor)
    public LandingOb(Scene sc, int x, int y, int width, int height) {
        this(sc, x, y, width, height, 0, 0);
    }

    // Method Name: LandingOb (constructor)
    // What it does: Initializes the LandingOb object with position, size, velocities, and creates its image.
    // Parameters: Scene sc (the scene the object belongs to), int x (initial x position), int y (initial y position), int width (width of the object), int height (height of the object), double velocityX (velocity in the x direction), double velocityY (velocity in the y direction)
    // Return Value: None (constructor)
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

    // Method Name: createCollisionShape
    // What it does: Creates a random polygon shape to represent the object's collision area based on the provided width and height.
    // Parameters: int width (width of the object), int height (height of the object)
    // Return Value: Polygon (the shape used for collision detection)
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

    // Method Name: moveObject
    // What it does: Moves the object by updating its position based on its velocity in both the x and y directions.
    // Parameters: None
    // Return Value: None (void method)
    public void moveObject() {
        setX(getX() + velocityX);
        setY(getY() + velocityY);
    }

    // Method Name: processEvent
    // What it does: Handles collision events, particularly bouncing off screen edges and adjusting position to keep the object within bounds.
    // Parameters: SpriteCollisionEvent se (the collision event that occurred)
    // Return Value: None (void method)
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

    // Method Name: createAsteroidImage
    // What it does: Creates an image of the asteroid using the provided width and height. The asteroid is drawn with a random shape and color.
    // Parameters: int width (width of the image), int height (height of the image)
    // Return Value: BufferedImage (the generated image of the asteroid)
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

    // Method Name: update
    // What it does: Updates the object's position by moving it according to its velocity.
    // Parameters: None
    // Return Value: None (void method)
    public void update() {
        moveObject();
    }
}
