package Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.MarioGame;

import Screens.PlayScreen;

/**
 * Created by Felix on 23.10.2016.
 */
public abstract class Item extends Sprite {

    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 16 / MarioGame.PPM, 16 / MarioGame.PPM);
        defineItem();

    }

    protected abstract void defineItem();
}
