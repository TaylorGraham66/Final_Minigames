package minigames;

import basicgraphics.CollisionEventType;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

public class WaveGameEnemy extends Sprite {

    static int enemyCount;
    public Picture enemy_pic;

    // Method Name: WaveGameEnemy (constructor)
    // What it does: Initializes a new enemy sprite, increments the enemy count, sets the enemy's image, randomly positions it on the screen (avoiding a specific area), and sets its velocity.
    // Parameters:
    //   Scene sc - The scene where the enemy sprite is placed.
    // Return Value: None
    public WaveGameEnemy(Scene sc) {
        super(sc);
        enemyCount++;
        enemy_pic = new Picture("squid_enemy.png");
        setPicture(enemy_pic);
        while (true) {
            setX(Home.rand.nextInt(Home.SCREEN_SIZE.width) - 5);
            setY(Home.rand.nextInt(Home.SCREEN_SIZE.height) - 5);
            if (Math.abs(getX() - Home.SCREEN_SIZE.width / 1.5) < 3 * 10
                    && Math.abs(getY() - Home.SCREEN_SIZE.height / 1.5) < 3 * 10) {
            } else {
                break;
            }
        }
        setVel(1.5 * Home.rand.nextDouble() - 1, 1.5 * Home.rand.nextDouble());
    }

    // Method Name: processEvent
    // What it does: Handles collision events for the enemy sprite, specifically if the enemy hits the screen boundaries, it wraps around to the opposite side.
    // Parameters:
    //   SpriteCollisionEvent se - The event that contains collision information (event type and specific side of collision).
    // Return Value: None
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            if (se.xlo) {
                setX(sc.getSize().width - getWidth());
            }
            if (se.xhi) {
                setX(0);
            }
            if (se.ylo) {
                setY(sc.getSize().height - getHeight());
            }
            if (se.yhi) {
                setY(0);
            }
        }
    }
}