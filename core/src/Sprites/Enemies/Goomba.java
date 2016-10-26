package Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.MarioGame;

import Screens.PlayScreen;

/**
 * Created by Felix on 23.10.2016.
 */
public class Goomba extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroyed = false;
    private boolean destroyed = false;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i<2;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i*16, 0,16,16));
            walkAnimation = new Animation(0.4f, frames);
            stateTime = 0;
            setBounds(getX(), getY(),16/MarioGame.PPM, 16/MarioGame.PPM);
        }
    }

    public void update(float dt){
        stateTime += dt;
        if (setToDestroyed && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 1, 16, 16));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MarioGame.PPM);
        fdef.filter.categoryBits = MarioGame.ENEMY_BIT;
        fdef.filter.maskBits =
                MarioGame.GROUND_BIT |
                MarioGame.COIN_BIT |
                MarioGame.BRICK_BIT |
                MarioGame.ENEMY_BIT |
                MarioGame.OBJECT_BIT |
                MarioGame.MARIO_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1/MarioGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1/MarioGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1/MarioGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1/MarioGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 1;//Experimentell, wenn Goombakopf zu hüpffreudig auf 0.5f
        fdef.filter.categoryBits = MarioGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    /*
     * texturen werden nur gezeichnet, wenn der Goomba nicht zerstört ist
     * oder seit weniger als 1 sekunde zerstört ist
     */
    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead() {
        setToDestroyed = true;
    }
}
