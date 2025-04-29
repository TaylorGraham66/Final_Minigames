package minigames;

import basicgraphics.SpriteComponent;
import basicgraphics.sounds.ReusableClip;

public class LanderLvl5 {

    static SpriteComponent sc5;
    final ReusableClip flame = new ReusableClip("fireburner.wav");
    final ReusableClip explode = new ReusableClip("explosion.wav");

    public LanderLvl5() {
        sc5 = new SpriteComponent();
        sc5.setPreferredSize(Home.SCREEN_SIZE);
        sc5.getScene().setPainter(LanderMain.painter);
    }
}
