package minigames;

import basicgraphics.SpriteComponent;
import basicgraphics.sounds.ReusableClip;

public class LanderLvl6 {

    static SpriteComponent sc6;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    public LanderLvl6() {
        sc6 = new SpriteComponent();
        sc6.setPreferredSize(Home.SCREEN_SIZE);
        sc6.getScene().setPainter(LanderMain.painter);
    }
}
