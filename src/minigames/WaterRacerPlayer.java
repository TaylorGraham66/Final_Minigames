package minigames;

import basicgraphics.Scene;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

import basicgraphics.Sprite;

import javax.swing.*;

public class WaterRacerPlayer extends Sprite{

    public Picture initialPic;
    private double baseSpeed = 1.0;

    // Method Name: WaterRacerPlayer (constructor)
    // What it does: Initializes the WaterRacerPlayer sprite, sets the initial image, positions the sprite on the screen, and sets its initial velocity.
    // Parameters:
    //   Scene sc - The scene where the sprite is placed.
    // Return Value: None
    public WaterRacerPlayer(Scene sc) {
        super(sc);
        initialPic = new Picture("sub.jpg");
        setPicture(initialPic);
        setX(550);
        setY(Home.SCREEN_SIZE.height / .8);
        setVel(baseSpeed, 0);
    }

    // Method Name: setBaseSpeed
    // What it does: Sets the base speed for the player.
    // Parameters:
    //   double speed - The speed to be set as the player's base speed.
    // Return Value: None
    public void setBaseSpeed(double speed) {
        this.baseSpeed = speed;
    }

    // Method Name: getBaseSpeed
    // What it does: Retrieves the current base speed of the player.
    // Parameters: None
    // Return Value: double - The player's base speed.
    public double getBaseSpeed() {
        return baseSpeed;
    }

    // Method Name: processEvent
    // What it does: Handles collision events for the player, displaying a "You died!" message and ending the game if the player collides with the top or bottom of the screen.
    // Parameters:
    //   SpriteCollisionEvent se - The event that contains collision information.
    // Return Value: None
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc2 = getSpriteComponent();
        if (se.ylo) {
            JOptionPane.showMessageDialog(sc2, "You died!");
            System.exit(0);
        }if (se.yhi) {
            JOptionPane.showMessageDialog(sc2, "You died!");
            System.exit(0);
        }
    }
}