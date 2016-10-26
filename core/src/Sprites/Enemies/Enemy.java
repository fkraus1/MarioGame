package Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Screens.PlayScreen;

/**
 * Created by Felix on 23.10.2016.
 */
public abstract class Enemy extends Sprite {

    protected PlayScreen screen;
    protected World world;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead();

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
