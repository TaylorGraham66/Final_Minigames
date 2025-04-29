package minigames;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WaterRacerOb extends Sprite {

    // Method Name: WaterRacerOb (constructor)
    // What it does: Initializes the WaterRacerOb sprite, randomly setting its position and generating an image for the obstacle (either a shark, fish, or seaweed).
    // Parameters:
    //   Scene sc - The scene where the sprite is placed.
    //   int x_bound - The minimum x-coordinate boundary for the obstacle's position.
    //   int y_bound - The minimum y-coordinate boundary for the obstacle's position.
    //   int x2_bound - The maximum x-coordinate boundary for the obstacle's position.
    //   int y2_bound - The maximum y-coordinate boundary for the obstacle's position.
    // Return Value: None
    public WaterRacerOb(Scene sc, int x_bound, int y_bound, int x2_bound, int y2_bound) {
        super(sc);

        if (x2_bound <= x_bound) x2_bound = x_bound + 1;
        if (y2_bound <= y_bound) y2_bound = y_bound + 1;

        setX(Home.rand.nextInt(x_bound, x2_bound));
        setY(Home.rand.nextInt(y_bound, y2_bound));

        BufferedImage image;
        int randomChoice = Home.rand.nextInt(0, 3);
        if (randomChoice == 0) {
            image = createSharkImage(50, 30);
        } else if (randomChoice == 1) {
            image = createFishImage();
        } else {
            image = createSeaweedImage(30, 100);
        }

        setPicture(new Picture(image));
    }

    // Method Name: createSharkImage
    // What it does: Creates a BufferedImage representing a shark with a specified width and height.
    // Parameters:
    //   int width - The width of the shark image.
    //   int height - The height of the shark image.
    // Return Value: BufferedImage - The generated image of a shark.
    private BufferedImage createSharkImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.GRAY);
        g.fillOval(10, 10, width - 20, height - 20);

        g.setColor(Color.DARK_GRAY);
        Polygon topFin = new Polygon();
        topFin.addPoint(width / 2 - 10, 10);
        topFin.addPoint(width / 2, -10);
        topFin.addPoint(width / 2 + 10, 10);
        g.fillPolygon(topFin);

        Polygon tailFin = new Polygon();
        tailFin.addPoint(width - 10, height / 2);
        tailFin.addPoint(width, height / 4);
        tailFin.addPoint(width, 3 * height / 4);
        g.fillPolygon(tailFin);

        g.setColor(Color.WHITE);
        g.fillRect(18, height / 2 - 5, 10, 3);
        g.fillRect(18, height / 2 + 2, 10, 3);

        g.setColor(Color.RED);
        g.fillOval(25, 15, 6, 6);
        g.setColor(Color.WHITE);
        g.fillOval(27, 17, 2, 2);

        return image;
    }

    // Method Name: createFishImage
    // What it does: Creates a fish image of random size by calling specific fish image creation methods for small, medium, or large fish.
    // Parameters: None
    // Return Value: BufferedImage - The generated fish image.
    private BufferedImage createFishImage() {
        int size = Home.rand.nextInt(20, 60);
        if (size < 30) {
            return createSmallFishImage(size, size / 2);
        } else if (size < 50) {
            return createMediumFishImage(size, size / 2);
        } else {
            return createLargeFishImage(size, size / 2);
        }
    }

    // Method Name: createSmallFishImage
    // What it does: Creates a small fish image with specified width and height.
    // Parameters:
    //   int width - The width of the fish image.
    //   int height - The height of the fish image.
    // Return Value: BufferedImage - The generated small fish image.
    private BufferedImage createSmallFishImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.CYAN);
        g.fillOval(0, 0, width - 5, height - 5);

        g.setColor(Color.BLUE);
        Polygon tail = new Polygon();
        tail.addPoint(0, height / 2);
        tail.addPoint(-10, height / 4);
        tail.addPoint(-10, 3 * height / 4);
        g.fillPolygon(tail);

        g.setColor(Color.BLACK);
        g.fillOval(width - 10, 5, 5, 5);

        return image;
    }

    // Method Name: createMediumFishImage
    // What it does: Creates a medium fish image with specified width and height.
    // Parameters:
    //   int width - The width of the fish image.
    //   int height - The height of the fish image.
    // Return Value: BufferedImage - The generated medium fish image.
    private BufferedImage createMediumFishImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.ORANGE);
        g.fillOval(0, 0, width - 5, height - 5);

        g.setColor(Color.RED);
        Polygon tail = new Polygon();
        tail.addPoint(0, height / 2);
        tail.addPoint(-15, height / 4);
        tail.addPoint(-15, 3 * height / 4);
        g.fillPolygon(tail);

        g.setColor(Color.BLACK);
        g.fillOval(width - 12, 5, 6, 6);

        return image;
    }

    // Method Name: createLargeFishImage
    // What it does: Creates a large fish image with specified width and height.
    // Parameters:
    //   int width - The width of the fish image.
    //   int height - The height of the fish image.
    // Return Value: BufferedImage - The generated large fish image.
    private BufferedImage createLargeFishImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.PINK);
        g.fillOval(0, 0, width - 10, height - 10);

        g.setColor(Color.MAGENTA);
        Polygon tail = new Polygon();
        tail.addPoint(0, height / 2);
        tail.addPoint(-20, height / 4);
        tail.addPoint(-20, 3 * height / 4);
        g.fillPolygon(tail);

        g.setColor(Color.BLACK);
        g.fillOval(width - 15, 5, 8, 8);

        return image;
    }

    // Method Name: createSeaweedImage
    // What it does: Creates a seaweed image with specified width and height, drawing the lines in a wavy pattern to simulate seaweed.
    // Parameters:
    //   int width - The width of the seaweed image.
    //   int height - The height of the seaweed image.
    // Return Value: BufferedImage - The generated seaweed image.
    private BufferedImage createSeaweedImage(int width, int height) {
        BufferedImage image = BasicFrame.createImage(width, height);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(0, 255, 0));

        int x = width / 2;
        int y = height;
        int segments = 12;
        int waveSize = 6;

        for (int i = 0; i < segments; i++) {
            int offset = (i % 2 == 0) ? waveSize : -waveSize;
            g.drawLine(x, y - i * (height / segments), x + offset, y - (i + 1) * (height / segments));
            x += offset;
        }

        g.setColor(new Color(0, 128, 0));
        g.drawLine(x + 2, y - 1, x + 2, y - height / 2);

        return image;
    }
}