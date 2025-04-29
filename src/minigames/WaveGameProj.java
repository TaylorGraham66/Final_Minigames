package minigames;

import basicgraphics.Scene;
import basicgraphics.Sprite;
import java.awt.Color;
import basicgraphics.ClockWorker;
import basicgraphics.images.Picture;

public class WaveGameProj extends Sprite{

    public Picture harpoon;

    // Method Name: WaveGameProj (constructor)
    // What it does: Initializes the projectile (harpoon) and sets its initial position to the center of the given sprite.
    // Parameters:
    //   Scene sc - The scene where the projectile is added.
    //   Sprite sp - The sprite (usually the player) that the projectile is centered on.
    // Return Value: None
    public WaveGameProj(Scene sc, Sprite sp) {
        super(sc);
        harpoon = new Picture("harpoon.jpg");
        setPicture(harpoon);
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());
    }
}