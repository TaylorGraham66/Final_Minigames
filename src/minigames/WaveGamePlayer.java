package minigames;

import basicgraphics.*;
import basicgraphics.images.Picture;
import java.awt.*;

public class WaveGamePlayer extends Sprite {

    public Picture player_pic;
    Scene scene;

    public WaveGamePlayer(Scene sc) {
        super(sc);
        scene = sc;
        player_pic = new Picture("diver.jpg");
        setPicture(player_pic);
        setX(Home.SCREEN_SIZE.width / 1.5);
        setY(Home.SCREEN_SIZE.height / 1.5);
    }

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