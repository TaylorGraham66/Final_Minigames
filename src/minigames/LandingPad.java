package minigames;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.images.Picture;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LandingPad extends Sprite {

    public LandingPad(Scene sc, int x, int y) {
        super(sc);

        int padWidth = 100;
        int padHeight = 20;

        BufferedImage platform = BasicFrame.createImage(padWidth, padHeight);
        Graphics g = platform.getGraphics();

        g.setColor(new Color(80, 80, 80));
        g.fillRect(0, 0, padWidth, padHeight);

        g.setColor(new Color(0, 255, 255));
        g.fillRect(0, 0, 8, padHeight);
        g.fillRect(padWidth - 8, 0, 8, padHeight);
        g.fillRect(0, 0, padWidth, 4);
        g.fillRect(0, padHeight - 4, padWidth, 4);

        g.fillOval(padWidth / 2 - 15, 3, 30, padHeight - 6);

        setX(x);
        setY(y);
        setPicture(new Picture(platform));
    }
}