package minigames;

import basicgraphics.*;
import basicgraphics.images.Picture;
import java.awt.*;

public class WaveGamePlayer extends Sprite {

    public Picture player_pic;
    Scene scene;

    // Method Name: WaveGamePlayer (constructor)
    // What it does: Initializes the player by setting the sprite image and placing it at a starting position on the screen.
    // Parameters:
    //   Scene sc - The scene where the player is added.
    // Return Value: None
    public WaveGamePlayer(Scene sc) {
        super(sc);
        scene = sc;
        player_pic = new Picture("diver.jpg");
        setPicture(player_pic);
        setX(Home.SCREEN_SIZE.width / 1.5);
        setY(Home.SCREEN_SIZE.height / 1.5);
    }

    // Method Name: processEvent
    // What it does: Handles collision events and ensures the player sprite wraps around the screen when colliding with boundaries.
    // Parameters:
    //   SpriteCollisionEvent se - The event containing collision information.
    // Return Value: None
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.xlo) {
            setX(sc.getFullSize().width - getWidth());
        }
        if (se.xhi) {
            setX(0);
        }
        if (se.ylo) {
            setY(sc.getFullSize().height - getHeight());
        }
        if (se.yhi) {
            setY(0);
        }
    }
}
