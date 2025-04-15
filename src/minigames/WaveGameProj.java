package minigames;

import basicgraphics.Scene;
import basicgraphics.Sprite;
import java.awt.Color;
import basicgraphics.ClockWorker;
import basicgraphics.images.Picture;

public class WaveGameProj extends Sprite{

    public Picture harpoon;

    public WaveGameProj(Scene sc, Sprite sp) {
        super(sc);
        harpoon = new Picture("harpoon.jpg");
        setPicture(harpoon);
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());
    }
}